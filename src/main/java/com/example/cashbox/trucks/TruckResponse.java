package com.example.cashbox.trucks;

import java.time.LocalDateTime;

public class TruckResponse {
    private int id;
    private double latitude;
    private double longitude;
    private LocalDateTime lastUpdate;

    public void setTruck(Truck truck) {
        this.id = truck.getId();
        this.latitude = truck.getLatitude();
        this.longitude = truck.getLongitude();
        this.lastUpdate = truck.getLastUpdated();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
