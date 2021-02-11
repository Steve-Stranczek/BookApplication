package com.steve.BookApi.service;

import com.steve.BookApi.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();
    Book getBook(String title, String author);
    long deleteBook(long id);
    long insertBook(Book book);
    long updateBook(Book book);
}
