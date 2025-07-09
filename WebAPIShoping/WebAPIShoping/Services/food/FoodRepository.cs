using Microsoft.EntityFrameworkCore;
using WebAPIShoping.Database;
using WebAPIShoping.Helpers;
using WebAPIShoping.Models;

namespace WebAPIShoping.Services.food
{
    public class FoodRepository:IFoodRepository
    {
        private readonly MyDbContext _context;

        public FoodRepository(MyDbContext context)
        {
            _context = context;
        }

        public async Task<FoodModel> CreateFoodAsync(FoodModel model)
        {
            var dbFood = model.ToEntity();
            _context.Foods.Add(dbFood);
            await _context.SaveChangesAsync();
            return dbFood.ToModel();
        }


        public async Task<List<FoodModel>> GetAllFoodsAsync()
        {
            var foods = await _context.Foods.ToListAsync();
            return foods.ToModelList(); // Gọi hàm map từ FoodMapper
        }

        public async Task<FoodModel?> GetFoodByIdAsync(Guid id)
        {
            var food = await _context.Foods.FindAsync(id);
            if (food == null) return null;
            return food.ToModel(); // Gọi hàm map từ FoodMapper
        }
    }
}
