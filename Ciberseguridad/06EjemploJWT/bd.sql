create database jwt_db;
use jwt_db;


create table usuarios(
    id int not null AUTO_INCREMENT primary key,
    email varchar (100) not null,
    password varchar (200) not null
); 

