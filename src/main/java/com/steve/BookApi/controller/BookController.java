package com.steve.BookApi.controller;

import com.steve.BookApi.model.Book;
import com.steve.BookApi.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController extends LoggingController {
    Logger LOG = LoggerFactory.getLogger(LoggingController.class);

    private final BookService bookService;

    @Autowired
    BookController(BookService _bookService) {
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
}
