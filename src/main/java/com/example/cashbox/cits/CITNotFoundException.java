package com.example.cashbox.cits;

public class CITNotFoundException extends RuntimeException {
    public CITNotFoundException(int id) {
        super("ID: " + id);
    }
}
