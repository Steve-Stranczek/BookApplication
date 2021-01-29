package com.steve.BookApi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.steve.BookApi.repository.SqlConstants;
import lombok.Data;

@Data
public class Genre {
    @JsonProperty(SqlConstants.genreId)
    public int genreId;
    @JsonProperty(SqlConstants.genre)
    public String name;
}