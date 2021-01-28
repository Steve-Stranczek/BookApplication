package com.company.BookApplication.DataLayer;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class BookRepository extends Repository {

    private static final String url = System.getenv("mySqlBookDB");
    private static final String user = "root";
    private static final String password = System.getenv("mySqlRootPassword");
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;



    public ArrayList<Book> GetAllBooks(){
        ArrayList<Book> books = new ArrayList<Book>();

        String query = "Select * from book";
        try{
       //     Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(url,user,password);

            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Book book = new Book();
                book.bookId = rs.getInt("bookId");
                book.bookTitle = rs.getString("bookTitle");
                book.genreId = rs.getInt("genreId");
                book.numPages = rs.getInt("numPages");
                book.authorId = rs.getInt("authorId");
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
}
