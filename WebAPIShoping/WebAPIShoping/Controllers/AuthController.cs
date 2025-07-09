using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using WebAPIShoping.Models;
using Microsoft.Extensions.Configuration;
using WebAPIShoping.Helpers;
using WebAPIShoping.Database;
using WebAPIShoping.Services.user;
using Microsoft.EntityFrameworkCore;
using WebAPIShoping.Services.voucher;

namespace WebAPIShoping.Controllers
{
    [ApiController]
    [Route("api/auth")]
    public class AuthController : ControllerBase
    {

        private readonly IUserDbRepository _userRepository;
        private readonly IVoucherRepository _voucherRepository;
        private readonly JwtUtils _jwtUtils;

        public AuthController(JwtUtils jwtUtils, IUserDbRepository userRepository, IVoucherRepository voucherRepository)
        {
            _userRepository = userRepository;
            _voucherRepository = voucherRepository;
            _jwtUtils = jwtUtils;
        }


      
        //Đăng ký người dùng mới và trả về JWT token
        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] UserRegisterModel model)
        {
            // Kiểm tra số điện thoại đã tồn tại chưa trong database
            var existingUser = await _userRepository.GetUserByPhoneNumberAsync(model.PhoneNumber);
            if (existingUser != null)
            {
                return BadRequest(new BaseResponse<object>
                {
                    StatusCode = 400,
                    Success = false,
                    Message = "Số điện thoại đã được đăng ký!",
                    Data = null
                });
            }

            // Tạo user mới, chuyển từ model sang entity UserDb
            var newUser = new UserDb
            {
                Id = Guid.NewGuid(),
                Username = model.Username,
                PhoneNumber = model.PhoneNumber,
                PasswordHash = PasswordHasher.HashPassword(model.Password) // Hash mật khẩu
            };

            // Lưu user mới vào database
            await _userRepository.CreateUserAsync(newUser);

            var defaultVouchers = await _voucherRepository.GetActiveVouchersAsync();

            var userVouchers = defaultVouchers.Select(v => new UserVoucherDb
            {
                Id = Guid.NewGuid(),
                UserId = newUser.Id,
                VoucherId = v.Id,
                RemainingUses = 5
            }).ToList();

            await _voucherRepository.AddUserVouchersAsync(userVouchers);


            // Tạo JWT token cho user mới
            var token = _jwtUtils.GenerateToken(newUser);

            return Created("", new BaseResponse<object>
            {
                StatusCode = 201,
                Success = true,
                Message = "Đăng ký thành công!",
                Data = new
                {
                    Token = token
                }
            });
        }



        // đăng nhập
        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] UserLoginModel model)
        {
            // Tìm user theo số điện thoại trong database
            var user = await _userRepository.GetUserByPhoneNumberAsync(model.PhoneNumber);

            // Nếu không tồn tại user hoặc mật khẩu không đúng
            if (user == null || !PasswordHasher.VerifyPassword(model.Password, user.PasswordHash))
            {
                return Unauthorized(new BaseResponse<object>
                {
                    StatusCode = 401,
                    Success = false,
                    Message = "Số điện thoại hoặc mật khẩu không đúng!",
                    Data = null
                });
            }

            // Tạo JWT token
            var token = _jwtUtils.GenerateToken(user);

            return Ok(new BaseResponse<object>
            {
                StatusCode = 200,
                Success = true,
                Message = "Đăng nhập thành công!",
                Data = new
                {
                    Token = token
                  
                }
            });
        }
        // đăng nhập bằng token

        [HttpPost("login-with-token")]
        public async Task<IActionResult> LoginWithToken([FromHeader(Name = "Authorization")] string authorizationHeader)
        {
            if (string.IsNullOrEmpty(authorizationHeader) || !authorizationHeader.StartsWith("Bearer "))
            {
                return Unauthorized(new { message = "Token không hợp lệ hoặc không được cung cấp!" });
            }

            var token = authorizationHeader.Substring("Bearer ".Length).Trim();

            var principal = _jwtUtils.ValidateToken(token);
            if (principal == null)
            {
                return Unauthorized(new { message = "Token không hợp lệ hoặc đã hết hạn!" });
            }

            var userIdClaim = principal.FindFirst(ClaimTypes.NameIdentifier);
            if (userIdClaim == null)
            {
                return Unauthorized(new { message = "Token không hợp lệ!" });
            }

            var userId = Guid.Parse(userIdClaim.Value);
            var user = await _userRepository.GetUserByIdAsync(userId);
            if (user == null)
            {
                return NotFound(new { message = "Không tìm thấy user!" });
            }

            return Ok(new
            {
                message = "Đăng nhập bằng token thành công!",
                user = new
                {
                    user.Id,
                    user.Username,
                    user.PhoneNumber
                }
            });
        }



   
        // Lấy thông tin user từ token (truyền trong header Authorization)
    
        [HttpGet("userinfo")]
        public async Task<IActionResult> GetUserInfo()
        {
            var authHeader = Request.Headers["Authorization"].ToString();
            if (string.IsNullOrEmpty(authHeader) || !authHeader.StartsWith("Bearer "))
            {
                return Unauthorized(new BaseResponse<object>
                {
                    StatusCode = 401,
                    Success = false,
                    Message = "Token không được cung cấp hoặc sai định dạng!",
                    Data = null
                });
            }

            var token = authHeader.Replace("Bearer ", "").Trim();
            var principal = _jwtUtils.ValidateToken(token);

            if (principal == null)
            {
                return Unauthorized(new BaseResponse<object>
                {
                    StatusCode = 401,
                    Success = false,
                    Message = "Token không hợp lệ hoặc đã hết hạn!",
                    Data = null
                });
            }

            var userId = principal.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            if (string.IsNullOrEmpty(userId))
            {
                return Unauthorized(new BaseResponse<object>
                {
                    StatusCode = 401,
                    Success = false,
                    Message = "Không thể lấy thông tin user từ token!",
                    Data = null
                });
            }

            var user = await _userRepository.GetUserByIdAsync(Guid.Parse(userId));
            if (user == null)
            {
                return NotFound(new BaseResponse<object>
                {
                    StatusCode = 404,
                    Success = false,
                    Message = "Không tìm thấy user!",
                    Data = null
                });
            }

            return Ok(new BaseResponse<object>
            {
                StatusCode = 200,
                Success = true,
                Message = "Lấy thông tin thành công!",
                Data = new
                {
                    user.Id,
                    user.Username,
                    user.PhoneNumber
                }
            });
        }




    }
}
