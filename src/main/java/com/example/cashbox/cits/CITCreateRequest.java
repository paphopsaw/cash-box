package com.example.cashbox.cits;

public class CITCreateRequest {
    private int senderId;
    private int receiverId;
    private String amountTHB;
    private String amountUSD;

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

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
