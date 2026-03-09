# TShop - Quick Start Batch Files

Danh sách các file batch để chạy dự án TShop một cách nhanh chóng.

## Chuẩn bị trước

Trước khi chạy các batch file, hãy đảm bảo:

1. **JDK 17** được cài đặt tại: `C:\Program Files\Java\jdk-17`
2. **Maven** được cài đặt tại: `C:\apache-maven-3.9.13`
3. **SQL Server** đang chạy trên `localhost:1433`
4. **Tomcat 9** được cài đặt tại: `D:\SU25\SWP\apache-tomcat-9.0.106-windows-x64\apache-tomcat-9.0.106`
5. **Tài khoản SQL Server**: `sa` / `123456`

> Nếu đường dẫn khác, sửa biến môi trường trong các file `.bat` tương ứng

---

## Danh sách Batch Files

### 1️⃣ **quick-start.bat** - Chạy Toàn Bộ Quy Trình (Khuyên Dùng)

**Chức năng:** Toàn bộ từ đầu - setup DB → build → deploy → start Tomcat

```bash
quick-start.bat
```

✅ **Nên dùng khi:**
- Lần đầu tiên chạy dự án
- Máy mới hoặc làm sạch toàn bộ
- Muốn setup tất cả trong một lệnh

---

### 2️⃣ **setup-database.bat** - Khởi Tạo Database

**Chức năng:** Tạo schema + insert dữ liệu mẫu

```bash
setup-database.bat
```

✅ **Nên dùng khi:**
- Chỉ muốn setup lại database
- Database bị xóa hoặc corrupt

---

### 3️⃣ **build.bat** - Build Project

**Chức năng:** Compile code với Maven (tạo WAR file)

```bash
build.bat
```

✅ **Nên dùng khi:**
- Sửa code và muốn build lại
- Chỉ muốn compile mà chưa deploy

---

### 4️⃣ **deploy.bat** - Deploy & Start Tomcat

**Chức năng:** Copy WAR sang Tomcat rồi start server

```bash
deploy.bat
```

✅ **Nên dùng khi:**
- Sau khi build thành công
- Muốn restart server với code mới

**Yêu cầu:** WAR file phải tồn tại (`target/TMobile-1.0-SNAPSHOT.war`)

---

### 5️⃣ **stop.bat** - Dừng Tomcat

**Chức năng:** Dừng server Tomcat

```bash
stop.bat
```

✅ **Nên dùng khi:**
- Cần dừng server
- Muốn shutdown trước khi tạo backup

---

### 6️⃣ **reset-database.bat** - Reset Database (Drop & Recreate)

**Chức năng:** Xóa DB cũ rồi tạo mới từ đầu

```bash
reset-database.bat
```

⚠️ **Cảnh báo:** Sẽ xóa TOÀN BỘ dữ liệu hiện tại!

✅ **Nên dùng khi:**
- Cần xóa sạch dữ liệu cũ
- Database bị lỗi không sửa được

---

## Quy Trình Chạy Dự Án

### Lần Đầu Tiên
```bash
quick-start.bat
```
→ Xong! Mở browser: http://localhost:8080/TMobile-1.0-SNAPSHOT/

---

### Lần Sau (Chỉ Sửa Code)
```bash
build.bat
deploy.bat
```

---

### Sửa Code + Database
```bash
build.bat
reset-database.bat
deploy.bat
```

---

### Muốn Dừng & Restart
```bash
stop.bat
deploy.bat
```

---

## Tài Khoản Test

### Admin Account
- **Email:** admin@example.com
- **Password:** 123456

### Customer Account
- **Email:** customer@example.com
- **Password:** 123456

---

## Khắc Phục Lỗi

### ❌ "mvn is not recognized"
→ Sửa `MAVEN_HOME` trong batch file hoặc thêm Maven vào PATH

### ❌ "JAVA_HOME is not set"
→ Sửa đường dẫn JDK trong batch file

### ❌ "SQL Server connection failed"
→ Kiểm tra SQL Server có chạy không, user `sa/123456` có đúng không

### ❌ "Tomcat not found"
→ Sửa `TOMCAT_HOME` hoặc cài đặt Tomcat 9

---

## Liên Hệ

Nếu have issues, check file RUN.md hoặc liên hệ team leader.
