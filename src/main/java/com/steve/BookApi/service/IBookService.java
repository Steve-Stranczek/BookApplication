package com.steve.BookApi.service;

import com.steve.BookApi.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();
    long deleteBook(long id);
    long insertBook(Book bookToInsert);
    long updateBook(Book bookToUpdate);
}
