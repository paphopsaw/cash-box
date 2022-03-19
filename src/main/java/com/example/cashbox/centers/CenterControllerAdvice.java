package com.example.cashbox.centers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CenterControllerAdvice {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CenterResponse centerNotFound(CenterNotFoundException e) {
        CenterResponse centerResponse = new CenterResponse();
        centerResponse.setMessage("Center " + e.getMessage() + " not found.");
        return centerResponse;
    }
}
