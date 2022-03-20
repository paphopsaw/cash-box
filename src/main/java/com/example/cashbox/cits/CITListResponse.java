package com.example.cashbox.cits;

import java.util.List;

public class CITListResponse {
    private List<CITResponse> citList;

    public List<CITResponse> getCitList() {
        return citList;
    }

    public void setCitList(List<CITResponse> citList) {
        this.citList = citList;
    }
}
