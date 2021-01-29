package com.steve.BookApi.repository;

public class SqlConstants {
    public static final String bookId = "bookId";
    public static final String bookTitle = "bookTitle";
    public static final String pages = "numPages";
    public static final String genreName = "genre";
    public static final String genreId = "genreId";
    public static final String authorName = "author";
    public static final String authorId = "authorId";

    public static final String getAllBooksQuery = String.format(
            "SELECT a.%s, a.%s, a.%s, c.%s, c.%s, b.%s, b.%s from book a " +
                    "INNER JOIN author b ON a.authorId = b.authorId " +
                    "INNER JOIN genre c ON a.genreId = c.genreId ",
            bookId, bookTitle, pages, genreName, genreId, authorName, authorId
    );

    public static final String getBookById = String.format(
            "SELECT a.%s, a.%s, a.%s, c.%s, c.%s, b.%s, b.%s from book a " +
                    "INNER JOIN author b ON a.authorId = b.authorId " +
                    "INNER JOIN genre c ON a.genreId = c.genreId " +
                    "WHERE bookId = ",
            bookId, bookTitle, pages, genreName, genreId, authorName, authorId
    );

    public static final String deleteBookById = String.format(
            "DELETE FROM book WHERE %s = (:id)",
            bookId
    );

    public static final String insertBook = String.format(
            "INSERT INTO book (%s, %s, %s, %s) VALUES (:bookTitle,:pages,:authorId,:genreId) ",
            bookTitle, pages, authorId, genreId
    );

    public static final String getAuthorIdByName = String.format(
            "SELECT %s FROM author WHERE author = (:authorName)",
            authorId
    );

    public static final String insertAuthor = String.format(
            "INSERT INTO author (author) Values (:authorName)"
    );

    public static final String getBookIdByTitleandAuthor = String.format(
            "SELECT bookId FROM book " +
                    "WHERE bookTitle = (:bookTitle) " +
                    "AND authorId = (:authorId)"
    );

}
