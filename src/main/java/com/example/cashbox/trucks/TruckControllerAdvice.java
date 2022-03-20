package com.example.cashbox.trucks;

import com.example.cashbox.MessageResponse;
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
    public MessageResponse truckNotFound(TruckNotFoundException e) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Truck " + e.getMessage() + " not found.");
        return messageResponse;
    }

}
