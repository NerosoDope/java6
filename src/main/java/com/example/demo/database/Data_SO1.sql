USE [SO1Smartphone];
GO
-- Insert data into Categories
INSERT INTO Categories (name, description, created_at)
VALUES 

('Iphone', N'Dòng sản phẩm điện thoại thông minh của Apple', GETDATE()),
('Samsung', N'Dòng sản phẩm điện thoại thông minh của Samsung', GETDATE()),
('Realme', N'Dòng sản phẩm điện thoại thông minh của Realme', GETDATE()),
('Xiaomi', N'Dòng sản phẩm điện thoại thông minh của Xiaomi', GETDATE()),
('Oppo', N'Dòng sản phẩm điện thoại thông minh của Oppo', GETDATE()),
('Vivo', N'Dòng sản phẩm điện thoại thông minh của Vivo', GETDATE()),
('Sony', N'Dòng sản phẩm điện thoại thông minh của Sony', GETDATE());



-- Insert data into Products
INSERT INTO Products (name, description, price, stock_quantity, category_id, image_url, status, created_at)
VALUES

('iPhone 14', N'iPhone 14 với thiết kế đẹp mắt và hiệu năng cao', 24990000, 50, 1, 'huaweimate40.jpg', 'available', GETDATE()),
('iPhone 13', N'iPhone 13, một trong những model phổ biến nhất của Apple', 19990000, 40, 1, 'huaweinova7.jpg', 'available', GETDATE()),
('iPhone SE 2023', N'iPhone SE mới nhất với giá phải chăng', 10990000, 30, 1, 'huaweinovay61.jpg', 'available', GETDATE()),
('Samsung Galaxy S23', N'Samsung Galaxy S23 với màn hình Dynamic AMOLED', 21990000, 45, 2, 'iphone14pm.png', 'notAvailable', GETDATE()),
('Samsung Galaxy Z Fold4', N'Samsung Galaxy Z Fold4 với thiết kế gập đột phá', 41990000, 15, 2, 'iphone15pm.png', 'available', GETDATE()),
('Samsung Galaxy A53', N'Điện thoại tầm trung với cấu hình mạnh mẽ', 8990000, 60, 2, 'iphone16pm.jpg', 'notAvailable', GETDATE()),
('Realme GT Neo 3', N'Realme GT Neo 3 với hiệu năng vượt trội', 10990000, 35, 3, 'opporeno12.png', 'available', GETDATE()),
('Realme C35', N'Realme C35, lựa chọn phổ thông đáng giá', 3990000, 80, 3, 'samsungA05.jpg', 'available', GETDATE()),
('Xiaomi 13 Pro', N'Xiaomi 13 Pro với camera Leica', 17990000, 25, 4, 'samsungA16.jpg', 'notAvailable', GETDATE()),
('Xiaomi Redmi Note 12', N'Dòng Redmi Note phổ biến của Xiaomi', 5990000, 70, 4, 'samsungA53.jpg', 'available', GETDATE()),
('Oppo Reno8', N'Oppo Reno8 với thiết kế hiện đại', 10990000, 50, 5, 'oppoa3.png', 'available', GETDATE()),
('Vivo V25 Pro', N'Vivo V25 Pro, dòng điện thoại chuyên chụp ảnh', 11990000, 40, 6, 'oppoa53s.jpg', 'available', GETDATE()),
('Sony Xperia 1 IV', N'Sony Xperia 1 IV với màn hình 4K OLED', 28990000, 10, 7, 'oppoa73.jpg', 'notAvailable', GETDATE()),
('iPhone 14', 'iPhone 14 với thiết kế đẹp mắt và hiệu năng cao', 24990000, 50, 1, 'huaweimate40.jpg', 'available', GETDATE()),
('iPhone 13', 'iPhone 13, một trong những model phổ biến nhất của Apple', 19990000, 40, 1, 'huaweinova7.jpg', 'available', GETDATE()),
('iPhone SE 2023', 'iPhone SE mới nhất với giá phải chăng', 10990000, 30, 1, 'huaweinovay61.jpg', 'available', GETDATE()),
('iPhone SE 2023', 'iPhone SE mới nhất với giá phải chăng', 10990000, 30, 1, 'huaweinovay61.jpg', 'available', GETDATE()),
('iPhone SE 2023', 'iPhone SE mới nhất với giá phải chăng', 10990000, 30, 1, 'huaweinovay61.jpg', 'notAvailable', GETDATE()),
('Samsung Galaxy S23', 'Samsung Galaxy S23 với màn hình Dynamic AMOLED', 21990000, 45, 2, 'iphone14pm.png', 'available', GETDATE()),
('Samsung Galaxy Z Fold4', 'Samsung Galaxy Z Fold4 với thiết kế gập đột phá', 41990000, 15, 2, 'iphone15pm.png', 'available', GETDATE()),
('Samsung Galaxy A53', 'Điện thoại tầm trung với cấu hình mạnh mẽ', 8990000, 60, 2, 'iphone16pm.jpg', 'available', GETDATE()),
('Realme GT Neo 3', 'Realme GT Neo 3 với hiệu năng vượt trội', 10990000, 35, 3, 'opporeno12.png', 'available', GETDATE()),
('Realme C35', 'Realme C35, lựa chọn phổ thông đáng giá', 3990000, 80, 3, 'samsungA05.jpg', 'notAvailable', GETDATE()),
('Xiaomi 13 Pro', 'Xiaomi 13 Pro với camera Leica', 17990000, 25, 4, 'samsungA16.jpg', 'available', GETDATE()),
('Xiaomi Redmi Note 12', 'Dòng Redmi Note phổ biến của Xiaomi', 5990000, 70, 4, 'samsungA53.jpg', 'available', GETDATE()),
('Oppo Reno8', 'Oppo Reno8 với thiết kế hiện đại', 10990000, 50, 5, 'oppoa3.png', 'notAvailable', GETDATE()),
('Oppo Reno8', 'Oppo Reno8 với thiết kế hiện đại', 10990000, 50, 5, 'oppoa3.png', 'available', GETDATE()),
('Oppo Reno8', 'Oppo Reno8 với thiết kế hiện đại', 10990000, 50, 5, 'oppoa3.png', 'notAvailable', GETDATE()),
('Oppo Reno8', 'Oppo Reno8 với thiết kế hiện đại', 10990000, 50, 5, 'oppoa3.png', 'available', GETDATE()),
('Oppo Reno8', 'Oppo Reno8 với thiết kế hiện đại', 10990000, 50, 5, 'oppoa3.png', 'available', GETDATE()),
('Vivo V25 Pro', 'Vivo V25 Pro, dòng điện thoại chuyên chụp ảnh', 11990000, 40, 6, 'oppoa53s.jpg', 'available', GETDATE()),
('Sony Xperia 1 IV', 'Sony Xperia 1 IV với màn hình 4K OLED', 28990000, 10, 7, 'oppoa73.jpg', 'notAvailable', GETDATE());


-- Insert data into Users
INSERT INTO Users (username, full_name , password, email, phone, status, role, created_at)
VALUES
('admin1', N'Nguyễn Hùng', '$2b$12$e8FcPoAx/faV9b5R0.FWx.OXX/EJ.KSJiT/BoEmz7Z2fF5tgWGP32', 'nguyenhung1@gmail.com', '0987654321','active', 'admin', GETDATE()),
('lethithuy', N'Lê Thị Thùy','$2b$12$e8FcPoAx/faV9b5R0.FWx.OXX/EJ.KSJiT/BoEmz7Z2fF5tgWGP32', 'lethithuy@yahoo.com', '0934567890','active', 'user', GETDATE()),
('tranvanhoang', N'Trần Văn Hoàng','$2b$12$e8FcPoAx/faV9b5R0.FWx.OXX/EJ.KSJiT/BoEmz7Z2fF5tgWGP32', 'tranvanhoang@outlook.com', '0912345678','notActive', 'user', GETDATE()),
('phamtuananh', N'Phạm Tuấn Anh','$2b$12$e8FcPoAx/faV9b5R0.FWx.OXX/EJ.KSJiT/BoEmz7Z2fF5tgWGP32', 'phamtuananh@gmail.com', '0976543210','active', 'user', GETDATE());

-- Insert data into Carts
INSERT INTO Carts (user_id, created_at)
VALUES
(2, GETDATE()),
(3, GETDATE()),
(4, GETDATE());

-- Insert data into Cart_Items
INSERT INTO Cart_Items (cart_id, product_id, quantity, created_at)
VALUES
(1, 1, 2, GETDATE()),
(1, 5, 1, GETDATE()),
(2, 8, 3, GETDATE()),
(3, 10, 1, GETDATE());

-- Insert data into Orders
INSERT INTO Orders (user_id, status, total_price, address, created_at)
VALUES
(2, 'DA_NHAN', 50980000, N'123 Đường Lê Lợi, Hà Nội', GETDATE()),
(3, 'TRA_HANG', 17990000, N'456 Đường Nguyễn Huệ, TP.HCM', GETDATE());

INSERT INTO Payment (order_id, status, type, content_payment)
VALUES
(1, 'CHUA_THANH_TOAN', 'OFFLINE', '1_1_dfdsv'),
(2, 'DA_THANH_TOAN', 'ONLINE', '2_2_dfsdf');


-- Insert data into Order_Items
INSERT INTO Order_Items (order_id, product_id, quantity, price)
VALUES
(1, 1, 2, 24990000),
(1, 2, 1, 19990000),
(2, 10, 1, 5990000);
