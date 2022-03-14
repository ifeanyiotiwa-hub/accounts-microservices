DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS accounts;

CREATE TABLE `customer` (
    `customer_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(15) NOT NULL,
    created_date DATE DEFAULT NULL
);

CREATE TABLE accounts (
    customer_id INTEGER NOT NULL,
    account_number INTEGER PRIMARY KEY AUTO_INCREMENT,
    account_type VARCHAR(255) NOT NULL,
    branch_address VARCHAR(255) NOT NULL,
    created_date DATE DEFAULT NULL
);

INSERT INTO `customer` (name, email, mobile_number, created_date)
VALUES (
    'Ifeanyichukwu Otiwa', 'i.o@gmail.com', '07036771035', CURDATE()
);

INSERT INTO accounts (customer_id, account_number, account_type, branch_address, created_date)
VALUES (
    1, 186576453, 'Coporate', '7 Asajon way, Sangotedo Lagos', CURDATE()
);