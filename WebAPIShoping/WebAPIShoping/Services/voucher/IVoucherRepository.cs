using WebAPIShoping.Database;
using WebAPIShoping.Models;

namespace WebAPIShoping.Services.voucher
{
    public interface IVoucherRepository
    {
        Task<List<VoucherDb>> GetAllAsync();
        Task<VoucherDb?> GetByIdAsync(Guid id);
        Task<VoucherDb?> GetByCodeAsync(string code);
        Task<Guid> CreateAsync(VoucherDb voucher);
        Task<bool> UpdateAsync(VoucherDb voucher);
        Task<bool> DeleteAsync(Guid id);

        Task<List<VoucherDb>> GetActiveVouchersAsync();
        Task AddUserVouchersAsync(List<UserVoucherDb> userVouchers);

        Task<List<UserVoucherModel>> GetVouchersByUserAsync(Guid userId);

    }
}