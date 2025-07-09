using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System;
using WebAPIShoping.Helpers;
using WebAPIShoping.Models;
using WebAPIShoping.Models.request;
using WebAPIShoping.Services;

namespace WebAPIShoping.Controllers
{
    [ApiController]
    [Route("api/order")]
    [Authorize]
    public class OrderController : ControllerBase
    {
        private readonly IOrderRepository _repository;

        public OrderController(IOrderRepository repository)
        {
            _repository = repository;
        }

        /// <summary>
        /// API đặt đơn hàng, nhận dữ liệu từ client (OrderModel) và lưu vào DB
        /// </summary>
        /// <param name="model">Model đơn hàng gửi từ client</param>
        /// <returns>Trả về BaseResponse với ID đơn hàng được tạo</returns>
        [HttpPost("place")]
        public async Task<ActionResult<BaseResponse<Guid>>> PlaceOrder([FromBody] OrderModel model)
        {
            var orderEntity = model.ToEntity();
            // Nếu EF tự sinh ID thì không cần tự gán
            // orderEntity.Id = Guid.NewGuid(); 

            if (orderEntity.VoucherId.HasValue)
            {
                var userVoucher = await _repository.GetUserVoucherAsync(orderEntity.UserId, orderEntity.VoucherId.Value);

                if (userVoucher == null || userVoucher.RemainingUses <= 0)
                {
                    return BadRequest(new BaseResponse<Guid>
                    {
                        Success = false,
                        Message = "Voucher không hợp lệ hoặc đã hết lượt"
                    });
                }

                userVoucher.RemainingUses--;
            }

            // Bạn nên có transaction để xử lý đồng bộ
            await _repository.PlaceOrderWithVoucherAsync(orderEntity);
            // Đảm bảo phần giảm RemainingUses được lưu ở đây hoặc trong repository

            return Ok(new BaseResponse<Guid>
            {
                StatusCode = 200,
                Success = true,
                Message = "Đặt hàng thành công",
                Data = orderEntity.Id
            });
        }



        /// <summary>
        /// Lấy danh sách đơn hàng theo ID người dùng
        /// </summary>
        /// <param name="userId">ID người dùng</param>
        /// <returns>Danh sách đơn hàng</returns>
        [HttpPost("getOrderbyUser")]
        public async Task<ActionResult<BaseResponse<List<OrderSummaryModel>>>> GetOrdersByUser([FromBody] UserIdRequest request)
        {
            var orders = await _repository.GetOrdersByUserAsync(request.UserId);

            var result = orders.ToSummaryModelList();

            return Ok(new BaseResponse<List<OrderSummaryModel>>
            {
                StatusCode = 200,
                Success = true,
                Message = "Lấy danh sách đơn hàng thành công",
                Data = result
            });
        }


        /// <summary>
        /// Lấy chi tiết một đơn hàng cụ thể theo ID
        /// </summary>
        /// <param name="orderId">ID đơn hàng</param>
        /// <returns>Chi tiết đơn hàng</returns>
        [HttpPost("getOrderByOrderId")]
        public async Task<ActionResult<BaseResponse<OrderDetailModel>>> GetOrderById([FromBody] OrderIdRequest request)
        {
            var order = await _repository.GetOrderByIdAsync(request.OrderId);

            if (order == null)
            {
                return NotFound(new BaseResponse<OrderDetailModel>
                {
                    StatusCode = 404,
                    Success = false,
                    Message = "Không tìm thấy đơn hàng",
                    Data = null
                });
            }

            return Ok(new BaseResponse<OrderDetailModel>
            {
                StatusCode = 200,
                Success = true,
                Message = "Lấy chi tiết đơn hàng thành công",
                Data = order.ToDetailModel()
            });
        }
    }
}
