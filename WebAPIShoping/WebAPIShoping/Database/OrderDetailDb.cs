using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace WebAPIShoping.Database
{
    public class OrderDetailDb
    {
        [Key]
        public Guid Id { get; set; } // ID chi tiết đơn hàng, tự động sinh, khóa chính

        [Required]
        public Guid OrderId { get; set; } // Khóa ngoại đến đơn hàng

        [ForeignKey("OrderId")]
        public OrderDb? Order { get; set; } // Quan hệ đến đơn hàng

        [Required]
        public Guid FoodId { get; set; } // Khóa ngoại đến món ăn

        [ForeignKey("FoodId")]
        public FoodDb? Food { get; set; } // Quan hệ đến món ăn

        [Required]
        public int Quantity { get; set; } // Số lượng món ăn trong đơn

        [Required]
        public string Size { get; set; } // Số lượng món ăn trong đơn

        [Required]
        public decimal UnitPrice { get; set; } // Giá của mỗi món ăn tại thời điểm đặt hàng
    }
}
