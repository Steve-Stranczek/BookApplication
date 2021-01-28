package com.company.BookApplication.DataLayer;

import java.util.ArrayList;

public interface IBookRepository {
     ArrayList<Book> GetAllBooks();
     ArrayList<Genre> GetAllGenres();
     void DeleteBook();
     void UpdateBook();
     void InsertBook();
}
