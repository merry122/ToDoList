DROP DATABASE IF EXISTS to_do_list;

CREATE DATABASE to_do_list;

USE to_do_list;

CREATE TABLE Tasks(
                      id INT PRIMARY KEY,
                      name VARCHAR(15) NOT NULL,
                      description VARCHAR(200) NOT NULL,
                      dueDate Date,
                      is_completed BOOLEAN DEFAULT FALSE
);
