DROP TABLE IF EXISTS User;
CREATE TABLE User (
    id int NOT NULL AUTO_INCREMENT,
    lastName varchar(255) NOT NULL,
    firstName varchar(255),
    email varchar(255),
    PRIMARY KEY (id)
);
