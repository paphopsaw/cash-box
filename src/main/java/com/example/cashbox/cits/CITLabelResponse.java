package com.example.cashbox.cits;

import java.util.UUID;

public class CITLabelResponse {
    private int id;
    private String senderAddress;
    private String receiverAddress;

    public void setCIT(CIT cit) {
        this.id = cit.getId();
        this.senderAddress = cit.getSender().getAddress();
        this.receiverAddress = cit.getReceiver().getAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
