package com.steve.BookApi;

public  class SqlConstants {
    public static final String createAuthorTable = String.format( "CREATE TABLE `bookdb`.`author` (" +
            "  `authorId` INT NOT NULL AUTO_INCREMENT," +
            "  `author` VARCHAR(100) NOT NULL," +
            "  PRIMARY KEY (`authorId`));");

    public static final String createGenreTable = String.format( "CREATE TABLE `bookdb`.`genre` (" +
            "  `genreId` INT NOT NULL AUTO_INCREMENT," +
            "  `genre` VARCHAR(100) NOT NULL," +
            "  PRIMARY KEY (`genreId`));");

    public static final String createBookTable = String.format( "CREATE TABLE `bookdb`.`book` (" +
            "  `bookId` INT NOT NULL AUTO_INCREMENT," +
            "  `bookTitle` VARCHAR(100) NOT NULL," +
            "  `numPages` INT NOT NULL," +
            "  `genreId` INT NOT NULL ," +
            "  `authorId` INT NOT NULL ," +
            "  PRIMARY KEY (`bookId`));");

    public static final String insertGenres = String.format("INSERT INTO genre (genreId, genre) " +
            "  VALUES " +
            "  (1,'Post-Modern'), " +
            "  (2,'Horror'), "+
            "  (3, 'Fantasy'); "
    );
}
