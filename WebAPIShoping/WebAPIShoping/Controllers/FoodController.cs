using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using WebAPIShoping.Models;
using WebAPIShoping.Services.food;

namespace WebAPIShoping.Controllers
{
    [ApiController]
    [Route("api/food")]
    [Authorize]  // Yêu cầu phải có token mới được truy cập tất cả action bên trong controller này
    public class FoodController : ControllerBase
    {
        private readonly IFoodRepository _foodRepository;

        public FoodController(IFoodRepository foodRepository)
        {
            _foodRepository = foodRepository;
        }

        /// <summary>
        /// Lấy danh sách tất cả món ăn
        /// </summary>
        [HttpGet("getAllFood")]
        public async Task<IActionResult> GetAllFoods()
        {
            var foods = await _foodRepository.GetAllFoodsAsync();
            if (foods == null || !foods.Any())
            {
                return Ok(new BaseResponse<List<FoodModel>>
                {
                    StatusCode = 404,
                    Success = false,
                    Message = "Không tìm thấy món ăn nào.",
                    Data = null
                });
            }

            return Ok(new BaseResponse<List<FoodModel>>
            {
                StatusCode = 200,
                Success = true,
                Message = "Lấy danh sách món ăn thành công.",
                Data = foods
            });
        }
        /// <summary>
        /// Lấy danh sách các món hot
        /// </summary>

        [HttpGet("getHotFoods")] 
        public async Task<IActionResult> GetHotFoods() 
        {
            var foods = await _foodRepository.GetAllFoodsAsync();
            if (foods == null || !foods.Any())
            {
                return Ok(new BaseResponse<List<FoodModel>>
                {
                    StatusCode = 404,
                    Success = false,
                    Message = "No hot foods found.",
                    Data = null
                });
            }

            var hotFoods = foods.Take(4).ToList(); 

            return Ok(new BaseResponse<List<FoodModel>>
            {
                StatusCode = 200,
                Success = true,
                Message = "Successfully retrieved hot foods.",
                Data = hotFoods
            });
        }


        /// <summary>
        /// Lấy thông tin một món ăn theo ID
        /// </summary>
        [HttpGet("getFoodById/{id}")]
        public async Task<IActionResult> GetFoodById(Guid id)
        {
            var food = await _foodRepository.GetFoodByIdAsync(id);
            if (food == null)
            {
                return Ok(new BaseResponse<FoodModel>
                {
                    StatusCode = 404,
                    Success = false,
                    Message = "Không tìm thấy món ăn.",
                    Data = null
                });
            }

            return Ok(new BaseResponse<FoodModel>
            {
                StatusCode = 200,
                Success = true,
                Message = "Lấy thông tin món ăn thành công.",
                Data = food
            });
        }

        /// <summary>
        /// Tạo món ăn mới.
        /// </summary>
        [HttpPost("addFoodNew")]
        public async Task<IActionResult> CreateFood([FromBody] FoodModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(new BaseResponse<object>
                {
                    StatusCode = 400,
                    Success = false,
                    Message = "Dữ liệu không hợp lệ.",
                    Data = null
                });
            }

            try
            {
                var createdFood = await _foodRepository.CreateFoodAsync(model);
                return Ok(new BaseResponse<FoodModel>
                {
                    StatusCode = 201,
                    Success = true,
                    Message = "Tạo món ăn thành công.",
                    Data = createdFood
                });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new BaseResponse<object>
                {
                    StatusCode = 500,
                    Success = false,
                    Message = "Có lỗi xảy ra khi tạo món ăn: " + ex.Message,
                    Data = null
                });
            }
        }
    }
}