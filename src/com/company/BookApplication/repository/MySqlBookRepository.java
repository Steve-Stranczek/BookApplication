package com.company.BookApplication.repository;

import com.company.BookApplication.driver.MySqlDriver;
import com.company.BookApplication.model.Book;
import com.company.BookApplication.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlBookRepository implements BookRepository {
    private MySqlDriver driver;

    private static String getAllBooksQuery =
            "select * from book a " +
            "inner join author b " +
            "on a.authorId = b.authorId " +
            "inner join genre c " +
            "on a.genreId = c.genreId; ";

    private static String getAllGenresQuery =  "select * from genre";


    public MySqlBookRepository() throws SQLException {
        driver = new MySqlDriver();
    }

    @Override
    public List<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();

        try{
            ResultSet rs = driver.getResultSet(getAllBooksQuery);

            while (rs.next())
            {
                Book book = new Book();

                book.id = rs.getInt("bookId");
                book.title = rs.getString("bookTitle");
                book.genre.id = rs.getInt("genreId");
                book.genre.name = rs.getString("genre");
                book.pages = rs.getInt("numPages");
                book.author.id = rs.getInt("authorId");
                book.author.name = rs.getString("author");
                books.add(book);
            }
        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        finally{
            driver.closeConnection();
        }

        return books;
    }

    @Override
    public List<Genre> getAllGenres() {
        ArrayList<Genre> genres = new ArrayList<>();

        try{
            ResultSet rs = driver.getResultSet(getAllGenresQuery);

            while (rs.next())
            {
                Genre genre = new Genre();
                genre.id = rs.getInt("genreId");
                genre.name = rs.getString("genre");
                genres.add(genre);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally{
            driver.closeConnection();
        }

        return genres;
    }

    @Override
    public void deleteBook() {

    }

    @Override
    public void updateBook() {

    }

    @Override
    public void insertBook() {

    }
}
