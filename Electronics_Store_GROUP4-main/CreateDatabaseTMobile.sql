-- ========================
-- CREATE DATABASE & SCHEMA
-- ========================
USE master;
GO
DROP DATABASE IF EXISTS TShop;
GO

CREATE DATABASE TShop;
GO
USE TShop;
GO

-- Roles
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY IDENTITY,
    RoleName NVARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Accounts (
    AccountID INT PRIMARY KEY IDENTITY,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    PasswordHash NVARCHAR(255),
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsActive BIT DEFAULT 1,
    RoleID INT NOT NULL FOREIGN KEY REFERENCES Roles(RoleID),
    EmailVerified BIT DEFAULT 0,
    ProfileImageURL NVARCHAR(500)
);

CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY,
    AccountID INT UNIQUE FOREIGN KEY REFERENCES Accounts(AccountID),
    FullName NVARCHAR(100),
    PhoneNumber NVARCHAR(15),
    BirthDate DATE,
    Gender NVARCHAR(10)
);

CREATE TABLE Staff (
    StaffID INT PRIMARY KEY,
    AccountID INT UNIQUE FOREIGN KEY REFERENCES Accounts(AccountID),
    FullName NVARCHAR(100),
    PhoneNumber NVARCHAR(15),
    BirthDate DATE,
    Gender NVARCHAR(10),
    Position NVARCHAR(100),
    HiredDate DATE
);

CREATE TABLE Admins (
    AdminID INT PRIMARY KEY,
    AccountID INT UNIQUE FOREIGN KEY REFERENCES Accounts(AccountID),
    FullName NVARCHAR(100),
    PhoneNumber NVARCHAR(15),
    BirthDate DATE,
    Gender NVARCHAR(10),
    Level INT DEFAULT 1
);

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY IDENTITY,
    CategoryName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(255),
    CreatedAt DATETIME DEFAULT GETDATE(),
    ImgURLLogo NVARCHAR(500),
    isActive BIT DEFAULT 1
);

CREATE TABLE CategoryDetailsGroup(
    CategoryDetailsGroupID INT PRIMARY KEY IDENTITY,
    NameCategoryDetailsGroup NVARCHAR(500) NOT NULL,
    CategoryID INT NOT NULL FOREIGN KEY REFERENCES Categories(CategoryID) ON DELETE CASCADE
);

CREATE TABLE CategoryDetails (
    CategoryDetailID INT PRIMARY KEY IDENTITY,
    CategoryID INT FOREIGN KEY REFERENCES Categories(CategoryID) ON DELETE NO ACTION,
    AttributeName NVARCHAR(100) NOT NULL,
    CategoryDetailsGroupID INT FOREIGN KEY REFERENCES CategoryDetailsGroup(CategoryDetailsGroupID) ON DELETE CASCADE
);

CREATE TABLE Brands (
    BrandID INT PRIMARY KEY IDENTITY,
    BrandName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(255),
    CategoryID INT FOREIGN KEY REFERENCES Categories(CategoryID),
    ImgURLLogo NVARCHAR(500),
    isActive BIT DEFAULT 1
);

CREATE TABLE Suppliers (
    SupplierID INT PRIMARY KEY IDENTITY,
    TaxID NVARCHAR(20) NOT NULL,
    Name NVARCHAR(255) NOT NULL,
    Email NVARCHAR(255),
    PhoneNumber NVARCHAR(50),
    Address NVARCHAR(500),
    CreatedDate DATETIME DEFAULT GETDATE(),
    LastModify DATETIME,
    Deleted BIT DEFAULT 0,
    Activate BIT DEFAULT 1,
    ContactPerson NVARCHAR(100),
    SupplyGroup NVARCHAR(100),
    Description NVARCHAR(MAX)
);

CREATE TABLE Products (
    ProductID INT PRIMARY KEY IDENTITY,
    ProductName NVARCHAR(100),
    Description NVARCHAR(MAX),
    Price DECIMAL(10, 2),
    Discount INT,
    Stock INT,
    Status NVARCHAR(100),
    SupplierID INT NULL FOREIGN KEY REFERENCES Suppliers(SupplierID) ON DELETE SET NULL,
    CategoryID INT NULL FOREIGN KEY REFERENCES Categories(CategoryID) ON DELETE SET NULL,
    BrandID INT NULL FOREIGN KEY REFERENCES Brands(BrandID) ON DELETE SET NULL,
    IsFeatured BIT,
    IsBestSeller BIT,
    IsNew BIT,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdateAt DATETIME DEFAULT GETDATE(),
    WarrantyPeriod INT,
    DiscountStartDate DATETIME NULL,
    DiscountEndDate DATETIME NULL,
    isActive BIT default 1
);

CREATE TABLE ProductImages (
    ImageID INT PRIMARY KEY IDENTITY,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE CASCADE,
    ImageURL NVARCHAR(500),
    AltText NVARCHAR(255)
);

CREATE TABLE ProductDetails (
    ProductDetailID INT PRIMARY KEY IDENTITY,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE CASCADE,
    CategoryDetailID INT FOREIGN KEY REFERENCES CategoryDetails(CategoryDetailID) ON DELETE CASCADE,
    AttributeValue NVARCHAR(255)
);

CREATE TABLE ImgProductDetails(
    ImageURL1 NVARCHAR(500),
    ImageURL2 NVARCHAR(500),
    ImageURL3 NVARCHAR(500),
    ImageURL4 NVARCHAR(500),
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE CASCADE
);

CREATE TABLE ProductVariants (
    VariantID INT PRIMARY KEY IDENTITY,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE CASCADE,
    Color NVARCHAR(50),
    Storage NVARCHAR(50),
    Quantity INT,
    Price DECIMAL(10,2),
    Discount INT DEFAULT 0,
    SKU NVARCHAR(50) UNIQUE,
    ImageURL NVARCHAR(500),
    IsActive BIT DEFAULT 1
);


CREATE TABLE Feedbacks (
    FeedbackID INT PRIMARY KEY IDENTITY,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE CASCADE,
    AccountID INT FOREIGN KEY REFERENCES Accounts(AccountID) ON DELETE CASCADE,
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    Comment NVARCHAR(MAX),
    CreatedAt DATETIME DEFAULT GETDATE(),
    IsVisible BIT DEFAULT 1
);

CREATE TABLE FeedbackReplies (
    ReplyID INT PRIMARY KEY IDENTITY,
    FeedbackID INT FOREIGN KEY REFERENCES Feedbacks(FeedbackID) ON DELETE CASCADE,
    AccountID INT FOREIGN KEY REFERENCES Accounts(AccountID) ON DELETE NO ACTION,
    ReplyContent NVARCHAR(MAX),
    RepliedAt DATETIME DEFAULT GETDATE(),
    IsPublic BIT DEFAULT 1
);

CREATE TABLE Cart (
    CartID INT PRIMARY KEY IDENTITY,
    AccountID INT UNIQUE FOREIGN KEY REFERENCES Accounts(AccountID) ON DELETE CASCADE,
    CreatedAt DATETIME DEFAULT GETDATE()
);

CREATE TABLE CartItems (
    CartItemID INT PRIMARY KEY IDENTITY,
    CartID INT FOREIGN KEY REFERENCES Cart(CartID) ON DELETE CASCADE,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE NO ACTION,
    VariantID INT NULL FOREIGN KEY REFERENCES ProductVariants(VariantID) ON DELETE NO ACTION,
    Quantity INT,
    AddedAt DATETIME DEFAULT GETDATE()
);

CREATE TABLE ProductViewHistory (
    ViewID INT PRIMARY KEY IDENTITY,
    AccountID INT FOREIGN KEY REFERENCES Accounts(AccountID) ON DELETE NO ACTION,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE CASCADE,
    ViewTime DATETIME DEFAULT GETDATE()
);

CREATE TABLE Addresses (
    AddressID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT NOT NULL,
    ProvinceName NVARCHAR(64) NOT NULL,
    DistrictName NVARCHAR(64) NOT NULL,
    WardName NVARCHAR(64) NOT NULL,
    AddressDetails NVARCHAR(255) NOT NULL,
    IsDefault BIT DEFAULT 0,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Vouchers (
    VoucherID INT PRIMARY KEY IDENTITY(1,1),
    Code NVARCHAR(50) UNIQUE NOT NULL,
    DiscountPercent INT,
    ExpiryDate DATETIME,
    MinOrderAmount DECIMAL(10,2),
    MaxDiscountAmount DECIMAL(10,2),
    UsageLimit INT,
    UsedCount INT DEFAULT 0,
    IsActive BIT DEFAULT 1,
    CreatedAt DATETIME DEFAULT GETDATE(),
    Description NVARCHAR(MAX),
    IsGlobal BIT DEFAULT 0
);

CREATE TABLE CustomerVoucher (
    CustomerID INT NOT NULL,
    VoucherID INT NOT NULL,
    ExpirationDate DATETIME,
    Quantity INT,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (VoucherID) REFERENCES Vouchers(VoucherID)
);

CREATE TABLE OrderStatus (
    ID INT PRIMARY KEY,
    [Status] NVARCHAR(50) NOT NULL
);

CREATE TABLE Orders (
    OrderID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT,
    FullName VARCHAR(100) NOT NULL,
    AddressSnapshot NTEXT NOT NULL,
    AddressID INT,
    PhoneNumber VARCHAR(15) NOT NULL,
    OrderedDate DATETIME NOT NULL,
    DeliveredDate DATETIME,
    Status INT,
    TotalAmount BIGINT,
    Discount INT,
	UpdatedAt DATETIME,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (Status) REFERENCES OrderStatus(ID),
    FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID)
);


CREATE TABLE OrderDetails (
    OrderID INT,
    ProductID INT,
    Quantity INT,
    Price BIGINT,
    PRIMARY KEY (OrderID, ProductID),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

CREATE TABLE Payments (
    PaymentID INT PRIMARY KEY IDENTITY,
    OrderID INT FOREIGN KEY REFERENCES Orders(OrderID) ON DELETE CASCADE,
    PaymentMethod NVARCHAR(50),
    PaymentStatus NVARCHAR(50),
    PaidDate DATETIME,
    Amount DECIMAL(12,2)
);

CREATE TABLE ImportStocks (
    ImportID INT PRIMARY KEY IDENTITY(1,1),
    SupplierID INT FOREIGN KEY REFERENCES Suppliers(SupplierID),
    AccountID INT FOREIGN KEY REFERENCES Accounts(AccountID),
    StaffID INT FOREIGN KEY REFERENCES Staff(StaffID),
    ImportDate DATETIME DEFAULT GETDATE(),
    TotalAmount DECIMAL(12,2),
    IsCompleted BIT DEFAULT 0
);

CREATE TABLE ImportStockDetails (
    ImportID INT FOREIGN KEY REFERENCES ImportStocks(ImportID) ON DELETE CASCADE,
    ProductID INT FOREIGN KEY REFERENCES Products(ProductID) ON DELETE NO ACTION,
    Quantity INT,
    UnitPrice DECIMAL(10,2),
    QuantityLeft INT,
    PRIMARY KEY (ImportID, ProductID)
);

CREATE TABLE Notifications (
    NotificationID INT PRIMARY KEY IDENTITY,
    AccountID INT FOREIGN KEY REFERENCES Accounts(AccountID) ON DELETE NO ACTION,
    Title NVARCHAR(255),
    Message NVARCHAR(MAX),
    Type NVARCHAR(50),
    IsRead BIT DEFAULT 0,
    CreatedAt DATETIME DEFAULT GETDATE(),
    RelatedEntityID INT NULL,
    RelatedEntityType NVARCHAR(50) NULL
);

CREATE TABLE ActivityLogs (
    LogID INT PRIMARY KEY IDENTITY,
    AccountID INT FOREIGN KEY REFERENCES Accounts(AccountID) ON DELETE NO ACTION,
    Action NVARCHAR(255),
    EntityType NVARCHAR(50),
    EntityID INT,
    Details NVARCHAR(MAX),
    IPAddress NVARCHAR(45),
    CreatedAt DATETIME DEFAULT GETDATE()
);

CREATE TABLE Promotions (
    PromotionID INT PRIMARY KEY IDENTITY,
    TargetType NVARCHAR(10) NOT NULL CHECK (TargetType IN ('BRAND', 'CATEGORY', 'PRODUCT')),
    TargetID INT NOT NULL,
    Discount INT NOT NULL CHECK (Discount BETWEEN 1 AND 100),
    StartDate DATETIME NOT NULL,
    EndDate DATETIME NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    CONSTRAINT CHK_Date CHECK (EndDate > StartDate),
    Name NVARCHAR(255) NOT NULL DEFAULT '',
    ActiveDiscount BIT NOT NULL DEFAULT 0
);

CREATE TABLE ProductRatings (
    RateID INT IDENTITY(1,1) PRIMARY KEY,
    CustomerID INT,
    ProductID INT,
    OrderID INT,
    CreatedDate DATETIME,
    Star INT,
    Comment NVARCHAR(300),
    IsDeleted BIT,
    IsRead BIT,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

CREATE TABLE RatingReplies (
    ReplyID INT IDENTITY (1,1) PRIMARY KEY,
    StaffID INT,
    RateID INT,
    Answer NVARCHAR(300),
    IsRead BIT,
    FOREIGN KEY (StaffID) REFERENCES Staff(StaffID),
    FOREIGN KEY (RateID) REFERENCES ProductRatings(RateID)
);

-- Indexes
CREATE INDEX IX_Products_CategoryID ON Products(CategoryID);
CREATE INDEX IX_Products_BrandID ON Products(BrandID);
CREATE INDEX IX_Products_Status ON Products(Status);
CREATE INDEX IX_Products_IsFeatured ON Products(IsFeatured);
CREATE INDEX IX_Products_IsBestSeller ON Products(IsBestSeller);
CREATE INDEX IX_Feedbacks_ProductID ON Feedbacks(ProductID);
CREATE INDEX IX_Feedbacks_AccountID ON Feedbacks(AccountID);
CREATE INDEX IX_ProductViewHistory_AccountID ON ProductViewHistory(AccountID);
CREATE INDEX IX_ProductViewHistory_ProductID ON ProductViewHistory(ProductID);
CREATE INDEX IX_Notifications_AccountID ON Notifications(AccountID);
CREATE INDEX IX_Notifications_IsRead ON Notifications(IsRead);
