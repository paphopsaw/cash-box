package com.example.cashbox.cits;

import com.example.cashbox.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CITController {
    @Autowired CITService citService;

    @GetMapping("/api/delivery-management/cit/{id}")
    public CITResponse getCIT(@PathVariable int id) {
        CITResponse citResponse = new CITResponse();
        CIT cit = citService.getById(id);
        citResponse.setCIT(cit);
        return citResponse;
    }

    @GetMapping("/api/delivery-management/cit/{id}/label")
    public CITLabelResponse getCITLabel(@PathVariable int id) {
        CITLabelResponse citLabelResponse = new CITLabelResponse();
        CIT cit = citService.getById(id);
        citLabelResponse.setCIT(cit);
        return citLabelResponse;
    }

    @GetMapping("/api/delivery-management/cits/{year}/{month}/{date}")
    public CITListResponse getCITsByDate(@PathVariable int year, @PathVariable int month, @PathVariable int date) {
        CITListResponse citListResponse = new CITListResponse();
        List<CIT> citList = citService.getByYMD(year, month, date);
        List<CITResponse> citResponseList = new ArrayList<>();
        for (CIT cit : citList) {
            CITResponse citResponse = new CITResponse();
            citResponse.setCIT(cit);
            citResponseList.add(citResponse);
        }
        citListResponse.setCitList(citResponseList);
        return citListResponse;
    }

    @PostMapping(value = "/api/delivery-management/cit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse createCIT(@RequestBody CITCreateRequest request) {
        int senderId = request.getSenderId();
        int receiverId = request.getReceiverId();
        BigDecimal amountTHB = new BigDecimal(request.getAmountTHB());
        BigDecimal amountUSD = new BigDecimal(request.getAmountUSD());
        CIT cit = citService.create(senderId, receiverId, amountTHB, amountUSD);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("New CIT created ID: " + cit.getId());
        return messageResponse;
    }

    @PostMapping(value = "/api/delivery-management/cit/{id}/receive",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse receive(@PathVariable int id, @RequestBody CITSetStatusToReceivedRequest request) {
        int truckId = request.getTruckId();
        citService.receive(id, truckId);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("CIT updated to RECEIVED.");
        return messageResponse;
    }

    @PostMapping(value = "/api/delivery-management/cit/{id}/deliver")
    public MessageResponse deliver(@PathVariable int id) {
        citService.deliver(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("CIT updated to DELIVERED.");
        return messageResponse;
    }

    @PostMapping(value = "/api/delivery-management/cit/{id}/confirm",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse confirm(@PathVariable int id, @RequestBody CITSetStatusToConfirmedRequest request) {
        BigDecimal amountTHB = new BigDecimal(request.getAmountTHB());
        BigDecimal amountUSD = new BigDecimal(request.getAmountUSD());
        citService.confirm(id, amountTHB, amountUSD);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("CIT updated to CONFIRMED.");
        return messageResponse;
    }


}
