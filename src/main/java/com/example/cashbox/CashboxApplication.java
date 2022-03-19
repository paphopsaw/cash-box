package com.example.cashbox;

import com.example.cashbox.centers.Center;
import com.example.cashbox.centers.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CashboxApplication {
	@Autowired
	private CenterRepository centerRepository;

	@PostConstruct
	public void initializeData() {
		Center center1 = new Center(
				1,
				"Main center",
				"273 Samsen Rd, Wat Sam Phraya, Phra Nakhon, Bangkok 10200",
				13.768747,
				100.500471,
				1_000_000_000.00,
				20_000_000.00);

		Center center2 = new Center(
				2,
				"Branch center 1",
				"1 Rat Burana 27 Alley, Rat Burana, Bangkok 10140",
				13.678239,
				100.513642,
				300_000_000.00,
				4_000_000.00);
		centerRepository.save(center1);
		centerRepository.save(center2);
	}

	public static void main(String[] args) {
		SpringApplication.run(CashboxApplication.class, args);
	}

}
