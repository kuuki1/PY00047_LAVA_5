create database ecommerce_ASM_JAVA_5
go
use ecommerce_ASM_JAVA_5
go
-- Table for UserDtls
CREATE TABLE UserDtls (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    mobileNumber NVARCHAR(50) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    address NVARCHAR(500) NOT NULL,
    city NVARCHAR(255) NOT NULL,
    pincode NVARCHAR(20) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    profileImage NVARCHAR(255),
    role NVARCHAR(50) NOT NULL,
    isEnable BIT NOT NULL,
    accountNonLocked BIT NOT NULL,
    failedAttempt INT NOT NULL,
    lockTime DATETIME,
    resetToken NVARCHAR(255)
);
go
-- Table for Category
CREATE TABLE Category (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    image_name NVARCHAR(255) NOT NULL,
    is_active BIT NOT NULL
);
go
-- Table for Product
CREATE TABLE Product (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(500) NOT NULL,
    description NVARCHAR(max) NOT NULL,
    category NVARCHAR(255) NOT NULL,
    price DECIMAL(18,2) NOT NULL,
    stock INT NOT NULL,
    image NVARCHAR(255) NOT NULL,
    discount INT NOT NULL,
    discountPrice DECIMAL(18,2),
    isActive BIT NOT NULL
);
go
-- Table for Cart
CREATE TABLE Cart (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT FK_Cart_User FOREIGN KEY (userId) REFERENCES UserDtls(id),
    CONSTRAINT FK_Cart_Product FOREIGN KEY (productId) REFERENCES Product(id)
);
go
-- Table for OrderAddress
CREATE TABLE OrderAddress (
    id INT IDENTITY(1,1) PRIMARY KEY,
    firstName NVARCHAR(255) NOT NULL,
    lastName NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    mobileNo NVARCHAR(50) NOT NULL,
    address NVARCHAR(500) NOT NULL,
    city NVARCHAR(255) NOT NULL,
    pincode NVARCHAR(20) NOT NULL
);
go
-- Table for ProductOrder
CREATE TABLE ProductOrder (
    id INT IDENTITY(1,1) PRIMARY KEY,
    orderId NVARCHAR(255) NOT NULL,
    orderDate DATETIME NOT NULL,
    productId INT NOT NULL,
    price DECIMAL(18,2) NOT NULL,
    quantity INT NOT NULL,
    userId INT NOT NULL,
    status NVARCHAR(50) NOT NULL,
    paymentType NVARCHAR(50) NOT NULL,
    orderAddressId INT NOT NULL,
    CONSTRAINT FK_ProductOrder_Product FOREIGN KEY (productId) REFERENCES Product(id),
    CONSTRAINT FK_ProductOrder_User FOREIGN KEY (userId) REFERENCES UserDtls(id),
    CONSTRAINT FK_ProductOrder_OrderAddress FOREIGN KEY (orderAddressId) REFERENCES OrderAddress(id)
);
go

-- Inserting data into Category table
INSERT INTO Category (name, image_name, is_active)
VALUES 
('iPhones', 'iphone.jpg', 1),
('MacBooks', 'iphone.jpg', 1),
('iPads', 'iphone.jpg', 1),
('Accessories', 'iphone.jpg', 1),
('Apple Watches', 'iphone.jpg', 1),
('AirPods', 'iphone.jpg', 1);

-- Inserting data into Product table for iPhones category
INSERT INTO Product (title, description, category, price, stock, image, discount, discount_price, is_active)
VALUES 
('iPhone 14', 'Apple iPhone 14 with 128GB storage, A15 Bionic chip, 5G support.', 'iPhones', 799.99, 50, 'iphone14.jpg', 10, 719.99, 1),
('iPhone 13', 'Apple iPhone 13 with 64GB storage, A15 Bionic chip, 5G support.', 'iPhones', 699.99, 70, 'iphone13.jpg', 5, 664.99, 1),
('iPhone 12', 'Apple iPhone 12 with 64GB storage, A14 Bionic chip, 5G support.', 'iPhones', 599.99, 100, 'iphone12.jpg', 5, 569.99, 1),
('iPhone SE 3', 'Apple iPhone SE 3 with 64GB storage, A15 Bionic chip, 5G support.', 'iPhones', 429.99, 30, 'iphonese3.jpg', 5, 409.99, 1),
('iPhone 11', 'Apple iPhone 11 with 64GB storage, A13 Bionic chip, 4G support.', 'iPhones', 499.99, 60, 'iphone11.jpg', 10, 449.99, 1),
('iPhone 14 Pro', 'Apple iPhone 14 Pro with 256GB storage, A16 Bionic chip, 5G support.', 'iPhones', 999.99, 40, 'iphone14pro.jpg', 15, 849.99, 1),
('iPhone 13 Pro', 'Apple iPhone 13 Pro with 256GB storage, A15 Bionic chip, 5G support.', 'iPhones', 999.99, 30, 'iphone13pro.jpg', 10, 899.99, 1),
('iPhone 13 Mini', 'Apple iPhone 13 Mini with 128GB storage, A15 Bionic chip, 5G support.', 'iPhones', 699.99, 90, 'iphone13mini.jpg', 5, 669.99, 1),
('iPhone 12 Pro', 'Apple iPhone 12 Pro with 128GB storage, A14 Bionic chip, 5G support.', 'iPhones', 899.99, 50, 'iphone12pro.jpg', 10, 809.99, 1),
('iPhone 12 Mini', 'Apple iPhone 12 Mini with 64GB storage, A14 Bionic chip, 5G support.', 'iPhones', 699.99, 40, 'iphone12mini.jpg', 5, 664.99, 1),
('iPhone XR', 'Apple iPhone XR with 64GB storage, A12 Bionic chip, 4G support.', 'iPhones', 499.99, 80, 'iphonexr.jpg', 10, 449.99, 1),
('iPhone 11 Pro', 'Apple iPhone 11 Pro with 256GB storage, A13 Bionic chip, 4G support.', 'iPhones', 899.99, 60, 'iphone11pro.jpg', 5, 854.99, 1),
('iPhone 8', 'Apple iPhone 8 with 64GB storage, A11 Bionic chip, 4G support.', 'iPhones', 399.99, 120, 'iphone8.jpg', 5, 379.99, 1),
('iPhone 7', 'Apple iPhone 7 with 32GB storage, A10 Fusion chip, 4G support.', 'iPhones', 299.99, 150, 'iphone7.jpg', 0, 299.99, 1),
('iPhone SE 2', 'Apple iPhone SE 2 with 64GB storage, A13 Bionic chip, 4G support.', 'iPhones', 399.99, 200, 'iphonese2.jpg', 5, 379.99, 1),
('iPhone 14 Plus', 'Apple iPhone 14 Plus with 128GB storage, A15 Bionic chip, 5G support.', 'iPhones', 899.99, 40, 'iphone14plus.jpg', 10, 809.99, 1),
('iPhone 13 Pro Max', 'Apple iPhone 13 Pro Max with 512GB storage, A15 Bionic chip, 5G support.', 'iPhones', 1099.99, 30, 'iphone13promax.jpg', 15, 929.99, 1),
('iPhone 12 Pro Max', 'Apple iPhone 12 Pro Max with 128GB storage, A14 Bionic chip, 5G support.', 'iPhones', 1099.99, 50, 'iphone12promax.jpg', 5, 1044.99, 1),
('iPhone 11 Pro Max', 'Apple iPhone 11 Pro Max with 256GB storage, A13 Bionic chip, 4G support.', 'iPhones', 999.99, 70, 'iphone11promax.jpg', 10, 899.99, 1),
('iPhone X', 'Apple iPhone X with 64GB storage, A11 Bionic chip, 4G support.', 'iPhones', 549.99, 90, 'iphonex.jpg', 5, 519.99, 1);
 
 -- Inserting data into Product table for MacBooks category
INSERT INTO Product (title, description, category, price, stock, image, discount, discount_price, is_active)
VALUES 
('MacBook Pro 14-inch', 'Apple MacBook Pro 14-inch with M1 Pro chip, 16GB RAM, 512GB SSD.', 'MacBooks', 1999.99, 30, 'macbookpro14.jpg', 10, 1799.99, 1),
('MacBook Air M2', 'Apple MacBook Air with M2 chip, 8GB RAM, 256GB SSD.', 'MacBooks', 1199.99, 40, 'macbookairm2.jpg', 5, 1139.99, 1),
('MacBook Pro 16-inch', 'Apple MacBook Pro 16-inch with M1 Max chip, 32GB RAM, 1TB SSD.', 'MacBooks', 2499.99, 20, 'macbookpro16.jpg', 15, 2124.99, 1),
('MacBook Pro 13-inch', 'Apple MacBook Pro 13-inch with M1 chip, 8GB RAM, 256GB SSD.', 'MacBooks', 1299.99, 50, 'macbookpro13.jpg', 10, 1169.99, 1),
('MacBook Pro 14-inch (Refurbished)', 'Refurbished Apple MacBook Pro 14-inch with M1 Pro chip, 16GB RAM, 512GB SSD.', 'MacBooks', 1799.99, 25, 'macbookpro14refurb.jpg', 20, 1439.99, 1),
('MacBook Air M1', 'Apple MacBook Air with M1 chip, 8GB RAM, 256GB SSD.', 'MacBooks', 999.99, 70, 'macbookairm1.jpg', 10, 899.99, 1),
('MacBook Pro 16-inch (Refurbished)', 'Refurbished Apple MacBook Pro 16-inch with M1 Max chip, 32GB RAM, 1TB SSD.', 'MacBooks', 2299.99, 15, 'macbookpro16refurb.jpg', 15, 1954.99, 1),
('MacBook Air 13-inch', 'Apple MacBook Air 13-inch with Intel i5, 8GB RAM, 256GB SSD.', 'MacBooks', 1099.99, 50, 'macbookair13.jpg', 5, 1044.99, 1),
('MacBook Pro 13-inch (Refurbished)', 'Refurbished Apple MacBook Pro 13-inch with M1 chip, 8GB RAM, 256GB SSD.', 'MacBooks', 1199.99, 30, 'macbookpro13refurb.jpg', 10, 1079.99, 1),
('MacBook Pro 15-inch', 'Apple MacBook Pro 15-inch with Intel i7, 16GB RAM, 512GB SSD.', 'MacBooks', 1899.99, 60, 'macbookpro15.jpg', 10, 1709.99, 1),
('MacBook Pro 14-inch (M1 Pro)', 'Apple MacBook Pro 14-inch with M1 Pro chip, 32GB RAM, 512GB SSD.', 'MacBooks', 2299.99, 40, 'macbookpro14m1pro.jpg', 10, 2069.99, 1),
('MacBook Pro 13-inch (M1)', 'Apple MacBook Pro 13-inch with M1 chip, 16GB RAM, 512GB SSD.', 'MacBooks', 1599.99, 50, 'macbookpro13m1.jpg', 5, 1519.99, 1),
('MacBook Pro 16-inch (M1 Max)', 'Apple MacBook Pro 16-inch with M1 Max chip, 64GB RAM, 2TB SSD.', 'MacBooks', 3099.99, 20, 'macbookpro16m1max.jpg', 15, 2634.99, 1),
('MacBook Pro 13-inch (Intel)', 'Apple MacBook Pro 13-inch with Intel i5, 8GB RAM, 512GB SSD.', 'MacBooks', 1499.99, 30, 'macbookpro13intel.jpg', 10, 1349.99, 1),
('MacBook Air M1 256GB', 'Apple MacBook Air with M1 chip, 8GB RAM, 256GB SSD.', 'MacBooks', 1099.99, 50, 'macbookairm1256gb.jpg', 5, 1044.99, 1),
('MacBook Pro 14-inch (Refurbished M1 Pro)', 'Refurbished Apple MacBook Pro 14-inch with M1 Pro chip, 16GB RAM, 512GB SSD.', 'MacBooks', 1699.99, 35, 'macbookpro14refurbm1.jpg', 15, 1449.99, 1),
('MacBook Pro 15-inch (Refurbished)', 'Refurbished Apple MacBook Pro 15-inch with Intel i7, 16GB RAM, 512GB SSD.', 'MacBooks', 1599.99, 20, 'macbookpro15refurb.jpg', 20, 1399.99, 1),
('MacBook Air 13-inch (Refurbished)', 'Refurbished Apple MacBook Air 13-inch with Intel i5, 8GB RAM, 256GB SSD.', 'MacBooks', 899.99, 60, 'macbookair13refurb.jpg', 10, 809.99, 1),
('MacBook Pro 16-inch (M1 Max 64GB)', 'Apple MacBook Pro 16-inch with M1 Max chip, 64GB RAM, 1TB SSD.', 'MacBooks', 3399.99, 10, 'macbookpro16m1max64gb.jpg', 20, 2719.99, 1),
('MacBook Air M2 512GB', 'Apple MacBook Air with M2 chip, 8GB RAM, 512GB SSD.', 'MacBooks', 1299.99, 40, 'macbookairm2512gb.jpg', 5, 1234.99, 1);

-- Inserting data into Product table for iPads category
INSERT INTO Product (title, description, category, price, stock, image, discount, discount_price, is_active)
VALUES 
('iPad Pro 12.9-inch', 'Apple iPad Pro 12.9-inch with M1 chip, 128GB storage.', 'iPads', 1099.99, 50, 'ipadpro129.jpg', 5, 1044.99, 1),
('iPad Air', 'Apple iPad Air with A14 Bionic chip, 64GB storage.', 'iPads', 599.99, 80, 'ipadair.jpg', 10, 539.99, 1),
('iPad Mini', 'Apple iPad Mini with A15 Bionic chip, 64GB storage.', 'iPads', 499.99, 100, 'ipadmini.jpg', 5, 474.99, 1),
('iPad 10.2-inch', 'Apple iPad 10.2-inch with A13 Bionic chip, 32GB storage.', 'iPads', 329.99, 70, 'ipad102.jpg', 5, 314.99, 1),
('iPad Pro 11-inch', 'Apple iPad Pro 11-inch with M1 chip, 128GB storage.', 'iPads', 799.99, 50, 'ipadpro11.jpg', 5, 759.99, 1),
('iPad 8th Gen', 'Apple iPad 8th Gen with A12 Bionic chip, 32GB storage.', 'iPads', 329.99, 90, 'ipad8thgen.jpg', 5, 314.99, 1),
('iPad Pro 12.9-inch (Refurbished)', 'Refurbished iPad Pro 12.9-inch with M1 chip, 128GB storage.', 'iPads', 999.99, 30, 'ipadpro12refurb.jpg', 10, 899.99, 1),
('iPad Air 4th Gen', 'Apple iPad Air 4th Gen with A14 Bionic chip, 64GB storage.', 'iPads', 629.99, 40, 'ipadair4.jpg', 5, 599.99, 1),
('iPad Mini 5', 'Apple iPad Mini 5 with A12 Bionic chip, 64GB storage.', 'iPads', 399.99, 120, 'ipadmini5.jpg', 5, 379.99, 1),
('iPad 9th Gen', 'Apple iPad 9th Gen with A13 Bionic chip, 32GB storage.', 'iPads', 329.99, 100, 'ipad9thgen.jpg', 5, 314.99, 1),
('iPad Pro 12.9-inch 5th Gen', 'Apple iPad Pro 12.9-inch 5th Gen with M1 chip, 256GB storage.', 'iPads', 1099.99, 50, 'ipadpro125g.jpg', 5, 1044.99, 1),
('iPad Pro 11-inch 2nd Gen', 'Apple iPad Pro 11-inch 2nd Gen with M1 chip, 128GB storage.', 'iPads', 849.99, 40, 'ipadpro112g.jpg', 5, 809.99, 1),
('iPad Mini 6th Gen', 'Apple iPad Mini 6th Gen with A15 Bionic chip, 64GB storage.', 'iPads', 499.99, 50, 'ipadmini6.jpg', 5, 474.99, 1),
('iPad Air 2021', 'Apple iPad Air 2021 with A14 Bionic chip, 256GB storage.', 'iPads', 749.99, 70, 'ipadair2021.jpg', 5, 709.99, 1),
('iPad Pro 11-inch 5G', 'Apple iPad Pro 11-inch with M1 chip, 128GB storage, 5G support.', 'iPads', 899.99, 80, 'ipadpro115g.jpg', 5, 854.99, 1),
('iPad Pro 12.9-inch 2021', 'Apple iPad Pro 12.9-inch with M1 chip, 512GB storage.', 'iPads', 1399.99, 30, 'ipadpro12.jpg', 5, 1329.99, 1),
('iPad 9th Gen (Refurbished)', 'Refurbished iPad 9th Gen with A13 Bionic chip, 32GB storage.', 'iPads', 279.99, 50, 'ipad9thgenrefurb.jpg', 10, 259.99, 1),
('iPad Mini 6 (Refurbished)', 'Refurbished iPad Mini 6th Gen with A15 Bionic chip, 64GB storage.', 'iPads', 479.99, 40, 'ipadmini6refurb.jpg', 5, 454.99, 1);

-- Inserting data into Product table for Accessories category
INSERT INTO Product (title, description, category, price, stock, image, discount, discount_price, is_active)
VALUES 
('Apple AirTag', 'Apple AirTag for item tracking, Bluetooth.', 'Accessories', 29.99, 150, 'airtag.jpg', 5, 28.49, 1),
('Apple Magic Mouse 2', 'Apple Magic Mouse 2, wireless and rechargeable.', 'Accessories', 79.99, 200, 'magicmouse2.jpg', 10, 71.99, 1),
('Apple Magic Keyboard', 'Apple Magic Keyboard with numeric keypad, wireless.', 'Accessories', 129.99, 100, 'magickeyboard.jpg', 5, 123.49, 1),
('Apple Pencil 2nd Gen', 'Apple Pencil 2nd Gen for iPad Pro and iPad Air.', 'Accessories', 129.99, 150, 'applepencil2.jpg', 10, 116.99, 1),
('Apple Smart Folio Case', 'Apple Smart Folio Case for iPad Pro 12.9-inch.', 'Accessories', 99.99, 70, 'smartfolio.jpg', 10, 89.99, 1),
('Apple AirPods Pro Case', 'Apple AirPods Pro Case for protection.', 'Accessories', 39.99, 120, 'airpodsprocase.jpg', 5, 37.99, 1),
('Apple MagSafe Charger', 'Apple MagSafe Charger for iPhone 12 and later.', 'Accessories', 39.99, 200, 'magsafecharger.jpg', 5, 37.99, 1),
('Apple Watch Sport Band', 'Apple Watch Sport Band in various colors.', 'Accessories', 49.99, 150, 'watchsportband.jpg', 10, 44.99, 1),
('Apple Leather Case', 'Apple Leather Case for iPhone 12 and later.', 'Accessories', 59.99, 80, 'appleleathercase.jpg', 5, 56.99, 1),
('Apple Magic Keyboard for iPad', 'Apple Magic Keyboard for iPad Pro 11-inch and 12.9-inch.', 'Accessories', 349.99, 40, 'magickeyboardipad.jpg', 15, 297.49, 1),
('Apple Lightning to USB Cable', 'Apple Lightning to USB Cable, 1 meter.', 'Accessories', 19.99, 250, 'lightningusb.jpg', 5, 18.99, 1),
('Apple 20W USB-C Power Adapter', 'Apple 20W USB-C Power Adapter for fast charging.', 'Accessories', 19.99, 100, '20wusbcharger.jpg', 5, 18.49, 1),
('Apple iPhone 13 Silicone Case', 'Apple iPhone 13 Silicone Case, soft-touch finish.', 'Accessories', 39.99, 200, 'iphone13siliconecase.jpg', 10, 35.99, 1),
('Apple Watch Sport Loop', 'Apple Watch Sport Loop, breathable and adjustable.', 'Accessories', 49.99, 150, 'sportsloop.jpg', 10, 44.99, 1),
('Apple AirPods Charging Case', 'Apple AirPods Charging Case, for 1st and 2nd Gen AirPods.', 'Accessories', 69.99, 100, 'airpodschargingcase.jpg', 5, 66.49, 1),
('Apple 3.5mm Headphone Jack Adapter', 'Apple 3.5mm Headphone Jack Adapter for iPhone and iPad.', 'Accessories', 9.99, 300, 'headphonejackadapter.jpg', 5, 9.49, 1),
('Apple Watch Magnetic Charger', 'Apple Watch Magnetic Charger for all models.', 'Accessories', 29.99, 200, 'watchmagneticcharger.jpg', 5, 28.49, 1),
('Apple iPhone 12 Leather Case', 'Apple iPhone 12 Leather Case, premium quality.', 'Accessories', 59.99, 50, 'iphone12leathercase.jpg', 10, 53.99, 1),
('Apple Smart Keyboard for iPad', 'Apple Smart Keyboard for iPad Pro 12.9-inch and 11-inch.', 'Accessories', 179.99, 40, 'smartkeyboardipad.jpg', 5, 170.99, 1);

-- Inserting data into Product table for Apple Watches category
INSERT INTO Product (title, description, category, price, stock, image, discount, discount_price, is_active)
VALUES 
('Apple Watch Series 7', 'Apple Watch Series 7 with 45mm, GPS + Cellular, Midnight.', 'Apple Watches', 399.99, 50, 'applewatch7.jpg', 10, 359.99, 1),
('Apple Watch SE', 'Apple Watch SE with GPS, 44mm, Space Gray.', 'Apple Watches', 279.99, 70, 'applewatchse.jpg', 5, 265.99, 1),
('Apple Watch Series 6', 'Apple Watch Series 6 with 40mm, GPS + Cellular, Blue.', 'Apple Watches', 399.99, 40, 'applewatch6.jpg', 15, 339.99, 1),
('Apple Watch Series 5', 'Apple Watch Series 5 with 40mm, GPS + Cellular, Gold.', 'Apple Watches', 349.99, 60, 'applewatch5.jpg', 10, 314.99, 1),
('Apple Watch Series 4', 'Apple Watch Series 4 with 44mm, GPS + Cellular, Space Black.', 'Apple Watches', 299.99, 50, 'applewatch4.jpg', 10, 269.99, 1),
('Apple Watch Series 3', 'Apple Watch Series 3 with 38mm, GPS, Space Gray.', 'Apple Watches', 199.99, 150, 'applewatch3.jpg', 5, 189.99, 1),
('Apple Watch SE (Refurbished)', 'Refurbished Apple Watch SE with GPS, 40mm, Space Gray.', 'Apple Watches', 249.99, 80, 'applewatchse_refurb.jpg', 15, 212.49, 1),
('Apple Watch Series 7 (GPS)', 'Apple Watch Series 7 with 41mm, GPS, Green.', 'Apple Watches', 399.99, 40, 'applewatch7gps.jpg', 5, 379.99, 1),
('Apple Watch Series 6 (GPS + Cellular)', 'Apple Watch Series 6 with 40mm, GPS + Cellular, Silver.', 'Apple Watches', 429.99, 60, 'applewatch6gps.jpg', 5, 408.99, 1),
('Apple Watch Nike Series 7', 'Apple Watch Nike Series 7 with 45mm, GPS, Space Gray.', 'Apple Watches', 399.99, 70, 'applewatchnikeseries7.jpg', 10, 359.99, 1),
('Apple Watch Series 5 (Refurbished)', 'Refurbished Apple Watch Series 5 with 40mm, GPS + Cellular.', 'Apple Watches', 299.99, 40, 'applewatch5refurb.jpg', 5, 284.99, 1),
('Apple Watch Hermès', 'Apple Watch Hermès with 42mm, GPS + Cellular, Space Black.', 'Apple Watches', 1299.99, 20, 'applewatchhermes.jpg', 5, 1234.99, 1),
('Apple Watch SE 40mm', 'Apple Watch SE with GPS, 40mm, White.', 'Apple Watches', 279.99, 100, 'applewatchse40mm.jpg', 10, 251.99, 1),
('Apple Watch Series 7 (GPS) 41mm', 'Apple Watch Series 7 with 41mm, GPS, Red.', 'Apple Watches', 399.99, 50, 'applewatch7_red.jpg', 10, 359.99, 1),
('Apple Watch Series 6 (GPS)', 'Apple Watch Series 6 with 44mm, GPS, Space Black.', 'Apple Watches', 349.99, 60, 'applewatch6gpsblack.jpg', 5, 324.99, 1),
('Apple Watch Series 3 (Refurbished)', 'Refurbished Apple Watch Series 3 with 38mm, GPS, Space Gray.', 'Apple Watches', 179.99, 150, 'applewatch3refurb.jpg', 10, 159.99, 1),
('Apple Watch Series 5 (Nike)', 'Apple Watch Series 5 with 40mm, GPS, Nike Sport Band.', 'Apple Watches', 379.99, 50, 'applewatch5nike.jpg', 10, 341.99, 1),
('Apple Watch Ultra', 'Apple Watch Ultra with 49mm, GPS + Cellular, Titanium.', 'Apple Watches', 799.99, 30, 'applewatchultra.jpg', 5, 759.99, 1);

-- Inserting data into Product table for AirPods category
INSERT INTO Product (title, description, category, price, stock, image, discount, discount_price, is_active)
VALUES 
('AirPods Pro 2nd Gen', 'Apple AirPods Pro (2nd Gen) with active noise cancellation.', 'AirPods', 249.99, 100, 'airpodspro2.jpg', 10, 224.99, 1),
('AirPods 3rd Gen', 'Apple AirPods (3rd Gen) with spatial audio.', 'AirPods', 179.99, 150, 'airpods3.jpg', 5, 169.99, 1),
('AirPods Pro', 'Apple AirPods Pro with active noise cancellation.', 'AirPods', 249.99, 60, 'airpodspro.jpg', 10, 224.99, 1),
('AirPods 2nd Gen', 'Apple AirPods 2nd Gen with charging case.', 'AirPods', 129.99, 200, 'airpods2.jpg', 5, 123.49, 1),
('AirPods Max', 'Apple AirPods Max over-ear headphones with spatial audio.', 'AirPods', 549.99, 40, 'airpodsmax.jpg', 5, 522.49, 1),
('AirPods Pro 1st Gen', 'Apple AirPods Pro 1st Gen with active noise cancellation.', 'AirPods', 229.99, 100, 'airpodspro1.jpg', 5, 218.99, 1),
('AirPods with Charging Case', 'Apple AirPods with standard charging case.', 'AirPods', 159.99, 300, 'airpods.jpg', 5, 151.99, 1),
('AirPods Max (Refurbished)', 'Refurbished Apple AirPods Max over-ear headphones.', 'AirPods', 499.99, 50, 'airpodsmaxrefurb.jpg', 10, 449.99, 1),
('AirPods 2nd Gen (Refurbished)', 'Refurbished Apple AirPods 2nd Gen with charging case.', 'AirPods', 119.99, 120, 'airpods2refurb.jpg', 5, 113.99, 1),
('AirPods 3rd Gen (Refurbished)', 'Refurbished Apple AirPods 3rd Gen with spatial audio.', 'AirPods', 169.99, 90, 'airpods3refurb.jpg', 10, 152.99, 1),
('AirPods Pro 2nd Gen (Refurbished)', 'Refurbished Apple AirPods Pro 2nd Gen with active noise cancellation.', 'AirPods', 224.99, 70, 'airpodspro2refurb.jpg', 5, 213.99, 1);

-- Thêm dữ liệu vào bảng UserDtls
INSERT INTO UserDtls (name, mobileNumber, email, password, address, city, pincode, profileImage, role, isEnable, accountNonLocked, failedAttempt, lockTime, resetToken)
VALUES
('admin', '0123456789', 'admin@gmail.com', '$2a$10$fdtQoEMHtcmvMebvD0Wrwe073IFnEXt.yCC/xOuYCUGeTTvuozQhW', 'admin', 'admin', '010905', 'iphone.jpg', 'ROLE_ADMIN', 1, 1, 1, NULL, NULL),
('user', '0123456789', 'user@gmail.com', '$2a$10$fB7JwFW1AwHrBmy/2ACyA.iEmgGGBM.GYQ5ekwfaMWVM6Gds/qpnK', 'user', 'user', '123456', 'unnamed.jpg', 'ROLE_USER', 1, 1, 1, NULL, NULL);
