using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace WebAPIShoping.Database
{
    [Table("UsersDb")]
    public class UserDb
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; } // ID tự động sinh, là khóa chính

        [Required]
        [MaxLength(50)]
        public required string Username { get; set; } // Tên đăng nhập, không được để trống, giới hạn 50 ký tự

        [Required]
        [MaxLength(20)]
        public required string PhoneNumber { get; set; } // Số điện thoại, không được để trống, giới hạn 20 ký tự

        [Required]
        public required string PasswordHash { get; set; } // Mật khẩu đã được băm (hash), không được để trống

        // Danh sách các đơn hàng thuộc về user này
        public List<OrderDb> Orders { get; set; } = new();

        // Danh sách voucher user đã nhận (nếu có)
        public List<UserVoucherDb> UserVouchers { get; set; } = new();
    }
}