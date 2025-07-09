using WebAPIShoping.Database;
using WebAPIShoping.Models;

namespace WebAPIShoping.Helpers
{
    public static class FoodMapper
    {
        // Map từ FoodDb sang FoodModel
        public static FoodModel? ToModel(this FoodDb? entity)
        {
            if (entity == null) return null;

            return new FoodModel
            {
                Id = entity.Id,  // Giữ nguyên Guid
                FoodName = entity.FoodName,
                Description = entity.Description,
                Price = entity.Price,
                Size = entity.Size,
                Quantity = entity.Quantity,
                ImageUrl = entity.ImageUrl
            };
        }

        // Map từ FoodModel sang FoodDb
        public static FoodDb? ToEntity(this FoodModel? model)
        {
            if (model == null) return null;

            return new FoodDb
            {
                // Không set Id, EF tự sinh
                FoodName = model.FoodName,
                Description = model.Description,
                Price = model.Price,
                Size = model.Size,
                Quantity = model.Quantity,
                ImageUrl = model.ImageUrl
            };
        }

        // Map danh sách FoodDb sang danh sách FoodModel
        public static List<FoodModel> ToModelList(this IEnumerable<FoodDb> entities)
        {
            return entities.Select(e => e.ToModel()!)
                           .Where(f => f != null)
                           .ToList()!;
        }

        // Map danh sách FoodModel sang danh sách FoodDb
        public static List<FoodDb> ToEntityList(this IEnumerable<FoodModel> models)
        {
            return models.Select(m => m.ToEntity()!)
                         .Where(f => f != null)
                         .ToList()!;
        }
    }
}


