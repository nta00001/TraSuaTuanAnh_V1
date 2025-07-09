using Microsoft.EntityFrameworkCore;

namespace WebAPIShoping.Database
{
    public class MyDbContext : DbContext
    {
        public MyDbContext(DbContextOptions<MyDbContext> options) : base(options)
        {
        }

        #region DbSet
        public DbSet<UserDb> Users { get; set; }
        public DbSet<FoodDb> Foods { get; set; }
        public DbSet<OrderDb> Orders { get; set; }
        public DbSet<OrderDetailDb> OrderDetails { get; set; }
        public DbSet<VoucherDb> Vouchers { get; set; }
        public DbSet<UserVoucherDb> UserVouchers { get; set; }  
        #endregion

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Quan hệ User - UserVoucher (1 - N)
            modelBuilder.Entity<UserVoucherDb>()
                .HasOne(uv => uv.User)
                .WithMany(u => u.UserVouchers)
                .HasForeignKey(uv => uv.UserId);

            // Quan hệ Voucher - UserVoucher (1 - N)
            modelBuilder.Entity<UserVoucherDb>()
                .HasOne(uv => uv.Voucher)
                .WithMany(v => v.UserVouchers)
                .HasForeignKey(uv => uv.VoucherId);

            // Quan hệ User - Order (1 - N)
            modelBuilder.Entity<OrderDb>()
                .HasOne(o => o.User)
                .WithMany(u => u.Orders)
                .HasForeignKey(o => o.UserId);

            // Quan hệ Order - OrderDetail (1 - N)
            modelBuilder.Entity<OrderDetailDb>()
                .HasOne(od => od.Order)
                .WithMany(o => o.OrderDetails)
                .HasForeignKey(od => od.OrderId);

            // Quan hệ Food - OrderDetail (1 - N)
            modelBuilder.Entity<OrderDetailDb>()
                .HasOne(od => od.Food)
                .WithMany(f => f.OrderDetails)
                .HasForeignKey(od => od.FoodId);
        }

    }
}

