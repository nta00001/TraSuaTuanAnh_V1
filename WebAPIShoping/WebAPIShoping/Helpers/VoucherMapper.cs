using WebAPIShoping.Database;
using WebAPIShoping.Models;

namespace WebAPIShoping.Helpers
{
    public static class VoucherMapper
    {
        public static VoucherModel? ToModel(VoucherDb? db)
        {
            if (db == null) return null;

            return new VoucherModel
            {
                Id = db.Id,
                Code = db.Code,
                DiscountAmount = db.DiscountAmount,
                StartDate = db.StartDate,
                ExpirationDate = db.ExpirationDate,
                IsActive = db.IsActive
            };
        }

        public static VoucherDb? ToDb(VoucherModel? model)
        {
            if (model == null) return null;

            return new VoucherDb
            {
                Id = Guid.NewGuid(),
                Code = model.Code,
                DiscountAmount = model.DiscountAmount,
                StartDate = model.StartDate,
                ExpirationDate = model.ExpirationDate,
                IsActive = model.IsActive,

                // Các trường bắt buộc còn lại bạn có thể set giá trị mặc định hoặc sửa lại sau khi tạo entity
                Description = string.Empty, 
                DiscountPercent = 0,
                TotalQuantity = 0
            };
        }

        // Hàm map UserVoucherDb -> UserVoucherModel mới thêm
        public static UserVoucherModel ToUserVoucherModel(UserVoucherDb uv)
        {
            return new UserVoucherModel
            {
                VoucherId = uv.VoucherId,
                Code = uv.Voucher?.Code,
                Description = uv.Voucher?.Description,
                DiscountAmount = uv.Voucher?.DiscountAmount,
                DiscountPercent = uv.Voucher?.DiscountPercent,
                ExpirationDate = uv.Voucher?.ExpirationDate,
                RemainingUses = uv.RemainingUses
            };
        }

        public static List<VoucherModel> ToModelList(IEnumerable<VoucherDb>? dbList)
        {
            return dbList?.Select(ToModel)
                          .Where(v => v != null)!
                          .ToList() ?? new List<VoucherModel>();
        }

        public static List<VoucherDb> ToDbList(IEnumerable<VoucherModel>? modelList)
        {
            return modelList?.Select(ToDb)
                            .Where(v => v != null)!
                            .ToList() ?? new List<VoucherDb>();
        }
    }
}
