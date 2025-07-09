using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Diagnostics;
using System.Text;
using WebAPIShoping.Database;
using WebAPIShoping.Helpers;
using WebAPIShoping.Services;

namespace WebAPIShoping
{
    public class Program
    {
        public static void Main(string[] args)
        {
            // Tạo và build Host ASP.NET Core
            var host = CreateHostBuilder(args).Build();

            // Khởi động ngrok tunnel (nếu bạn cần expose app ra ngoài)
            Process.Start(new ProcessStartInfo
            {
                FileName = @"C:\Users\ADMIN\Downloads\ngrok-v3-stable-windows-amd64\ngrok.exe", // Đường dẫn đến file ngrok.exe
                Arguments = "start myapp", // Tên tunnel config trong ngrok
                UseShellExecute = true // Chạy ngrok dưới chế độ shell
            });

            // Chạy ứng dụng web
            host.Run();
        }

        // Cấu hình host builder, dùng Startup class để cấu hình dịch vụ và pipeline
        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    // Đăng ký class Startup để khởi tạo app
                    webBuilder.UseStartup<Startup>();

                    // Thiết lập URL và port cho ứng dụng (https)
                    webBuilder.UseUrls("https://localhost:7298");
                });
    }
}