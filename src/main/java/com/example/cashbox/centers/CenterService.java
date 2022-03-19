package com.example.cashbox.centers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;

    public void setCenterRepository(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public Center getById(int id) {
        Optional<Center> result = centerRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CenterNotFoundException(id);
    }

    public void setBalance(int id, Currency currency, double amount) {
        Optional<Center> result = centerRepository.findById(id);
        if (result.isPresent()) {
            Center center = result.get();
            switch (currency) {
                case THB:
                    center.setBalanceTHB(amount);
                    break;
                case USD:
                    center.setBalanceUSD(amount);
                    break;
            }
            centerRepository.save(center);
        } else {
            throw new CenterNotFoundException(id);
        }
    }



}
