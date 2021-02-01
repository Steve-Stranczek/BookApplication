package com.steve.BookApi.repository;

import com.steve.BookApi.model.Book;

import java.util.List;

public interface IBookRepository {
    List<Book> getAllBooks();
    long deleteBook(long id);
    long insertBook(Book bookToInsert);
    long updateBook(Book bookToUpdate);
}