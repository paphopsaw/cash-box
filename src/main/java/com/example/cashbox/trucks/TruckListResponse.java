package com.example.cashbox.trucks;

import java.util.List;

public class TruckListResponse {
    private List<TruckResponse> truckResponseList;

    public List<TruckResponse> getTruckResponseList() {
        return truckResponseList;
    }

    public void setTruckResponseList(List<TruckResponse> truckResponseList) {
        this.truckResponseList = truckResponseList;
    }
}
