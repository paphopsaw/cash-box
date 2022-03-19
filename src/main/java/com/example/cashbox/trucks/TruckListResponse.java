package com.example.cashbox.trucks;

import java.util.List;

public class TruckListResponse {
    private List<Truck> truckList;
    private String message;

    public List<Truck> getTruckList() {
        return truckList;
    }

    public void setTruckList(List<Truck> truckList) {
        this.truckList = truckList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
