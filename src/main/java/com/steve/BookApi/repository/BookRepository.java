package com.steve.BookApi.repository;

import com.steve.BookApi.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAllBooks();
    long deleteBook(long id);
}