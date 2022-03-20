package com.example.cashbox.cits;

import java.time.LocalDateTime;
import java.util.UUID;

public class CITResponse {
    private int id;
    private int senderId;
    private int receiverId;
    private int deliveryTruckId;
    private String amountTHB;
    private String amountUSD;
    private String status;

    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;

    private LocalDateTime deliveredAt;
    private LocalDateTime confirmedAt;
    private String amountTHBConfirmed;
    private String amountUSDConfirmed;

    private String message;


    public void setCIT(CIT cit) {
        this.id = cit.getId();
        this.senderId = cit.getSender().getId();
        this.receiverId = cit.getReceiver().getId();
        if (cit.getDeliveryTruck() != null)
            this.deliveryTruckId = cit.getDeliveryTruck().getId();

        this.amountTHB = cit.getAmountTHB().toString();
        this.amountUSD = cit.getAmountUSD().toString();
        switch (cit.getStatus()) {
            case GENERATED:
                this.status = "generated";
                break;
            case RECEIVED:
                this.status = "received";
                break;
            case DELIVERED:
                this.status = "delivered";
                break;
            case CONFIRMED:
                this.status = "confirmed";
                break;
        }
        this.lastUpdated = cit.getLastUpdated();
        this.createdAt = cit.getCreatedAt();
        this.receivedAt = cit.getReceivedAt();
        this.deliveredAt = cit.getDeliveredAt();
        this.confirmedAt = cit.getConfirmedAt();
        if (cit.getAmountTHBConfirmed() != null) {
            this.amountTHBConfirmed = cit.getAmountTHBConfirmed().toString();
        }
        if (cit.getAmountUSDConfirmed() != null) {
            this.amountUSDConfirmed = cit.getAmountUSDConfirmed().toString();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getDeliveryTruckId() {
        return deliveryTruckId;
    }

    public void setDeliveryTruckId(int deliveryTruckId) {
        this.deliveryTruckId = deliveryTruckId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public String getAmountTHBConfirmed() {
        return amountTHBConfirmed;
    }

    public void setAmountTHBConfirmed(String amountTHBConfirmed) {
        this.amountTHBConfirmed = amountTHBConfirmed;
    }

    public String getAmountUSDConfirmed() {
        return amountUSDConfirmed;
    }

    public void setAmountUSDConfirmed(String amountUSDConfirmed) {
        this.amountUSDConfirmed = amountUSDConfirmed;
    }
}
