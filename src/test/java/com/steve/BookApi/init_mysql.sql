CREATE TABLE `bookdb`.`author`
             (`authorId` INT NOT NULL AUTO_INCREMENT,
             `author` VARCHAR(100) NOT NULL,
              PRIMARY KEY (`authorId`));

CREATE TABLE `bookdb`.`genre` (
             `genreId` INT NOT NULL AUTO_INCREMENT,
             `genre` VARCHAR(100) NOT NULL,
              PRIMARY KEY (`genreId`));

CREATE TABLE `bookdb`.`book` (
             `bookId` INT NOT NULL AUTO_INCREMENT,
             `bookTitle` VARCHAR(100) NOT NULL,
             `numPages` INT NOT NULL,
             `genreId` INT NOT NULL ,
             `authorId` INT NOT NULL ,
             PRIMARY KEY (`bookId`));
