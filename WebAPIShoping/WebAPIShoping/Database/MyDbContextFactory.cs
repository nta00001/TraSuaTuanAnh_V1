using Microsoft.EntityFrameworkCore.Design;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace WebAPIShoping.Database
{
    public class MyDbContextFactory : IDesignTimeDbContextFactory<MyDbContext>
    {
        public MyDbContext CreateDbContext(string[] args)
        {
            // Load cấu hình từ appsettings.json
            IConfigurationRoot configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())  // Thư mục gốc project
                .AddJsonFile("appsettings.json")
                .Build();

            var connectionString = configuration.GetConnectionString("MyDB");

            var optionsBuilder = new DbContextOptionsBuilder<MyDbContext>();

            // Sử dụng MySQL với Pomelo (hoặc MySql.Data.EntityFrameworkCore nếu bạn dùng)
            optionsBuilder.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString));

            return new MyDbContext(optionsBuilder.Options);
        }
    }
}
