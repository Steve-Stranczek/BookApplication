package com.steve.BookApi.repository;

import com.steve.BookApi.model.Book;

import java.util.List;

public interface IBookRepository {
    List<Book> getAllBooks();
    Book getBook(String title, String author);
    long deleteBook(long id);
    long insertBook(Book book);
    long updateBook(Book book);
}