package com.products.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionApi {

    @JsonProperty("exception")
    private String exception;

    @JsonProperty("message")
    private String message;
}
