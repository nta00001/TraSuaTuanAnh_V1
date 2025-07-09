using Microsoft.EntityFrameworkCore;
using WebAPIShoping.Database;
using WebAPIShoping.Helpers;
using WebAPIShoping.Models;

namespace WebAPIShoping.Services.voucher
{
    public class VoucherRepository : IVoucherRepository
    {
        private readonly MyDbContext _context;

        public VoucherRepository(MyDbContext context)
        {
            _context = context;
        }

        public async Task<List<VoucherDb>> GetAllAsync()
        {
            return await _context.Vouchers.ToListAsync();
        }

        public async Task<VoucherDb?> GetByIdAsync(Guid id)
        {
            return await _context.Vouchers.FirstOrDefaultAsync(v => v.Id == id);
        }

        public async Task<VoucherDb?> GetByCodeAsync(string code)
        {
            return await _context.Vouchers.FirstOrDefaultAsync(v => v.Code == code);
        }

        public async Task<Guid> CreateAsync(VoucherDb voucher)
        {
            voucher.Id = Guid.NewGuid();

            // Thêm voucher vào bảng Vouchers
            _context.Vouchers.Add(voucher);

            // Lấy danh sách tất cả user
            var users = await _context.Users.ToListAsync();

            // Tạo UserVoucher cho mỗi user
            var userVouchers = users.Select(user => new UserVoucherDb
            {
                Id = Guid.NewGuid(),
                VoucherId = voucher.Id,
                UserId = user.Id,
                RemainingUses = 5 // hoặc bao nhiêu lượt bạn muốn cho mỗi user
            }).ToList();

            // Thêm vào bảng UserVoucher
            _context.UserVouchers.AddRange(userVouchers);

            // Lưu tất cả thay đổi
            await _context.SaveChangesAsync();

            return voucher.Id;
        }


        public async Task<bool> UpdateAsync(VoucherDb voucher)
        {
            var existing = await _context.Vouchers.FindAsync(voucher.Id);
            if (existing == null) return false;

            _context.Entry(existing).CurrentValues.SetValues(voucher);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<bool> DeleteAsync(Guid id)
        {
            var voucher = await _context.Vouchers.FindAsync(id);
            if (voucher == null) return false;

            _context.Vouchers.Remove(voucher);
            await _context.SaveChangesAsync();
            return true;
        }
        public async Task<List<UserVoucherModel>> GetVouchersByUserAsync(Guid userId)
        {

            var userVouchers = await _context.UserVouchers
                .Include(uv => uv.Voucher)
                .Where(uv => uv.UserId == userId)
                .ToListAsync();

            var result = userVouchers.Select(VoucherMapper.ToUserVoucherModel).ToList();

            return result;

        }

        public async Task<List<VoucherDb>> GetActiveVouchersAsync()
        {
            return await _context.Vouchers
                .Where(v => v.IsActive == true)
                .ToListAsync();
        }

        public async Task AddUserVouchersAsync(List<UserVoucherDb> userVouchers)
        {
            _context.UserVouchers.AddRange(userVouchers);
            await _context.SaveChangesAsync();
        }

    }


}