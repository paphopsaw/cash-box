package com.example.cashbox.centers;

public class CenterResponse {
    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private double balanceTHB;
    private double balanceUSD;
    private String message;


    public void setCenter(Center center) {
        this.id = center.getId();
        this.name = center.getName();
        this.address = center.getAddress();
        this.latitude = center.getLatitude();
        this.longitude = center.getLongitude();
        this.balanceTHB = center.getBalanceTHB();
        this.balanceUSD = center.getBalanceUSD();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public double getBalanceTHB() {
        return balanceTHB;
    }

    public void setBalanceTHB(double balanceTHB) {
        this.balanceTHB = balanceTHB;
    }

    public double getBalanceUSD() {
        return balanceUSD;
    }

    public void setBalanceUSD(double balanceUSD) {
        this.balanceUSD = balanceUSD;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}