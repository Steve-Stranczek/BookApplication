package com.steve.BookApi.service;

import com.steve.BookApi.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    void deleteBook();

}
