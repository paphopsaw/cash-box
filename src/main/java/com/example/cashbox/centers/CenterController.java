package com.example.cashbox.centers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CenterController {

    @Autowired
    CenterService centerService;

    @GetMapping("/api/center-management/center/{id}")
    public CenterResponse getCenter(@PathVariable int id) {
        CenterResponse response = new CenterResponse();
        Center center = centerService.getById(id);
        response.setCenter(center);
        return response;
    }
}
