use library;

CREATE TABLE employee (
employeeID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
firstName VARCHAR(255) NOT NULL,
lastName VARCHAR(255) NOT NULL,
`role` VARCHAR(255) NOT NULL,
`password` VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL
);

CREATE TABLE book (
bookID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
description VARCHAR(255) NOT NULL,
addDate DATE NOT NULL
);

CREATE TABLE tag (
tagID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
tagDesciption VARCHAR(255) NOT NULL
);

CREATE TABLE booktag (
bookID INT NOT NULL,
tagID INT NOT NULL,
FOREIGN KEY(bookID) REFERENCES book(bookID) ON DELETE CASCADE,
FOREIGN KEY(tagID) REFERENCES tag(tagID) ON DELETE CASCADE,
PRIMARY KEY(bookID,tagID)
);

CREATE TABLE copy (
copyID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
isAvailable BOOLEAN NOT NULL,
`status` VARCHAR(255) NOT NULL,
bookID INT NOT NULL,
FOREIGN KEY(bookID) REFERENCES book(bookID) ON DELETE CASCADE
);

CREATE TABLE bookRent (
rentID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
employeeID INT NOT NULL,
bookID INT NOT NULL,
copyID INT NOT NULL,
rentalDate DATE NOT NULL,
returnDate DATE NOT NULL,
`status` VARCHAR(255) NOT NULL,
note VARCHAR(255) NOT NULL,
FOREIGN KEY(employeeID) REFERENCES employee(employeeID) ON DELETE CASCADE,
FOREIGN KEY(bookID) REFERENCES book(bookID) ON DELETE CASCADE,
FOREIGN KEY(copyID) REFERENCES copy(copyID) ON DELETE CASCADE
);

CREATE TABLE rentRequest (
rentreqID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
employeeID INT NOT NULL,
bookID INT NOT NULL,
requestDate DATE NOT NULL,
`status` VARCHAR(255) NOT NULL,
FOREIGN KEY(employeeID) REFERENCES employee(employeeID) ON DELETE CASCADE,
FOREIGN KEY(bookID) REFERENCES book(bookID) ON DELETE CASCADE
);

CREATE TABLE bookRequest (
bookreqID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
employeeID INT NOT NULL,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
pCompany VARCHAR(255),
link VARCHAR(255),
noCopies INT,
aproxCost INT,
`status` VARCHAR(255) NOT NULL,
FOREIGN KEY(employeeID) REFERENCES employee(employeeID) ON DELETE CASCADE
);



