package com.steve.BookApi.controller;

import com.steve.BookApi.model.Book;
import com.steve.BookApi.service.IBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController extends LoggingController {
    Logger LOG = LoggerFactory.getLogger(LoggingController.class);

    private final IBookService bookService;

    @Autowired
    BookController(IBookService _bookService) {
        bookService = _bookService;
    }

    @GetMapping("v1/books")
    ResponseEntity<List<Book>> getBooks() {
        return timeOperation(() -> {
                    LOG.info("Received request: request[v1/books]");
                    return ResponseEntity.ok().body(bookService.getAllBooks());
                }
        );
    }

    @PostMapping("v1/book")
    ResponseEntity<Long> insertBook(@Validated @RequestBody Book bookToInsert)
    {
        return timeOperation(() -> {
                LOG.info("Received request: request[v1/book]");
                return ResponseEntity.ok().body(bookService.insertBook(bookToInsert));
            }
        );
    }

    @PutMapping("v1/book/{id}")
    ResponseEntity<Long> updateBook(@Validated @RequestBody Book bookToUpdate, @PathVariable(value="id") int id)
    {
        bookToUpdate.id=id;
        return timeOperation(() -> {
                    LOG.info("Received request: request[v1/book]");
                    long numRowsAffected = bookService.updateBook(bookToUpdate);
                    if(numRowsAffected == 0)
                    {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    else {
                        return new ResponseEntity<Long>(numRowsAffected, HttpStatus.OK);
                    }
                }
        );
    }


    @DeleteMapping("v1/deleteBook/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable(value="id") long id)
    {
        long numRowsAffected = bookService.deleteBook(id);

        if(numRowsAffected == 0)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
    }


}
