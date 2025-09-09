create schema finalGUIProject; 

use finalGUIProject; 

create table users( 

id integer not null unique auto_increment, 

username varchar(255) not null unique, 

pass varchar(255) not null, 

userRole integer not null, 

primary key (id) 

); 

create table product( 

id integer not null unique auto_increment, 

prodName varchar(255) not null, 

price decimal(10,2) not null, 

primary key (id) 

); 

create table sale( 

saleID integer not null unique auto_increment, 

prodID integer not null, 

userID integer not null, 

qty integer not null, 

total decimal(10,2) not null, 

primary key (saleID), 

foreign key (prodID) references product(id), 

foreign key (userID) references users(id) 

); 

insert into users (username, pass, userRole) VALUES ("Admin", "Admin", 1); 

DELIMITER // 

  

CREATE PROCEDURE RegisterNewUser( 

    IN p_Username VARCHAR(255), 

    IN p_Password VARCHAR(255), 

    IN p_UserRole INT 

) 

BEGIN 

    INSERT INTO users (username, pass, userRole) 

    VALUES (p_Username, p_Password, p_UserRole); 

END // 

  

CREATE PROCEDURE LoginWithCreds( 

    IN p_Username VARCHAR(255), 

    IN p_Password VARCHAR(255)

) 

BEGIN 

    SELECT id, userRole

    FROM users 

    WHERE username = p_Username AND pass = p_Password; 

END // 

  

CREATE PROCEDURE GetAllProducts() 
BEGIN 
    SELECT id, prodName, price 
    FROM product; 
END // 

  

CREATE PROCEDURE GetSalesTotal() 
BEGIN 
    SELECT SUM(total) 
    FROM sale; 
END // 

  

CREATE PROCEDURE SubmitOrder( 

    IN p_ProdID INT, 

    IN p_UserID INT, 

    IN p_Qty INT, 

    IN p_Total DECIMAL(10, 2) 

) 

BEGIN 

    INSERT INTO sale (prodID, userID, qty, total) 

    VALUES (p_ProdID, p_UserID, p_Qty, p_Total); 

END // 

  

CREATE PROCEDURE SubmitNewProduct( 

    IN p_ProdName VARCHAR(255), 

    IN p_Price DECIMAL(10, 2) 

) 

BEGIN 

    INSERT INTO product (prodName, price) 

    VALUES (p_ProdName, p_Price); 

END // 

  

DELIMITER ; 