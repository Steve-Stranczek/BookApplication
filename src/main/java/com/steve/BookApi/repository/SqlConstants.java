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
            "SELECT %s, %s, %s, %s, %s, %s, %s from book a " +
            "INNER JOIN author b ON a.authorId = b.authorId " +
            "INNER JOIN genre c ON a.genreId = c.genreId ",
            bookId, bookTitle, pages, genreName, genreId, authorName, authorId
    );
}
