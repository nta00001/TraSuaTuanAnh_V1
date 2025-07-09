namespace WebAPIShoping.Models
{
    public class UserVoucherModel
    {
        public Guid VoucherId { get; set; }
        public string? Code { get; set; }

        public string? Description { get; set; }
        public decimal? DiscountAmount { get; set; }
        public decimal? DiscountPercent { get; set; }
        public DateTime? ExpirationDate { get; set; }
        public int RemainingUses { get; set; }
    }
}
