-- T?o b?ng UserDtls
Create database JAVA5;
drop database JAVA5;
use master;
CREATE TABLE UserDtls (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255),
    mobileNumber VARCHAR(15),
    email VARCHAR(255),
    address NVARCHAR(500),
    city NVARCHAR(255),
    stateN NVARCHAR(255),
    pincode VARCHAR(10),
    password VARCHAR(255),
    profileImage VARCHAR(255),
    role VARCHAR(50),
    isEnable BIT,
    accountNonLocked BIT,
    failedAttempt INT,
    lockTime DATETIME,
    resetToken VARCHAR(255)
);

-- T?o b?ng Category
CREATE TABLE Category (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255),
    imageName VARCHAR(255),
    isActive BIT
);

-- T?o b?ng Product
CREATE TABLE Product (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(500),
    description NVARCHAR(max),
    category NVARCHAR(255),
    price DECIMAL(18, 2),
    stock INT,
    image VARCHAR(255),
    discount INT,
    discountPrice DECIMAL(18, 2),
    isActive BIT
);

-- T?o b?ng OrderAddress
CREATE TABLE OrderAddress (
    id INT IDENTITY(1,1) PRIMARY KEY,
    firstName NVARCHAR(255),
    lastName NVARCHAR(255),
    email VARCHAR(255),
    mobileNo VARCHAR(15),
    address NVARCHAR(500),
    city NVARCHAR(255),
    state NVARCHAR(255),
    pincode VARCHAR(10)
);

-- T?o b?ng ProductOrder
CREATE TABLE ProductOrder (
    id INT IDENTITY(1,1) PRIMARY KEY,
    orderId VARCHAR(50),
    orderDate DATE,
    productId INT,
    price DECIMAL(18, 2),
    quantity INT,
    userId INT,
    status NVARCHAR(50),
    paymentType VARCHAR(50),
    orderAddressId INT,
    FOREIGN KEY (productId) REFERENCES Product(id),
    FOREIGN KEY (userId) REFERENCES UserDtls(id),
    FOREIGN KEY (orderAddressId) REFERENCES OrderAddress(id)
);

-- T?o b?ng Cart
CREATE TABLE Cart (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT,
    productId INT,
    quantity INT,
    totalPrice DECIMAL(18, 2),
    totalOrderPrice DECIMAL(18, 2),
    FOREIGN KEY (userId) REFERENCES UserDtls(id),
    FOREIGN KEY (productId) REFERENCES Product(id)
);
-- Th�m d? li?u cho b?ng Category
INSERT INTO Category (name, imageName, isActive)  
VALUES  
(N'�i?n tho?i', 'phone.jpg', 1),  
(N'M�y t�nh b?ng', 'tablet.jpg', 1),  
(N'Ph? ki?n', 'accessory.jpg', 1);


-- Th�m d? li?u cho b?ng Product
INSERT INTO Product (title, description, category, price, stock, image, discount, discountPrice, isActive)
VALUES 
(N'�i?n tho?i iPhone 14 Pro Max', N'M�n h?nh OLED 6.7 inch, chip A16 Bionic, camera Pro Max 48MP', N'�i?n t?', 35000000, 20, 'iphone_14.jpg', 5, 33250000, 1),
(N'Samsung Galaxy S23 Ultra', N'M�n h?nh 6.8 inch, camera 200MP, pin 5000mAh', N'�i?n t?', 32000000, 30, 'samsung_s23.jpg', 8, 29440000, 1),
(N'MacBook Pro 16 inch M2', N'Chip Apple M2 Max, RAM 32GB, SSD 1TB', N'�i?n t?', 70000000, 10, 'macbook_pro.jpg', 10, 63000000, 1),
(N'Laptop Dell XPS 13 Plus', N'CPU Intel Core i7 Gen 12, RAM 16GB, SSD 512GB', N'�i?n t?', 45000000, 15, 'dell_xps.jpg', 12, 39600000, 1),
(N'Smart TV LG OLED 65 inch', N'�? ph�n gi?i 4K, c�ng ngh? AI ThinQ, HDR10 Pro', N'�i?n t?', 60000000, 8, 'lg_oled_tv.jpg', 15, 51000000, 1),
(N'Tai nghe Sony WH-1000XM5', N'Ch?ng ?n v�?t tr?i, pin 30 gi?, k?t n?i Bluetooth', N'�i?n t?', 9500000, 50, 'sony_xm5.jpg', 10, 8550000, 1),
(N'Loa JBL Charge 5', N'C�ng su?t l?n, ch?ng n�?c IP67, pin 20 gi?', N'�i?n t?', 4500000, 100, 'jbl_charge5.jpg', 5, 4275000, 1),
(N'M�y ?nh Canon EOS R6', N'C?m bi?n CMOS Full-Frame 20.1MP, quay phim 4K', N'�i?n t?', 55000000, 12, 'canon_r6.jpg', 8, 50600000, 1),
(N'Smartwatch Apple Watch Series 8', N'Theo d?i s?c kh?e, GPS, ch?ng n�?c', N'�i?n t?', 12000000, 25, 'apple_watch.jpg', 7, 11160000, 1),
(N'Router Wi-Fi ASUS RT-AX86U', N'Chu?n Wi-Fi 6, b�ng th�ng cao, b?o m?t AiProtection', N'�i?n t?', 6000000, 40, 'asus_router.jpg', 12, 5280000, 1), 
(N'?p l�ng iPhone 14 Pro Max Spigen', N'?p l�ng ch?ng s?c, b?o v? 360 �?', N'Ph? ki?n', 600000, 100, 'oplung_spigen.jpg', 5, 570000, 1),  
(N'Tai nghe Bluetooth Samsung Galaxy Buds 2 Pro', '�m thanh Hi-Fi, ch?ng ?n ch? �?ng', N'Ph? ki?n', 5000000, 50, 'samsung_buds.jpg', 10, 4500000, 1),  
(N'C�p s?c nhanh Anker Powerline+ USB-C', 'H? tr? s?c nhanh Power Delivery 60W', N'Ph? ki?n', 350000, 150, 'anker_cable.jpg', 7, 325500, 1),  
(N'Pin s?c d? ph?ng Xiaomi 20000mAh', N'H? tr? s?c nhanh 18W, 2 c?ng USB', N'Ph? ki?n', 700000, 70, 'xiaomi_powerbank.jpg', 10, 630000, 1),  
(N'K�nh c�?ng l?c iPhone 14 Pro Max Nillkin', 'Ch?ng tr?y x�?c, �? c?ng 9H', N'Ph? ki?n', 300000, 120, 'kinh_cuong_luc.jpg', 5, 285000, 1),  
(N'Gi� �? �i?n tho?i Ugreen', N'Ch?t li?u h?p kim nh�m, xoay 360 �?', N'Ph? ki?n', 400000, 60, 'gia_do_ugreen.jpg', 8, 368000, 1),  
(N'Tai nghe Bluetooth Apple AirPods Pro 2', N'Ch?ng ?n ch? �?ng, �m thanh kh�ng gian', N'Ph? ki?n', 6500000, 30, 'airpods_pro2.jpg', 10, 5850000, 1),  
(N'S?c nhanh Apple 20W USB-C', N'H? tr? s?c nhanh cho iPhone v� iPad', N'Ph? ki?n', 800000, 90, 'apple_charger.jpg', 10, 720000, 1),  
(N'G?y ch?p ?nh Bluetooth Xiaomi', N'K?t n?i Bluetooth, c� tripod', N'Ph? ki?n', 500000, 40, 'xiaomi_selfie_stick.jpg', 10, 450000, 1),  
(N'�i?n tho?i Xiaomi 13 Pro', N'M�n h?nh AMOLED 6.73 inch, Snapdragon 8 Gen 2', N'�i?n t?', 27000000, 25, 'xiaomi_13pro.jpg', 8, 24840000, 1),  
(N'Tai nghe Bluetooth Sony WF-1000XM4', N'Ch?ng ?n ch? �?ng, pin 24 gi?', N'Ph? ki?n', 5990000, 45, 'sony_wf1000xm4.jpg', 12, 5271200, 1),  
(N'?p l�ng Samsung Galaxy S23 Ultra Nillkin', N'Ch?ng s?c, ch?ng b�m v�n tay', N'Ph? ki?n', 550000, 100, 'oplung_samsung.jpg', 10, 495000, 1),  
(N'K�nh c�?ng l?c Samsung Galaxy S23 Ultra', N'Ch?ng tr?y x�?c, �? c?ng 9H', N'Ph? ki?n', 350000, 90, 'kinh_samsung.jpg', 8, 322000, 1),  
(N'Pin d? ph?ng Anker 10000mAh PowerCore', N'H? tr? s?c nhanh PowerIQ, nh? g?n', N'Ph? ki?n', 890000, 60, 'anker_powercore.jpg', 10, 801000, 1),  
(N'C�p s?c nhanh Baseus USB-C 100W', N'H? tr? s?c nhanh PD, b?c d� ch?c ch?n', N'Ph? ki?n', 450000, 80, 'baseus_cable.jpg', 7, 418500, 1),  
(N'S?c kh�ng d�y Belkin 15W', N'H? tr? s?c nhanh cho iPhone, Samsung', N'Ph? ki?n', 1200000, 50, 'belkin_wireless_charger.jpg', 10, 1080000, 1),  
(N'Gi� �? �i?n tho?i Baseus Gravity', N'G?n xe h�i, xoay 360 �?', N'Ph? ki?n', 490000, 70, 'baseus_car_mount.jpg', 8, 450800, 1),  
(N'?p l�ng trong su?t UAG iPhone 14 Pro Max', N'B?o v? ch?ng s?c ti�u chu?n qu�n �?i', N'Ph? ki?n', 990000, 30, 'uag_case.jpg', 12, 871200, 1),  
(N'Tai nghe Bluetooth JBL Tune 230NC', N'Ch?ng ?n, �m bass m?nh m?', N'Ph? ki?n', 2790000, 40, 'jbl_tune.jpg', 10, 2511000, 1),  
(N'�i?n tho?i OPPO Find X6 Pro', N'M�n h?nh AMOLED 6.82 inch, Snapdragon 8 Gen 2', N'�i?n t?', 28000000, 20, 'oppo_findx6.jpg', 8, 25760000, 1), 
(N'Tai nghe Bluetooth AirPods Pro 2', N'Ch?ng ?n ch? �?ng, �m thanh kh�ng gian', N'Ph? ki?n', 6990000, 30, 'airpods_pro2.jpg', 10, 6291000, 1),  
(N'S?c nhanh Apple 20W USB-C', N'H? tr? s?c nhanh cho iPhone v� iPad', N'Ph? ki?n', 950000, 50, 'apple_20w_charger.jpg', 8, 874000, 1),  
(N'C�p Lightning Apple MFi', N'Ch?ng nh?n MFi, b?n b?, an to�n', N'Ph? ki?n', 450000, 70, 'apple_lightning_cable.jpg', 10, 405000, 1),  
(N'Pin d? ph?ng Xiaomi 20000mAh', N'H? tr? s?c nhanh, c?ng USB-C', N'Ph? ki?n', 1100000, 40, 'xiaomi_powerbank.jpg', 12, 968000, 1),  
(N'Gi� �? �i?n tho?i Remax RM-C26', N'Xoay 360 �?, g?n tr�n b�n l�m vi?c', N'Ph? ki?n', 350000, 80, 'remax_stand.jpg', 7, 325500, 1),  
(N'?p l�ng iPhone 14 Pro Max Nillkin', N'B?o v? ch?ng s?c, m?ng nh?', N'Ph? ki?n', 590000, 60, 'nillkin_case.jpg', 9, 536900, 1),  
(N'B? s?c kh�ng d�y Magsafe', N'H? tr? s?c kh�ng d�y nhanh cho iPhone', N'Ph? ki?n', 1490000, 35, 'magsafe_charger.jpg', 8, 1370800, 1),  
(N'Tai nghe Bluetooth Beats Studio Buds', N'�m thanh m?nh m?, pin l�n �?n 24 gi?', N'Ph? ki?n', 5200000, 25, 'beats_studio_buds.jpg', 10, 4680000, 1),  
(N'K�nh c�?ng l?c iPhone 14 Pro Max Mocoll', N'Ch?ng tr?y x�?c, ph? nano ch?ng b�m v�n tay', N'Ph? ki?n', 450000, 90, 'mocoll_screen_guard.jpg', 5, 427500, 1),  
(N'�i?n tho?i Vivo X90 Pro+', N'Chip Dimensity 9200, camera ZEISS 1 inch', N'�i?n t?', 26000000, 15, 'vivo_x90.jpg', 12, 22880000, 1),
(N'Xiaomi 13 Pro', N'M�n h?nh AMOLED 6.73 inch, Snapdragon 8 Gen 2, camera Leica 50MP', N'�i?n tho?i', 24000000, 25, 'xiaomi_13_pro.jpg', 8, 22080000, 1),  
(N'OPPO Find X6 Pro', N'M�n h?nh 6.82 inch, camera tele 50MP, pin 5000mAh', N'�i?n tho?i', 27000000, 15, 'oppo_find_x6.jpg', 7, 25110000, 1),  
(N'Vivo X90 Pro+', N'M�n h?nh AMOLED 6.78 inch, chip Dimensity 9200, camera ZEISS', N'�i?n tho?i', 23000000, 18, 'vivo_x90_pro.jpg', 9, 20930000, 1),  
(N'Google Pixel 7 Pro',N'M�n h?nh LTPO OLED 6.7 inch, chip Google Tensor G2, camera 50MP', N'�i?n tho?i', 26000000, 12, 'pixel_7_pro.jpg', 10, 23400000, 1),  
(N'Realme GT Neo 5', N'M�n h?nh AMOLED 6.74 inch, s?c nhanh 240W, chip Snapdragon 8+ Gen 1', N'�i?n tho?i', 15000000, 30, 'realme_gt_neo5.jpg', 5, 14250000, 1),  
(N'OnePlus 11', N'M�n h?nh AMOLED 6.7 inch, chip Snapdragon 8 Gen 2, camera Hasselblad', N'�i?n tho?i', 22000000, 20, 'oneplus_11.jpg', 7, 20460000, 1),  
(N'Asus ROG Phone 7 Ultimate',N'M�n h?nh 6.78 inch, t?n s? qu�t 165Hz, pin 6000mAh', N'�i?n tho?i', 29000000, 10, 'rog_phone_7.jpg', 12, 25520000, 1),  
(N'Sony Xperia 1 V', N'M�n h?nh 4K OLED 6.5 inch, camera 24mm Exmor-T', N'�i?n tho?i', 32000000, 8, 'xperia_1_v.jpg', 10, 28800000, 1),  
(N'Huawei Mate 60 Pro', N'M�n h?nh LTPO OLED 6.82 inch, camera Leica 50MP, HarmonyOS', N'�i?n tho?i', 31000000, 15, 'huawei_mate_60.jpg', 8, 28520000, 1),  
(N'Nokia X30 5G',N'M�n h?nh AMOLED 6.43 inch, camera PureView 50MP, pin 4200mAh', N'�i?n tho?i', 12500000, 40, 'nokia_x30.jpg', 5, 11875000, 1);  

-- Th�m d? li?u cho b?ng OrderAddress
INSERT INTO OrderAddress (firstName, lastName, email, mobileNo, address, city, state, pincode)
VALUES 
(N'L�', N'Tr?ng Chi?n', 'chienltpy00076@gmail.com', '0978123456', N'12 Nguy?n V�n Linh', N'H? Ch� Minh', N'H? Ch� Minh', '700000'),
(N'Phan', N'Thanh T�m', 'thao.le@example.com', '0934567890', N'45 L? Th�?ng Ki?t', N'H� N?i', N'H� N?i', '100000');

-- Th�m d? li?u cho b?ng ProductOrder
INSERT INTO ProductOrder (orderId, orderDate, productId, price, quantity, userId, status, paymentType, orderAddressId)
VALUES 
('DH002', '2025-02-04', 1, 35000000, 1, 1, N'�ang x? l?', N'Chuy?n kho?n', 1),
('DH003', '2025-02-05', 6, 9500000, 2, 2, N'Ho�n t?t', N'Thanh to�n khi nh?n h�ng', 2),
('DH004', '2025-02-05', 3, 70000000, 1, 1, N'�ang x? l?', N'Chuy?n kho?n', 1);

-- Th�m d? li?u cho b?ng Cart
INSERT INTO Cart (userId, productId, quantity, totalPrice, totalOrderPrice)
VALUES 
(1, 2, 1, 32000000, 29440000),
(2, 7, 3, 13500000, 12825000),
(1, 8, 1, 55000000, 50600000);

-- Th�m d? li?u cho b?ng UserDtls
INSERT INTO UserDtls (name, mobileNumber, email, address, city, stateN, pincode, password, profileImage, role, isEnable, accountNonLocked, failedAttempt, lockTime, resetToken)
VALUES 
(N'V? Duy Hi?u', '0987654321', 'vduyhieu123@gmail.com', N'123 L� L?i', N'H� N?i', N'H� N?i', '100000', 'password123', 'user1.jpg', 'User', 1, 1, 0, NULL, NULL),
(N'Hu?nh Qu?c Trung', '0912345678', 'trunghq123@gmail.com', N'456 Tr?n Ph�', N'H? Ch� Minh', N'H? Ch� Minh', '700000', 'password456', 'user2.jpg', 'User', 1, 1, 0, NULL, NULL);


select * from UserDtls; 