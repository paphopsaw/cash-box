package com.example.cashbox.trucks;

public class TruckNotFoundException extends RuntimeException {
    public TruckNotFoundException(int id) {
        super("ID: " + id);
    }
}
