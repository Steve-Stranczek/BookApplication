CREATE TABLE `bookdb`.`author` (
  `authorId` int NOT NULL AUTO_INCREMENT,
  `author` varchar(45) NOT NULL,
  PRIMARY KEY (`authorId`)
);


CREATE TABLE `bookdb`.`book` (
  `bookId` int NOT NULL AUTO_INCREMENT,
  `bookTitle` varchar(100) NOT NULL,
  `numPages` int NOT NULL,
  `genreId` int NOT NULL,
  `authorId` int NOT NULL,
  PRIMARY KEY (`bookId`)
);


CREATE TABLE `bookdb`.`genre` (
  `genreId` int NOT NULL AUTO_INCREMENT,
  `genre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`genreId`)
);

INSERT INTO bookdb.genre (genreId, genre)  VALUES  (1,'Post-Modern'),   (2,'Horror'),   (3, 'Fantasy');



