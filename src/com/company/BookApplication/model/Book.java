package com.company.BookApplication.model;

public class Book {
    public int id;
    public String title;
    public Author author;
    public Genre genre;
    public int pages;

    public Book() {
        author = new Author();
        genre = new Genre();
    }
}