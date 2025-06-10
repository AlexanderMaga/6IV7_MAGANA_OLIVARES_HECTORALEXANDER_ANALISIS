create database jwt_bd;
use jwt_bd;


create table usuarios(
    id int not null AUTO_INCREMENT primary key,
    email varchar (100) not null,
    password varchar (200) not null
); 

