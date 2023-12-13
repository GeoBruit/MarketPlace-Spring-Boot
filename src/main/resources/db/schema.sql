-- User Table
CREATE TABLE users (
    userId BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- UserProfile Table
CREATE TABLE user_profiles (
    profileId BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(20)
);

-- Product Table
CREATE TABLE products (
    productId BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    createdBy BIGINT,
    FOREIGN KEY (createdBy) REFERENCES users(userId)
);

-- Image Table
CREATE TABLE images (
    imageId BIGINT AUTO_INCREMENT PRIMARY KEY,
    imageName VARCHAR(255),
    imageData BLOB,
    productId BIGINT,
    FOREIGN KEY (productId) REFERENCES products(productId)
);

-- SavedProduct Table
CREATE TABLE saved_products (
    savedId BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT,
    productId BIGINT,
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (productId) REFERENCES products(productId)
);
