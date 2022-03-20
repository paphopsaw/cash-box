package com.example.cashbox.cits;

public class CITSetStatusToConfirmedRequest {
    private String amountTHB;
    private String amountUSD;

    public String getAmountTHB() {
        return amountTHB;
    }

    public void setAmountTHB(String amountTHB) {
        this.amountTHB = amountTHB;
    }

    public String getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(String amountUSD) {
        this.amountUSD = amountUSD;
    }
}
