using WebAPIShoping.Models;

namespace WebAPIShoping.Services.food
{
    public interface IFoodRepository
    {
        Task<List<FoodModel>> GetAllFoodsAsync();
        Task<FoodModel?> GetFoodByIdAsync(Guid id);

        Task<FoodModel> CreateFoodAsync(FoodModel model);

    }
}
