DROP DATABASE IF EXISTS to_do_list;

CREATE DATABASE to_do_list;

USE to_do_list;

CREATE TABLE Tasks{
    name VARCHAR(15) PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(200) NOT NULL,
    dueDate Date,
    is_completed BOOLEAN DEFAULT FALSE
};
