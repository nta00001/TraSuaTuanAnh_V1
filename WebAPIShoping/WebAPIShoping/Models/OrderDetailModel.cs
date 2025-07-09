namespace WebAPIShoping.Models
{
    public class OrderDetailModel
    {
        public string UserName { get; set; } = string.Empty;
        public string PhoneNumber { get; set; } = string.Empty;
        public string Address { get; set; } = string.Empty;
        public DateTime OrderDate { get; set; }
        public string PaymentMethod { get; set; } = string.Empty;
        public string OrderStatus { get; set; } = string.Empty;
        public decimal DiscountAmount { get; set; }
        public decimal TotalAmount { get; set; }
        public List<FoodModel> Foods { get; set; } 
    }
}
