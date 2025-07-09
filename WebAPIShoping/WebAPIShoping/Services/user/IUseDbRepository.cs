using WebAPIShoping.Database;

namespace WebAPIShoping.Services.user
{
    /// <summary>
    /// Interface quản lý các thao tác với bảng UsersDb trong cơ sở dữ liệu
    /// </summary>
    public interface IUserDbRepository
    {

        /// Kiểm tra số điện thoại đã tồn tại trong database chưa
        Task<bool> IsPhoneNumberExistsAsync(string phoneNumber);


        /// Thêm người dùng mới vào database
        Task AddUserAsync(UserDb user);


        /// Tìm người dùng theo số điện thoại
        Task<UserDb?> GetUserByPhoneNumberAsync(string phoneNumber);


        /// Tìm người dùng theo ID
        Task<UserDb?> GetUserByIdAsync(Guid id);
        Task CreateUserAsync(UserDb newUser);
    }
}
