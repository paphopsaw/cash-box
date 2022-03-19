package com.example.cashbox.trucks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        truckListResponse.setTruckList(truckService.getAll());
        return truckListResponse;
    }

    @PostMapping(value = "/api/truck-management/truck/{id}/updateLocation",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TruckResponse updateLocation(@PathVariable int id,
                                        @RequestBody TruckUpdateLocationRequest truckUpdateLocationRequest) {
        truckService.updateLocation(id, truckUpdateLocationRequest);
        TruckResponse truckResponse = new TruckResponse();
        truckResponse.setMessage("Location updated.");
        return truckResponse;
    }

}
