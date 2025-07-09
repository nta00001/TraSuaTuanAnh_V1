namespace WebAPIShoping.Models
{
    public class User
    {
        public string Id { get; set; } 
        public string Username { get; set; }
        public string PhoneNumber { get; set; }
        public string PasswordHash { get; set; } // Mật khẩu đã băm
    }
}
