package com.steve.BookApi.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    public int bookId;
    public String bookTitle;
    @JsonUnwrapped
    public Author author;
    @JsonUnwrapped
    public Genre genre;
    public int pages;
}