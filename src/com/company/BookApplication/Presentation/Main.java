package com.company.BookApplication.Presentation;
import com.company.BookApplication.DataLayer.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        BookRepository myBookRepository = new BookRepository();
        ArrayList<Book> books = myBookRepository.GetAllBooks();
        ArrayList<Genre> genres = myBookRepository.GetAllGenres();
        int a = 5;
	// write your code here
    }
}
