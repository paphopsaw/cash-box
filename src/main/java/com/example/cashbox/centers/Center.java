package com.example.cashbox.centers;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Center {
    @Id
    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private BigDecimal balanceTHB;
    private BigDecimal balanceUSD;


    public Center() {}

    public Center(int id, String name, String address, double latitude, double longitude, BigDecimal balanceTHB, BigDecimal balanceUSD) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.balanceTHB = balanceTHB;
        this.balanceUSD = balanceUSD;
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

    public BigDecimal getBalanceTHB() {
        return balanceTHB;
    }

    public void setBalanceTHB(BigDecimal balanceTHB) {
        this.balanceTHB = balanceTHB;
    }

    public BigDecimal getBalanceUSD() {
        return balanceUSD;
    }

    public void setBalanceUSD(BigDecimal balanceUSD) {
        this.balanceUSD = balanceUSD;
    }
}
