package com.example.cashbox.cits;

import com.example.cashbox.centers.Center;
import com.example.cashbox.trucks.Truck;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class CIT {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Center sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Center receiver;
    @ManyToOne
    @JoinColumn(name = "delivery_truck_id")
    private Truck deliveryTruck;

    private BigDecimal amountTHB;
    private BigDecimal amountUSD;

    @Enumerated
    private CITStatus status;

    private LocalDateTime lastUpdated;
    private LocalDateTime createdAt;
    private LocalDateTime receivedAt;

    private LocalDateTime deliveredAt;
    private LocalDateTime confirmedAt;
    private BigDecimal amountTHBConfirmed;
    private BigDecimal amountUSDConfirmed;

    public CIT() {
    }

    public CIT(int id, Center sender, Center receiver, BigDecimal amountTHB, BigDecimal amountUSD, LocalDateTime createdAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amountTHB = amountTHB;
        this.amountUSD = amountUSD;
        this.createdAt = createdAt;
        this.lastUpdated = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Center getSender() {
        return sender;
    }

    public void setSender(Center sender) {
        this.sender = sender;
    }

    public Center getReceiver() {
        return receiver;
    }

    public void setReceiver(Center receiver) {
        this.receiver = receiver;
    }

    public Truck getDeliveryTruck() {
        return deliveryTruck;
    }

    public void setDeliveryTruck(Truck deliveryTruck) {
        this.deliveryTruck = deliveryTruck;
    }

    public BigDecimal getAmountTHB() {
        return amountTHB;
    }

    public void setAmountTHB(BigDecimal amountTHB) {
        this.amountTHB = amountTHB;
    }

    public BigDecimal getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(BigDecimal amountUSD) {
        this.amountUSD = amountUSD;
    }

    public CITStatus getStatus() {
        return status;
    }

    public void setStatus(CITStatus status) {
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

    public BigDecimal getAmountTHBConfirmed() {
        return amountTHBConfirmed;
    }

    public void setAmountTHBConfirmed(BigDecimal amountTHBConfirmed) {
        this.amountTHBConfirmed = amountTHBConfirmed;
    }

    public BigDecimal getAmountUSDConfirmed() {
        return amountUSDConfirmed;
    }

    public void setAmountUSDConfirmed(BigDecimal amountUSDConfirmed) {
        this.amountUSDConfirmed = amountUSDConfirmed;
    }
}
