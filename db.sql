create table user_join
(
id INT PRIMARY KEY AUTO_INCREMENT,
user_id varchar(12) NOT NULL,
pw varchar(12) NOT NULL,
name varchar(12) NOT NULL,
apartment_number varchar(12) NOT NULL,
member_name varchar(12),
phone varchar(12) NOT NULL,
email varchar(30) NOT NULL
);

create table fee
(
id INT PRIMARY KEY AUTO_INCREMENT,
apartment_number varchar(12) NOT NULL,
electricity_fee varchar(12) NOT NULL,
water_fee varchar(12) NOT NULL,
gas_fee varchar(12) NOT NULL,
security_fee varchar(12) NOT NULL,
days date 
);

create table request
(
id INT PRIMARY KEY AUTO_INCREMENT,
requestname varchar(12) NOT NULL,
apartment_number varchar(12) NOT NULL,
requestdate date NOT NULL,
requesttext varchar(100) NOT NULL,
completedate date
);
        