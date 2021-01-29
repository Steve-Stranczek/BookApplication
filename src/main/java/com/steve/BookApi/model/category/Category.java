package com.steve.BookApi.model.category;

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
public class Category {
    @JsonProperty(SqlConstants.id)
    private int id;

    @JsonProperty(SqlConstants.name)
    private String name;

    @JsonUnwrapped
    private Audit audit;
}
