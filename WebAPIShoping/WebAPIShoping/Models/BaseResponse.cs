namespace WebAPIShoping.Models
{
    public class BaseResponse<T>
    {
        public int StatusCode { get; set; } // Mã HTTP (200, 401, 500...)
        public bool Success { get; set; } // Thành công hay không
        public string Message { get; set; } // Nội dung phản hồi
        public T Data { get; set; } // Dữ liệu trả về (VD: Token, User info

    
    }
}
