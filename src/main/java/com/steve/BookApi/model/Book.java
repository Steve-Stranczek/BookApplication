package com.steve.BookApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.steve.BookApi.repository.SqlConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @JsonProperty(SqlConstants.bookId)
    public int id;
    @JsonProperty(SqlConstants.bookTitle)
    public String title;
    @JsonUnwrapped
    public Author author;
    @JsonUnwrapped
    public Genre genre;
    @JsonProperty(SqlConstants.pages)
    public int pages;
}