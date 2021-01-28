package com.company.BookApplication.DataLayer;
import java.util.ArrayList;
import java.sql.*;

public class BookRepository implements IBookRepository{

    private static final String url = System.getenv("mySqlBookDB");
    private static final String user = "root";
    private static final String password = System.getenv("mySqlRootPassword");
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;


    public ArrayList<Book> GetAllBooks(){
        ArrayList<Book> books = new ArrayList<Book>();

        String query =  "select * from book a " +
                            "inner join author b " +
                                "on a.authorId = b.authorId " +
                            "inner join genre c " +
                                "on a.genreId = c.genreId; ";
        try{
            this.con = DriverManager.getConnection(url,user,password);

            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Book book = new Book();
                book.bookId = rs.getInt("bookId");
                book.bookTitle = rs.getString("bookTitle");
                book.genreId = rs.getInt("genreId");
                book.genre = rs.getString("genre");
                book.numPages = rs.getInt("numPages");
                book.authorId = rs.getInt("authorId");
                book.author = rs.getString("author");
                books.add(book);
            }

        }
        catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
        finally{
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return books;
    }


    public void DeleteBoook() {

    }


    public void UpdateBook() {

    }

    
    public void InertBook() {

    }
}
