package com.steve.BookApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.steve.BookApi.repository.SqlConstants;
import lombok.Data;

@Data
public class Genre {
    @JsonProperty(SqlConstants.genreId)
    public int id;
    @JsonProperty(SqlConstants.genreName)
    public String name;
}