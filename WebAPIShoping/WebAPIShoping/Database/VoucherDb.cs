using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace WebAPIShoping.Database
{
    [Table("VoucherDb")]
    public class VoucherDb
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; } // ID voucher, tự động sinh, khóa chính

        [Required]
        [MaxLength(100)]
        public required string Description { get; set; } // Mô tả voucher (ví dụ: "Giảm 20% cho đơn hàng")

        [Required]
        [MaxLength(50)]
        public required string Code { get; set; } // Mã voucher (ví dụ: "SALE20")

        [Required]
        public decimal DiscountAmount { get; set; } // Số tiền giảm trực tiếp (có thể dùng nếu không phải %)

        [Required]
        [Range(0, 100)]
        public int DiscountPercent { get; set; } // Phần trăm giảm giá (0-100)

        [Required]
        public int TotalQuantity { get; set; } // Tổng số voucher được phát hành (tổng số lượt dùng)

        [Required]
        public DateTime StartDate { get; set; } // Ngày bắt đầu có hiệu lực

        [Required]
        public DateTime? ExpirationDate { get; set; } // Ngày hết hạn

        [Required]
        public bool IsActive { get; set; } = true; // Trạng thái voucher còn hiệu lực hay không

        // Danh sách các user đã nhận voucher này
        public List<UserVoucherDb> UserVouchers { get; set; } = new();
    }
}
