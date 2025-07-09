using WebAPIShoping.Database;

namespace WebAPIShoping.Services
{
    public interface IOrderRepository
    {
        Task<Guid> PlaceOrderAsync(OrderDb order);
        Task<List<OrderDb>> GetOrdersByUserAsync(Guid userId);
        Task<OrderDb?> GetOrderByIdAsync(Guid orderId);
        Task<FoodDb?> GetFoodByIdAsync(Guid foodId);
        Task<UserVoucherDb?> GetUserVoucherAsync(Guid userId, Guid voucherId);
        Task PlaceOrderWithVoucherAsync(OrderDb order);
    }
}
