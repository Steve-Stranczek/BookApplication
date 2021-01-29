package com.steve.BookApi.model;

import lombok.Data;

@Data
public class Book {
    public int id;
    public String title;
    public Author author;
    public Genre genre;
    public int pages;
}