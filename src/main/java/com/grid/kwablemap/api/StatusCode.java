package com.grid.kwablemap.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusCode {
    OK(HttpStatus.OK, "200");


    private String code;
    private HttpStatus status;

    StatusCode(HttpStatus httpStatus, String number) {

    }
}
