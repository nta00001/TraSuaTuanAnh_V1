namespace WebAPIShoping.Models
{
    public class VoucherModel
    {
        public Guid Id { get; set; }
        public string Code { get; set; }
        public decimal DiscountAmount { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime? ExpirationDate { get; set; }
        public bool IsActive { get; set; }
    }
}
