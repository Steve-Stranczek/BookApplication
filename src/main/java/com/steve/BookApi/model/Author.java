package com.steve.BookApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.steve.BookApi.repository.SqlConstants;
import lombok.Data;

@Data
public class Author {
    @JsonProperty(SqlConstants.authorId)
    public int id;
    @JsonProperty(SqlConstants.authorName)
    public String name;
}