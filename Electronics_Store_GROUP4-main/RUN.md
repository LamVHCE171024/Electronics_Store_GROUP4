## Hướng dẫn chạy dự án TShop

### 1. Yêu cầu môi trường

- **JDK**: JDK 8 (khuyến nghị, tương thích `mssql-jdbc:12.8.0.jre8` và cấu hình Maven hiện tại).
- **Maven**: Apache Maven 3.x (dùng để build dự án và tạo file `.war`).
- **SQL Server**: SQL Server (Express hoặc Developer) chạy trên `localhost:1433`.
- **Tài khoản SQL**: 
  - `username`: `sa`
  - `password`: `123456`
- **Application Server**: Apache Tomcat 9 (hoặc server Java EE tương đương hỗ trợ Servlet 4.0).

> Lưu ý: Các script SQL và code đã được cấu hình sẵn để dùng database `TShop` với `sa/123456`.

---

### 2. Chuẩn bị sau khi clone/pull

```bash
git clone https://github.com/LamVHCE171024/Electronics_Store.git
cd Electronics_Store
```

Hoặc nếu đã có sẵn:

```bash
cd D:\SP2026\SWP\TShop
git pull
```

Đảm bảo đã:

- Cài JDK 8 và thiết lập biến môi trường `JAVA_HOME`, thêm `bin` vào `PATH`.
- Cài Maven và thêm thư mục `bin` của Maven vào `PATH` (kiểm tra bằng `mvn -v`).
- Cài SQL Server, bật đăng nhập SQL (Mixed mode) và đảm bảo tài khoản `sa/123456` hoạt động.
- Cài Tomcat 9, ghi nhớ thư mục cài đặt (ví dụ `C:\tomcat9`).

---

### 3. Khởi tạo database tự động (schema + data)

Trong root của dự án đã có sẵn các script:

- `TShop.sql`: tạo database `TShop` và toàn bộ schema (bảng, khóa ngoại, ràng buộc,…).
- `InsertDTB.sql`: chèn dữ liệu mẫu (roles, accounts, categories, products,…).

#### 3.1. Tạo database và schema

Trên Windows, mở **Command Prompt** hoặc **PowerShell** với quyền đủ để kết nối SQL Server, chạy:

```bash
cd D:\SP2026\SWP\TShop

sqlcmd -S localhost -U sa -P 123456 -i TShop.sql
```

Giải thích:

- `-S localhost`: SQL Server chạy trên máy local (mặc định port 1433).
- `-U sa -P 123456`: thông tin đăng nhập do bạn cung cấp.
- `-i TShop.sql`: thực thi toàn bộ script tạo DB và bảng.

#### 3.2. Chèn dữ liệu mẫu

Sau khi schema được tạo xong, chạy:

```bash
sqlcmd -S localhost -U sa -P 123456 -i InsertDTB.sql
```

Sau hai lệnh trên:

- Database `TShop` đã được **tự động tạo**.
- Dữ liệu mẫu đã được **seed** đầy đủ để chạy project.

> Mỗi lần cần reset database (hoặc trên máy mới), chỉ cần chạy lại **hai lệnh sqlcmd** này.

---

### 4. Cấu hình kết nối database trong code

File `src/main/java/utils/DBContext.java` đã được cấu hình sẵn:

- Server: `localhost:1433`
- Database: `TShop`
- User: `sa`
- Password: `123456`

Bạn chỉ cần đảm bảo:

- SQL Server đang chạy.
- Login `sa/123456` tồn tại và có quyền trên database `TShop`.

Nếu muốn đổi user/password, sửa chuỗi `dbURL` trong `DBContext` cho phù hợp.

---

### 5. Build dự án bằng Maven

Tại root dự án:

```bash
cd D:\SP2026\SWP\TShop
mvn clean package
```

Kết quả:

- Maven tải dependency và build ra file `.war` tại: `target/TMobile-1.0-SNAPSHOT.war`.

Nếu build thất bại, kiểm tra:

- Đã cài đúng JDK 8 và Maven chưa.
- Mạng không chặn Maven tải dependency.

---

### 6. Deploy và chạy trên Tomcat

Giả sử Tomcat được cài tại `C:\tomcat9`.

#### 6.1. Copy file `.war` sang Tomcat

```bash
copy D:\SP2026\SWP\TShop\target\TMobile-1.0-SNAPSHOT.war C:\tomcat9\webapps\
```

Tomcat sẽ tự động giải nén và deploy file `.war`.

#### 6.2. Start Tomcat

```bash
cd C:\tomcat9\bin
startup.bat
```

Nếu muốn dừng server:

```bash
cd C:\tomcat9\bin
shutdown.bat
```

#### 6.3. Truy cập ứng dụng

Mở trình duyệt và truy cập:

- `http://localhost:8080/TMobile-1.0-SNAPSHOT/`

Hoặc nếu Tomcat đổi context path theo tên thư mục deploy, kiểm tra trong `C:\tomcat9\webapps` và truy cập theo đúng tên.

---

### 7. Quy trình nhanh sau khi clone/pull

Trên **máy mới** hoặc sau khi **reset môi trường**, bạn chỉ cần:

```bash
cd D:\SP2026\SWP\TShop

# 1. Tạo DB + schema
sqlcmd -S localhost -U sa -P 123456 -i TShop.sql

# 2. Seed dữ liệu mẫu
sqlcmd -S localhost -U sa -P 123456 -i InsertDTB.sql

# 3. Build project
mvn clean package

# 4. Copy .war sang Tomcat
copy target\TMobile-1.0-SNAPSHOT.war C:\tomcat9\webapps\
```

Sau đó start Tomcat:

```bash
cd C:\tomcat9\bin
startup.bat
```

Là có thể truy cập web qua `http://localhost:8080/TMobile-1.0-SNAPSHOT/`.

