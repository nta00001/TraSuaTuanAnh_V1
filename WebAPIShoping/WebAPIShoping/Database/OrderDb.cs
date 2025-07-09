using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace WebAPIShoping.Database
{
    public class OrderDb
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; } // ID đơn hàng, tự động sinh, khóa chính


        [Required]
        public required string Address { get; set; }


        [Required]
        public DateTime OrderDate { get; set; } = DateTime.Now;


        [Required]
        public required string PaymentMethod { get; set; }


        [Required]
        public required string OrderStatus { get; set; }


        [Required]
        public decimal DiscountAmount { get; set; } = 0;


        [Required]
        public decimal TotalAmount { get; set; } = 0;


        [Required]
        public Guid UserId { get; set; }


        [ForeignKey("UserId")]
        public UserDb? User { get; set; }


        public List<OrderDetailDb> OrderDetails { get; set; } = new();



        public Guid? VoucherId { get; set; }

        [ForeignKey("VoucherId")]
        public VoucherDb? Voucher { get; set; }
    }
}