# 🍹 Kotlin Android + Web API (.NET 5) – Ứng dụng Mua Sắm Trà Sữa

Dự án gồm hai phần:
- 📱 **Ứng dụng Android** viết bằng Kotlin
- 🌐 **Backend Web API** viết bằng ASP.NET Core (.NET 5)

---

## 📱 Android App – `TraSuaTuanAnh`

### 🔧 Công nghệ & Thư viện chính

| Công nghệ                 | Mô tả |
|---------------------------|------|
| **Ngôn ngữ**              | Kotlin |
| **Firebase**              | Auth, Realtime Database, Firestore, Analytics |
| **Retrofit2 + GSON**      | Giao tiếp REST API, parse JSON |
| **Glide**                 | Tải & hiển thị ảnh từ URL |
| **Google Maps SDK**       | Hiển thị bản đồ, định vị |
| **ZXing**                 | Quét mã QR / Barcode |
| **MPAndroidChart**        | Hiển thị biểu đồ thống kê |
| **DotsIndicator**         | Indicator dạng chấm dưới ViewPager2 |
| **AndroidX Security**     | Mã hóa dữ liệu nhạy cảm |
| **ViewBinding / DataBinding** | Kết nối UI & dữ liệu |
| **LiveData / StateFlow**  | Quản lý trạng thái dữ liệu một cách phản ứng (lifecycle-aware) |

### 🏗️ Kiến trúc ứng dụng

Ứng dụng sử dụng mô hình **MVP (Model - View - Presenter)**:

- **Model**: quản lý dữ liệu & xử lý nghiệp vụ (Firebase, API, DB)
- **View**: Fragment/Activity chỉ hiển thị dữ liệu, không xử lý logic
- **Presenter**: trung gian điều phối, xử lý logic và gọi đến Model

> Kết hợp với `LiveData` và `StateFlow` để cập nhật dữ liệu UI một cách tự động, hiện đại và dễ test.


### ⚙️ Cấu hình hệ thống

- `minSdk`: 24  
- `targetSdk`: 33  
- `compileSdk`: 33  
- Dùng `kotlin-parcelize` để truyền object qua `Intent`

---

## 🌐 Web API – `WebAPIShoping`

### 🔧 Công nghệ sử dụng

| Thành phần                     | Mô tả |
|--------------------------------|------|
| **ASP.NET Core Web API (.NET 5)** | Backend RESTful |
| **Entity Framework Core + MySQL** | ORM truy cập cơ sở dữ liệu |
| **JWT Authentication**        | Xác thực người dùng qua token |
| **Swagger (Swashbuckle)**     | Tài liệu & giao diện test API |
| **Ngrok**                     | Expose API ra Internet để test |
| **Service / Repository Pattern** | Tách biệt logic, dễ test & mở rộng |

### 🔩 Thành phần chính

- `Startup.cs` – Cấu hình JWT, DI, Swagger, DbContext
- `Program.cs` – Khởi động server + ngrok
- `appsettings.json` – Cấu hình DB, JWT, môi trường

---

## 🚀 Hướng dẫn chạy

### 1. Clone dự án
```bash
git clone https://github.com/nta00001/TraSuaTuanAnh_V1
