package com.steve.BookApi.model.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.steve.BookApi.repository.SqlConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    @JsonProperty(SqlConstants.user)
    private String user;

    @JsonProperty(SqlConstants.time)
    private String time;
}
