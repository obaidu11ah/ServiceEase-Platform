
-- 1. CREATE DATABASE AND USE IT
CREATE DATABASE ServiceEase;
USE ServiceEase;


-- 3. USERS TABLE
CREATE TABLE Users (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Password VARCHAR(64) NOT NULL,  -- For SHA-256
    Salt VARCHAR(36) NOT NULL DEFAULT NEWID(), -- For password hashing
    Role VARCHAR(20) CHECK (Role IN ('Customer', 'ServiceProvider', 'Admin')) NOT NULL
);


-- 4. SERVICES TABLE
CREATE TABLE Services (
    ServiceID INT PRIMARY KEY IDENTITY(1,1),
    ServiceTitle NVARCHAR(100) NOT NULL,
    ServiceCategory NVARCHAR(50),
    Description NVARCHAR(MAX),
    Price DECIMAL(10,2) NOT NULL,
    IsAvailable BIT NOT NULL,
    EstimatedDuration NVARCHAR(50),
    CreatedDate DATETIME DEFAULT GETDATE(),
    LastModified DATETIME DEFAULT GETDATE(),
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    Publish BIT DEFAULT 0
);


-- 5. BOOKINGS TABLE
CREATE TABLE Booking (
    BookingID INT PRIMARY KEY IDENTITY(1,1),
    ServiceID INT,
    CustomerID INT,
    BookingDate DATETIME DEFAULT GETDATE(),
    Status BIT DEFAULT 0,  -- 0 = Pending, 1 = Delivered
    FOREIGN KEY (ServiceID) REFERENCES Services(ServiceID) ON DELETE CASCADE,
    FOREIGN KEY (CustomerID) REFERENCES Users(UserID)
);


-- 6. AUDIT LOG TABLE
CREATE TABLE ServiceAudit (
    AuditID INT IDENTITY(1,1) PRIMARY KEY,
    ServiceID INT,
    ActionType NVARCHAR(10),
    ActionDate DATETIME DEFAULT GETDATE(),
    OldData NVARCHAR(MAX),
    NewData NVARCHAR(MAX)	
);


-- 7. INDEXES (Improves SELECT query performance)
CREATE NONCLUSTERED INDEX idx_service_title ON Services(ServiceTitle);
CREATE NONCLUSTERED INDEX idx_service_category ON Services(ServiceCategory);
CREATE NONCLUSTERED INDEX idx_ServiceUserID ON Services(UserID);
--INDEXES (for faster login/signup queries)
CREATE NONCLUSTERED INDEX idx_UserEmail ON Users(Email);
CREATE NONCLUSTERED INDEX idx_UserFullName ON Users(FullName);
CREATE NONCLUSTERED INDEX idx_UserRole ON Users(Role);
-- Indexes for faster booking queries
CREATE NONCLUSTERED INDEX idx_BookingCustomerID ON Booking(CustomerID);
CREATE NONCLUSTERED INDEX idx_BookingServiceID ON Booking(ServiceID);


-- 8. TRIGGER FOR AUDIT LOGGING
CREATE TRIGGER trServiceAudit
ON Services
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;--   Iska matlab hai ke trigger jab chale, to woh rows affected ke messages (jaise "3 rows affected") nahi bhejega, taake unnecessary messages na aaye aur performance achi rahe.


    IF EXISTS (SELECT * FROM inserted) AND EXISTS (SELECT * FROM deleted)-- mtlb koi be value upate or insert hoi to update audit change kro
    BEGIN
        -- OLD DATA UPDATE ACTION
        INSERT INTO ServiceAudit (ServiceID, ActionType, OldData, NewData)
        SELECT d.ServiceID, 'UPDATE',
            JSON_OBJECT('title': d.ServiceTitle, 'category': d.ServiceCategory, 'price': d.Price, 'available': d.IsAvailable),
            JSON_OBJECT('title': i.ServiceTitle, 'category': i.ServiceCategory, 'price': i.Price, 'available': i.IsAvailable)
        FROM deleted d
        INNER JOIN inserted i ON d.ServiceID = i.ServiceID;
    END
    ELSE IF EXISTS (SELECT * FROM inserted)
    BEGIN
        -- INSERT ACTION
        INSERT INTO ServiceAudit (ServiceID, ActionType, NewData)
        SELECT i.ServiceID, 'INSERT',
            JSON_OBJECT('title': i.ServiceTitle, 'category': i.ServiceCategory, 'price': i.Price, 'available': i.IsAvailable)
        FROM inserted i;
    END
    ELSE
    BEGIN
        -- DELETE ACTION
        INSERT INTO ServiceAudit (ServiceID, ActionType, OldData)
        SELECT d.ServiceID, 'DELETE',
            JSON_OBJECT('title': d.ServiceTitle, 'category': d.ServiceCategory, 'price': d.Price, 'available': d.IsAvailable)
        FROM deleted d;
    END
END;


-- 9. LOGIN FUNCTION
CREATE FUNCTION dbo.CheckLoginFunc(
    @FullName VARCHAR(100),
    @PasswordHash VARCHAR(64),
    @Role VARCHAR(20)
)
RETURNS INT
AS
BEGIN
    DECLARE @result INT
    IF EXISTS (
        SELECT 1 FROM Users
        WHERE FullName = @FullName AND Password = @PasswordHash AND Role = @Role
    )
        SET @result = 1
    ELSE
        SET @result = 0
    RETURN @result
END;


-- 10. VALIDATION PROCEDURES
CREATE PROCEDURE dbo.ValidateServiceProvider
    @FullName VARCHAR(100),
    @PasswordHash VARCHAR(64),
    @UserID INT OUTPUT
AS
BEGIN
    SELECT @UserID = UserID
    FROM Users
    WHERE FullName = @FullName AND Password = @PasswordHash AND Role = 'ServiceProvider'

    IF @@ROWCOUNT = 0
        SET @UserID = -1
END;

-- VALIDATE CUSTOMER 
CREATE PROCEDURE dbo.ValidateCustomer
    @FullName VARCHAR(100),
    @PasswordHash VARCHAR(64),
    @UserID INT OUTPUT
AS
BEGIN
    SELECT @UserID = UserID
    FROM Users
    WHERE FullName = @FullName AND Password = @PasswordHash AND Role = 'Customer'

    IF @@ROWCOUNT = 0
        SET @UserID = -1
END;


-- 11. CONCURRENCY CONTROL: Booking Stored Procedure
CREATE PROCEDURE dbo.BookService
    @ServiceTitle NVARCHAR(100),
    @CustomerID INT
AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
    BEGIN TRANSACTION;

    BEGIN TRY
        DECLARE @ServiceID INT, @IsAvailable BIT;

        -- Row-level locking using UPDLOCK
        SELECT @ServiceID = ServiceID, @IsAvailable = IsAvailable
        FROM Services WITH (UPDLOCK)
        WHERE ServiceTitle = @ServiceTitle;

        IF @IsAvailable = 0
        BEGIN
            THROW 51000, 'Service is already booked!', 1;
        END

        -- Update availability
        UPDATE Services SET IsAvailable = 0 WHERE ServiceID = @ServiceID;

        -- Insert booking
        INSERT INTO Booking (ServiceID, CustomerID)
        VALUES (@ServiceID, @CustomerID);

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;


-- 12. USER-DEFINED FUNCTIONS
-- Provider Dashboard
CREATE FUNCTION dbo.GetServicesByUser(@UserID INT)
RETURNS TABLE
AS
RETURN (
    SELECT 
        ServiceTitle AS [Service Title],
        ServiceCategory AS [Service Category],
        Description,
        Price,
        CASE WHEN IsAvailable = 1 THEN 'Yes' ELSE 'No' END AS [Avail],
        EstimatedDuration AS [Estimate Time],
        CreatedDate AS [Created Date],
        LastModified AS [Last Modified],
        CASE WHEN Publish = 0 THEN 'Live' ELSE '' END AS [Live]
    FROM Services
    WHERE UserID = @UserID
);


-- Customer View: Show All Services
CREATE FUNCTION dbo.GetServicesShow()
RETURNS TABLE
AS
RETURN (
    SELECT 
        ServiceTitle AS [Service Title],
        ServiceCategory AS [Service Category],
        Description,
        Price,
        CASE WHEN IsAvailable = 1 THEN 'Yes' ELSE 'No' END AS [Avail],
        EstimatedDuration AS [Estimate Time]
    FROM Services
);


-- Provider Booked Services
CREATE FUNCTION dbo.GetBookedServicesByProvider(@ProviderUserID INT)
RETURNS TABLE
AS
RETURN (
    SELECT 
        u.FullName AS [CustomerName],
        s.ServiceTitle AS [BookedService],
        s.Price,
        b.BookingDate
    FROM Booking b
    INNER JOIN Services s ON b.ServiceID = s.ServiceID
    INNER JOIN Users u ON b.CustomerID = u.UserID
    WHERE s.UserID = @ProviderUserID
);


-- Customer Bookings
CREATE FUNCTION dbo.GetBookedServicesByCustomer(@CustomerID INT)
RETURNS TABLE
AS
RETURN (
    SELECT 
        s.ServiceTitle AS [BookedService],
        sp.FullName AS [ProviderName],
        s.Price,
        b.BookingDate,
        CASE WHEN b.Status = 1 THEN 'Delivered' ELSE 'Dilevred' END AS DeliveryStatus
    FROM Booking b
    INNER JOIN Services s ON b.ServiceID = s.ServiceID
    INNER JOIN Users sp ON s.UserID = sp.UserID
    WHERE b.CustomerID = @CustomerID
);



--FOR ADMIN TESTING
SELECT * FROM Services;
SELECT * FROM Booking;
SELECT * FROM Users;
SELECT * FROM ServiceAudit ORDER BY AuditID DESC; --trigger insert happen

-- Maintain Database[ServiceEase] Recovery.
SELECT name, recovery_model_desc
FROM sys.databases
WHERE name = 'ServiceEase';

-- Set to SIMPLE (recommended for testing or student projects)
ALTER DATABASE ServiceEase
SET RECOVERY FULL;

-- Full database backup
BACKUP DATABASE ServiceEase
TO DISK = 'C:\Backups\ServiceEase_Full.bak';

