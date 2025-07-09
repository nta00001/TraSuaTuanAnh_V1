using Microsoft.EntityFrameworkCore;
using System;
using WebAPIShoping.Database;

namespace WebAPIShoping.Services
{
    public class OrderRepository : IOrderRepository
    {
        private readonly MyDbContext _context;

        public OrderRepository(MyDbContext context)
        {
            _context = context;
        }

        public async Task<Guid> PlaceOrderAsync(OrderDb order)
        {
            _context.Orders.Add(order);
            await _context.SaveChangesAsync();
            return order.Id;
        }

        public async Task<List<OrderDb>> GetOrdersByUserAsync(Guid userId)
        {
            return await _context.Orders
                .Include(o => o.User) 
                .Include(o => o.OrderDetails)
                    .ThenInclude(od => od.Food)
                .Where(o => o.UserId == userId)
                .OrderByDescending(o => o.OrderDate)
                .ToListAsync();
        }


        public async Task<OrderDb?> GetOrderByIdAsync(Guid orderId)
        {
            return await _context.Orders
                .Include(o => o.User) 
                .Include(o => o.OrderDetails) 
                    .ThenInclude(od => od.Food) 
                .FirstOrDefaultAsync(o => o.Id == orderId);
        }



        public async Task<FoodDb?> GetFoodByIdAsync(Guid foodId)
        {
            return await _context.Foods.FindAsync(foodId);
        }

        public async Task<UserVoucherDb?> GetUserVoucherAsync(Guid userId, Guid voucherId)
        {
            return await _context.UserVouchers
                .Include(uv => uv.Voucher)
                .FirstOrDefaultAsync(uv => uv.UserId == userId && uv.VoucherId == voucherId);
        }

        public async Task PlaceOrderWithVoucherAsync(OrderDb order)
        {
            _context.Orders.Add(order);
            await _context.SaveChangesAsync();
        }

    }
}

