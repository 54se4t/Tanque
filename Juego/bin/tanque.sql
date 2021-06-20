create database if not exists Tanque
CHARACTER SET utf8 
COLLATE utf8_spanish_ci;

use Tanque;

create table cuenta (
	usuario varchar(20) primary key,
    contrasenya varchar(20)
);