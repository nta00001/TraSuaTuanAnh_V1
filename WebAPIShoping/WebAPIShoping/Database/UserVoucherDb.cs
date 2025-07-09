using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace WebAPIShoping.Database
{
    [Table("UserVouchers")]
    public class UserVoucherDb
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; } // ID, khóa chính

        [Required]
        public Guid UserId { get; set; } // Khóa ngoại đến UserDb

        [ForeignKey("UserId")]
        public UserDb? User { get; set; } // Quan hệ đến user

        [Required]
        public Guid VoucherId { get; set; } // Khóa ngoại đến VoucherDb

        [ForeignKey("VoucherId")]
        public VoucherDb? Voucher { get; set; } // Quan hệ đến voucher

        [Required]
        public int RemainingUses { get; set; } // Số lượt còn lại user có thể dùng voucher này
    }
}
