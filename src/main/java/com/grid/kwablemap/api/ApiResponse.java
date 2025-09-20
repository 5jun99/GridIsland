package com.grid.kwablemap.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private HttpStatus status;

    public ApiResponse(String code, String message, T data, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public static <T> ApiResponse<T> of(StatusCode statusCode, String message, T data) {
        return new ApiResponse(statusCode.getCode(), message, data, statusCode.getStatus());
    }
}
