using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace WebAPIShoping.Database
{
    public class FoodDb
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; } // ID món ăn, tự động sinh, khóa chính

        [Required]
        [MaxLength(50)]
        public required string FoodName { get; set; } // Tên món ăn, tối đa 50 ký tự, không được để trống

        [Required]
        [MaxLength(200)]
        public required string Description { get; set; } // Mô tả món ăn, tối đa 200 ký tự, không được để trống

        [Required]
        public required decimal Price { get; set; } // Giá món ăn, kiểu decimal, không được để trống

        [Required]
        public required string Size { get; set; } // Kích cỡ món ăn (ví dụ: nhỏ, vừa, lớn), không được để trống

        [Required]
        public int Quantity { get; set; } // Số lượng món ăn còn trong kho

        // URL hoặc tên file ảnh minh họa món ăn, tối đa 200 ký tự, có thể null (nếu không có ảnh)
        [MaxLength(200)]
        public string? ImageUrl { get; set; }

        // Danh sách chi tiết đơn hàng có chứa món ăn này
        // Một món ăn có thể xuất hiện trong nhiều OrderDetail khác nhau
        public List<OrderDetailDb> OrderDetails { get; set; } = new();
    }
}
