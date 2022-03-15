/*create database virtualshop;*/
use virtualshop;
create table if not exists products
( 
ID int AUTO_INCREMENT PRIMARY KEY,
productcode varchar(25) unique not null,
productname varchar(25) not null, 
productprice float not null,
productquantity int not null,
producttype varchar(20) not  null,
productdescription varchar(300) not null
)
select* from products;
truncate table products;