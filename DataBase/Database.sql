DROP DATABASE IF EXISTS to_do_list;

CREATE DATABASE to_do_list;

USE to_do_list;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,  -- Foreign key to associate tasks with a user
    name VARCHAR(15) NOT NULL,
    description VARCHAR(200) NOT NULL,
    dueDate DATE,
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'PENDING',
    priority ENUM('LOW', 'MEDIUM', 'HIGH'),
    category ENUM('WORK', 'PERSONAL', 'SHOPPING', 'OTHERS'),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE  -- Ensures tasks are deleted if the user is deleted
);