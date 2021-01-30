package com.steve.BookApi.service;

import com.steve.BookApi.model.Book;
import com.steve.BookApi.repository.IBookRepository;

import java.util.List;

public class BookService implements IBookService {

    IBookRepository repo;

    public BookService(IBookRepository _repo) {
        repo = _repo;
    }

    @Override
    public List<Book> getAllBooks() {
        return repo.getAllBooks();
    }

    @Override
    public long deleteBook(long id) {
        return repo.deleteBook(id);
    }

    @Override
    public long insertBook(Book bookToInsert) {
        return repo.insertBook(bookToInsert);
    }

    @Override
    public long updateBook(Book bookToUpdate) {
        return repo.updateBook(bookToUpdate);
    }


}
