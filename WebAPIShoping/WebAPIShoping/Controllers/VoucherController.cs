using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebAPIShoping.Database;
using WebAPIShoping.Helpers;
using WebAPIShoping.Migrations;
using WebAPIShoping.Models;
using WebAPIShoping.Models.request;
using WebAPIShoping.Services.voucher;

namespace WebAPIShoping.Controllers
{
    [ApiController]
    [Route("api/voucher")]
    [Authorize]
    public class VoucherController : ControllerBase
    {
        private readonly IVoucherRepository _repository;
      

        public VoucherController(IVoucherRepository repository)
        {
            _repository = repository;
        }

        /// <summary>
        /// Lấy danh sách tất cả voucher
        /// </summary>
        [HttpGet("getAllVoucher")]
        public async Task<ActionResult<BaseResponse<List<VoucherModel>>>> GetAll()
        {
            try
            {
                var listDb = await _repository.GetAllAsync();
                var listModel = VoucherMapper.ToModelList(listDb);

                return Ok(new BaseResponse<List<VoucherModel>>
                {
                    StatusCode = 200,
                    Success = true,
                    Message = "Lấy danh sách voucher thành công",
                    Data = listModel
                });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new BaseResponse<List<VoucherModel>>
                {
                    StatusCode = 500,
                    Success = false,
                    Message = "Lỗi: " + ex.Message,
                    Data = new List<VoucherModel>()
                });
            }
        }

        /// <summary>
        /// Lấy danh sách voucher kèm số lượt còn lại theo người dùng
        /// </summary>
        [HttpPost("getVoucherByUser")]
        public async Task<ActionResult<BaseResponse<List<UserVoucherModel>>>> GetVouchersByUser([FromBody] UserIdRequest request)
        {
            try
            {
                var list = await _repository.GetVouchersByUserAsync(request.UserId);

                // Lọc bỏ các voucher có RemainingUses == 0
                var filteredList = list.Where(v => v.RemainingUses > 0).ToList();

                return Ok(new BaseResponse<List<UserVoucherModel>>
                {
                    StatusCode = 200,
                    Success = true,
                    Message = "Lấy voucher theo người dùng thành công",
                    Data = filteredList
                });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new BaseResponse<List<UserVoucherModel>>
                {
                    StatusCode = 500,
                    Success = false,
                    Message = "Lỗi: " + ex.Message,
                    Data = new List<UserVoucherModel>()
                });
            }
        }


        /// <summary>
        /// Tạo voucher mới
        /// </summary>
        [HttpPost("createVoucher")]
        public async Task<ActionResult<BaseResponse<Guid>>> Create([FromBody] VoucherModel dto)
        {
            try
            {
                var voucher = VoucherMapper.ToDb(dto);
                var id = await _repository.CreateAsync(voucher);

                return Ok(new BaseResponse<Guid>
                {
                    StatusCode = 200,
                    Success = true,
                    Message = "Tạo voucher thành công",
                    Data = id
                });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new BaseResponse<Guid>
                {
                    StatusCode = 500,
                    Success = false,
                    Message = "Đã xảy ra lỗi: " + ex.Message,
                    Data = Guid.Empty
                });
            }
        }



    }
}