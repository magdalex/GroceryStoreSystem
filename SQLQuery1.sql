

CREATE TABLE Products (
    productID INT NOT NULL,
    productName VARCHAR(255) NOT NULL,
    productDescription VARCHAR(255) NOT NULL,
    productPrice DECIMAL(10,2) NOT NULL,
    productCategory VARCHAR(255) NOT NULL,
    productInventoryQuantity INT NOT NULL,
    PRIMARY KEY (productID)
);

INSERT INTO Products (productID, productName, productDescription, productPrice, productCategory, productInventoryQuantity)
VALUES
(1, 'Apple', 'A sweet, juicy fruit', 0.99, 'Fruit', 100),
(2, 'Banana', 'A yellow, curved fruit', 0.59, 'Fruit', 100),
(3, 'Orange', 'A citrus fruit with a thick rind', 0.79, 'Fruit', 100),
(4, 'Grapes', 'A small, round fruit', 1.99, 'Fruit', 100),
(5, 'Strawberry', 'A sweet, red fruit', 2.99, 'Fruit', 100),
(6, 'Tomato', 'A red, juicy fruit', 0.99, 'Vegetable', 100),
(7, 'Carrot', 'A long, orange vegetable', 0.59, 'Vegetable', 100),
(8, 'Cucumber', 'A green, cylindrical vegetable', 0.79, 'Vegetable', 100),
(9, 'Potato', 'A starchy, tuberous vegetable', 1.99, 'Vegetable', 100),
(10, 'Onion', 'A pungent, bulbous vegetable', 2.99, 'Vegetable', 100);

INSERT INTO Products (productID, productName, productDescription, productPrice, productCategory, productInventoryQuantity)
VALUES
(11, 'Chicken', 'Tastes like chicken', 4.99, 'Meat', 30),
(12, 'Beef', 'Raw meat from a cow or bull', 4.59, 'Meat', 37),
(13, 'Ground Porc', 'Great for making meat pie', 3.79, 'Meat', 21),
(14, 'Italian Sausage', 'Comes in a package of 4', 6.19, 'Meat', 11),
(15, 'Ketchup', 'Heinz, 750ml', 3.99, 'Condiment', 25),
(16, 'Mustard', 'yellow mustard, Presidents Choice, 250ml', 1.19, 'Condiment', 3);

Create table Accounts (
    emailAccount varchar(255) NOT NULL unique primary key,
    password varchar(255) NOT NULL,
    firstName varchar(255) NOT NULL,
    lastName varchar(255) NOT NULL,
    phoneNumber varchar(255) NOT NULL,
    addressNumber varchar(255) NOT NULL,
    addressStreet varchar(255) NOT NULL,
    addressApt varchar(255),
    addressCity varchar(255) NOT NULL,
    addressZip varchar(255) NOT NULL,
    addressState varchar(255) NOT NULL,
    payCardNumber varchar(255),
    payCardType varchar(255),
    payCardExp varchar(255),
    creationDate Date NOT NULL,
    accountEnabled char(5) not null,
    unlockTime date
);

insert into Accounts(emailAccount, password,firstName,lastName,phoneNumber,addressNumber,
addressStreet,addressCity,addressZip ,addressState,creationDate,accountEnabled)
Values
('remy@gmail.com','12345','remy','bourdon','514-600-6060','12',
'first avenue','montreal','H3X 1T5','Quebec',getdate(),'true');

Create table Carts (
cartID varchar(255),
productID varchar(255),
quantity numeric,
totalCost decimal(10,2)
primary key (cartID, productID)
);