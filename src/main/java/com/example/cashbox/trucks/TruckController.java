package com.example.cashbox.trucks;

import com.example.cashbox.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TruckController {

    @Autowired
    private TruckService truckService;

    @GetMapping("/api/truck-management/truck/{id}")
    public TruckResponse getTruck(@PathVariable int id) {
        TruckResponse truckResponse = new TruckResponse();
        Truck truck = truckService.getById(id);
        truckResponse.setTruck(truck);
        return truckResponse;
    }

    @GetMapping("/api/truck-management/trucks")
    public TruckListResponse getTrucks() {
        TruckListResponse truckListResponse = new TruckListResponse();
        List<Truck> truckList = truckService.getAll();
        List<TruckResponse> truckResponseList = new ArrayList<>();
        for (Truck truck : truckList) {
            TruckResponse truckResponse = new TruckResponse();
            truckResponse.setTruck(truck);
            truckResponseList.add(truckResponse);
        }
        truckListResponse.setTruckResponseList(truckResponseList);
        return truckListResponse;
    }

    @PostMapping(value = "/api/truck-management/truck/{id}/updateLocation",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse updateLocation(@PathVariable int id,
                                        @RequestBody TruckUpdateLocationRequest truckUpdateLocationRequest) {
        double latitude = truckUpdateLocationRequest.getLatitude();
        double longitude = truckUpdateLocationRequest.getLongitude();
        String dateTimeString = truckUpdateLocationRequest.getDateTimeString();
        truckService.updateLocation(id, latitude, longitude, dateTimeString);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Location updated.");
        return messageResponse;
    }

}
