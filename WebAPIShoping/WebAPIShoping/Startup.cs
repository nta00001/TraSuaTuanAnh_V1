using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using System.Text;
using WebAPIShoping.Database;
using WebAPIShoping.Helpers;
using WebAPIShoping.Services;
using WebAPIShoping.Services.food;
using WebAPIShoping.Services.user;
using WebAPIShoping.Services.voucher;

namespace WebAPIShoping
{
    public class Startup
    {
        private readonly IConfiguration _config;

        // Constructor lấy IConfiguration từ DI container, đọc config appsettings.json
        public Startup(IConfiguration config)
        {
            _config = config;
        }

        // Phương thức đăng ký các service vào DI container
        public void ConfigureServices(IServiceCollection services)
        {
            // Lấy key bí mật để mã hóa JWT từ appsettings.json
            var key = Encoding.UTF8.GetBytes(_config["JwtSettings:SecretKey"]);

            // Đăng ký DbContext với MySQL, dùng connection string từ config
            services.AddDbContext<MyDbContext>(options =>
                options.UseMySql(_config.GetConnectionString("MyDB"),
                    new MySqlServerVersion(new Version(9, 0, 3))));

            // Đăng ký các repository và helper vào DI container
            services.AddScoped<IUserDbRepository, UserDbRepository>();
            services.AddScoped<IOrderRepository, OrderRepository>();
            services.AddScoped<JwtUtils>();
            services.AddScoped<IFoodRepository, FoodRepository>();
            services.AddScoped<IVoucherRepository, VoucherRepository>();


            // Cấu hình Authentication với JWT Bearer
            services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
                .AddJwtBearer(options =>
                {
                    // Cấu hình kiểm tra token
                    options.TokenValidationParameters = new TokenValidationParameters
                    {
                        ValidateIssuerSigningKey = true, // Kiểm tra key dùng để sign token
                        IssuerSigningKey = new SymmetricSecurityKey(key), // Key để kiểm tra
                        ValidateIssuer = true, // Kiểm tra issuer
                        ValidateAudience = true, // Kiểm tra audience
                        ValidIssuer = _config["JwtSettings:Issuer"], // Issuer hợp lệ
                        ValidAudience = _config["JwtSettings:Audience"], // Audience hợp lệ
                        ValidateLifetime = true, // Kiểm tra thời gian sống của token
                        ClockSkew = TimeSpan.Zero // Không cho phép sai số thời gian
                    };
                });

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo
                {
                    Title = "Web API Shopping",
                    Version = "v1"
                });

                c.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
                {
                    Description = "Nhập token theo định dạng: Bearer {token}",
                    Name = "Authorization",
                    In = ParameterLocation.Header,
                    Type = SecuritySchemeType.ApiKey,
                    Scheme = "Bearer"
                });

                c.AddSecurityRequirement(new OpenApiSecurityRequirement
    {
        {
            new OpenApiSecurityScheme
            {
                Reference = new OpenApiReference
                {
                    Type = ReferenceType.SecurityScheme,
                    Id = "Bearer"
                },
                In = ParameterLocation.Header,
                Name = "Authorization"
            },
            new List<string>()
        }
    });
            });



            // Đăng ký controller MVC
            services.AddControllers();

            // Thêm swagger (tùy chọn, cho phát triển)
            services.AddEndpointsApiExplorer();
            services.AddSwaggerGen();
        }

        // Phương thức cấu hình middleware pipeline
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                // Hiển thị trang lỗi chi tiết khi dev
                app.UseDeveloperExceptionPage();

                // Cho phép truy cập Swagger UI khi phát triển
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            // Cho phép phục vụ file tĩnh từ thư mục wwwroot
            app.UseStaticFiles();

            // Sử dụng routing middleware
            app.UseRouting();

            // Bật authentication middleware (xác thực)
            app.UseAuthentication();

            // Bật authorization middleware (phân quyền)
            app.UseAuthorization();

            // Định tuyến endpoint cho controller
            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}