package com.example.cashbox.trucks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TruckControllerAdvice {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TruckResponse truckNotFound(TruckNotFoundException e) {
        TruckResponse truckResponse = new TruckResponse();
        truckResponse.setMessage("Truck " + e.getMessage() + " not found.");
        return truckResponse;
    }

}
