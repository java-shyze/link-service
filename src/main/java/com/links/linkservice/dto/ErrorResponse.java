package com.links.linkservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String error,
    String message,
    Integer code
) {
    public ErrorResponse(String message) {
        this("Bad Request", message, 400);
    }
}
