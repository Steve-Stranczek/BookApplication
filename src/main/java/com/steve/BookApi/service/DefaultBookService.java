package com.steve.BookApi.service;

import com.steve.BookApi.model.Book;
import com.steve.BookApi.repository.BookRepository;

import java.util.List;

public class DefaultBookService implements BookService {

    BookRepository repo;

    public DefaultBookService(BookRepository _repo) {
        repo = _repo;
    }

    @Override
    public List<Book> getAllBooks() {
        return repo.getAllBooks();
    }
}
