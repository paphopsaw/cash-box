package com.example.cashbox.centers;

public class CenterNotFoundException extends RuntimeException {
    public CenterNotFoundException(int id) {
        super("ID: " + id);
    }
}
