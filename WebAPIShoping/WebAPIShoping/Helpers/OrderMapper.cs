using WebAPIShoping.Database;
using WebAPIShoping.Models;
using WebAPIShoping.Helpers; // Thêm dòng này để nhận diện hàm mở rộng

namespace WebAPIShoping.Helpers
{
    public static class OrderMapper
    {
        public static OrderModel ToModel(this OrderDb entity)
        {
            return new OrderModel
            {
                UserName = entity.User?.Username ?? string.Empty,
                PhoneNumber = entity.User?.PhoneNumber ?? string.Empty,
                Address = entity.Address ?? string.Empty,
                OrderDate = entity.OrderDate,
                PaymentMethod = entity.PaymentMethod ?? string.Empty,
                OrderStatus = entity.OrderStatus ?? string.Empty,
                DiscountAmount = entity.DiscountAmount,
                TotalAmount = entity.TotalAmount,
                UserId = entity.UserId,
                VoucherId = entity.VoucherId,
                Foods = entity.OrderDetails?.Select(od => new FoodModel
                {
                    Id = od.FoodId,
                    FoodName = od.Food?.FoodName ?? string.Empty,
                    Description = od.Food?.Description ?? string.Empty,
                    Price = od.UnitPrice, 
                    Size = od.Size,
                    Quantity = od.Quantity,
                    ImageUrl = od.Food?.ImageUrl
                }).ToList() ?? new List<FoodModel>()
            };
        }

        public static OrderDb ToEntity(this OrderModel model)
        {
            return new OrderDb
            {
                Address = model.Address,
                OrderDate = model.OrderDate,
                PaymentMethod = model.PaymentMethod,
                OrderStatus = model.OrderStatus,
                DiscountAmount = model.DiscountAmount,
                TotalAmount = model.TotalAmount,
                VoucherId = model.VoucherId,
                UserId = model.UserId,
                OrderDetails = model.Foods.Select(f => new OrderDetailDb
                {
                    FoodId = f.Id,
                    Quantity = f.Quantity,
                    Size = f.Size ?? string.Empty,
                    UnitPrice = f.Price
                }).ToList()
            };
        }



        public static OrderSummaryModel ToSummaryModel(this OrderDb entity)
        {
            return new OrderSummaryModel
            {
                OrderId = entity.Id,
                UserName = entity.User?.Username ?? string.Empty,
                PhoneNumber = entity.User?.PhoneNumber ?? string.Empty,
                Address = entity.Address,
                OrderDate = entity.OrderDate,
                PaymentMethod = entity.PaymentMethod,
                OrderStatus = entity.OrderStatus,
                DiscountAmount = entity.DiscountAmount,
                TotalAmount = entity.TotalAmount
            };
        }

        public static OrderDetailModel ToDetailModel(this OrderDb order)
        {
            return new OrderDetailModel
            {
                UserName = order.User?.Username ?? string.Empty,
                PhoneNumber = order.User?.PhoneNumber ?? string.Empty,
                Address = order.Address ?? string.Empty,
                OrderDate = order.OrderDate,
                PaymentMethod = order.PaymentMethod ?? string.Empty,
                OrderStatus = order.OrderStatus ?? string.Empty,
                DiscountAmount = order.DiscountAmount,
                TotalAmount = order.TotalAmount,
                Foods = order.OrderDetails.Select(od => new FoodModel
                {
                    Id = od.FoodId,
                    FoodName = od.Food?.FoodName ?? string.Empty,
                    Description = od.Food?.Description ?? string.Empty,
                    Price = od.UnitPrice,
                    Size = od.Size ?? string.Empty,
                    Quantity = od.Quantity,
                    ImageUrl = od.Food?.ImageUrl
                }).ToList()
            };
        }





        public static List<OrderSummaryModel> ToSummaryModelList(this List<OrderDb> entities)
        {
            return entities.Select(o => o.ToSummaryModel()).ToList();
        }
    }


}
