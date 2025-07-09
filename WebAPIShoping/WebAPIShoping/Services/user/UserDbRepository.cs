using Microsoft.EntityFrameworkCore;
using WebAPIShoping.Database;

namespace WebAPIShoping.Services.user
{
    /// <summary>
    /// Lớp triển khai interface IUserDbRepository để thao tác với bảng UsersDb
    /// </summary>
    public class UserDbRepository : IUserDbRepository
    {
        private readonly MyDbContext _context;

        public UserDbRepository(MyDbContext context)
        {
            _context = context;
        }

        public async Task<bool> IsPhoneNumberExistsAsync(string phoneNumber)
        {
            return await _context.Users.AnyAsync(u => u.PhoneNumber == phoneNumber);
        }

        public async Task AddUserAsync(UserDb user)
        {
            _context.Users.Add(user);
            await _context.SaveChangesAsync();
        }

        public async Task<UserDb?> GetUserByPhoneNumberAsync(string phoneNumber)
        {
            return await _context.Users.FirstOrDefaultAsync(u => u.PhoneNumber == phoneNumber);
        }

        public async Task<UserDb?> GetUserByIdAsync(Guid id)
        {
            return await _context.Users.FindAsync(id);
        }

        public async Task CreateUserAsync(UserDb user)
        {
            _context.Users.Add(user);       
            await _context.SaveChangesAsync();
        }
    }
}