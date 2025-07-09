using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using WebAPIShoping.Database;
using WebAPIShoping.Models;

namespace WebAPIShoping.Helpers
{
    public class JwtUtils
    {
        private readonly IConfiguration _config;
        private readonly byte[] _key;

        public JwtUtils(IConfiguration config)
        {
            _config = config;
            _key = Encoding.UTF8.GetBytes(_config["JwtSettings:SecretKey"]);
        }

        // Tạo token JWT
        public string GenerateToken(UserDb user)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var credentials = new SigningCredentials(new SymmetricSecurityKey(_key), SecurityAlgorithms.HmacSha256);

            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new[]
                {
                new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new Claim(ClaimTypes.Name, user.Username),
                new Claim(ClaimTypes.MobilePhone, user.PhoneNumber)
            }),
                Expires = DateTime.UtcNow.AddDays(3),
                SigningCredentials = credentials,
                Issuer = _config["JwtSettings:Issuer"],
                Audience = _config["JwtSettings:Audience"]
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);
        }

        // Validate token, trả về ClaimsPrincipal nếu hợp lệ, ngược lại trả về null
        public ClaimsPrincipal? ValidateToken(string token)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            try
            {
                var parameters = new TokenValidationParameters
                {
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(_key),
                    ValidateIssuer = true,
                    ValidIssuer = _config["JwtSettings:Issuer"],
                    ValidateAudience = true,
                    ValidAudience = _config["JwtSettings:Audience"],
                    ValidateLifetime = true,
                    ClockSkew = TimeSpan.Zero
                };

                var principal = tokenHandler.ValidateToken(token, parameters, out SecurityToken validatedToken);

                // Bạn có thể kiểm tra thêm kiểu token hoặc thuật toán ở đây nếu cần

                return principal;
            }
            catch
            {
                // Token không hợp lệ hoặc hết hạn
                return null;
            }
        }
    }
}