namespace WebAPIShoping.Models
{
    public class OrderModel
    {
        public string UserName { get; set; } 
        public string PhoneNumber { get; set; } 
        public string Address { get; set; }
        public DateTime OrderDate { get; set; } 
        public string PaymentMethod { get; set; }
        public string OrderStatus { get; set; } 
        public decimal DiscountAmount { get; set; }
        public decimal TotalAmount { get; set; }
        public Guid UserId { get; set; }

        public Guid? VoucherId { get; set; }

        public List<FoodModel> Foods { get; set; } = new();
    }



}
