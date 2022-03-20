package com.example.cashbox.cits;

import com.example.cashbox.MessageResponse;
import com.example.cashbox.centers.CenterNotFoundException;
import com.example.cashbox.centers.CenterResponse;
import com.example.cashbox.trucks.TruckNotFoundException;
import com.example.cashbox.trucks.TruckResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CITControllerAdvice {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse centerNotFound(CenterNotFoundException e) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Center " + e.getMessage() + " not found.");
        return messageResponse;
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse truckNotFound(TruckNotFoundException e) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Truck " + e.getMessage() + " not found.");
        return messageResponse;
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse citNotFound(CITNotFoundException e) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("CIT " + e.getMessage() + " not found.");
        return messageResponse;
    }
}
