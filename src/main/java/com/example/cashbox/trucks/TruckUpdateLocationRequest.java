package com.example.cashbox.trucks;

import java.time.LocalDateTime;

public class TruckUpdateLocationRequest {
    private double latitude;
    private double longitude;
    private String dateTimeString;

    public TruckUpdateLocationRequest() {
    }

    public TruckUpdateLocationRequest(double latitude, double longitude, String dateTimeString) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTimeString = dateTimeString;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }
}
