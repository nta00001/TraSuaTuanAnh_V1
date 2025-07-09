namespace WebAPIShoping.Models
{
    public class FoodModel
    {
        public Guid Id { get; set; }  // Thống nhất kiểu với DB

        public string FoodName { get; set; } = null!;

        public string Description { get; set; } = null!;

        public decimal Price { get; set; }

        public string Size { get; set; } = null!;

        public int Quantity { get; set; }

        public string? ImageUrl { get; set; }
    }
}
