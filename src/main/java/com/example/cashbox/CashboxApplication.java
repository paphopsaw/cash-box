package com.example.cashbox;

import com.example.cashbox.centers.Center;
import com.example.cashbox.centers.CenterRepository;
import com.example.cashbox.trucks.Truck;
import com.example.cashbox.trucks.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class CashboxApplication {
	@Autowired
	private CenterRepository centerRepository;

	@Autowired
	private TruckRepository truckRepository;

	@PostConstruct
	public void initializeData() {
		Center center1 = new Center(
				1,
				"Main center",
				"273 Samsen Rd, Wat Sam Phraya, Phra Nakhon, Bangkok 10200",
				13.768747,
				100.500471,
				new BigDecimal("1000000000.00"),
				new BigDecimal("20000000.00"));

		Center center2 = new Center(
				2,
				"Branch center 1",
				"1 Rat Burana 27 Alley, Rat Burana, Bangkok 10140",
				13.678239,
				100.513642,
				new BigDecimal("300000000.00"),
				new BigDecimal( "4000000.00"));
		centerRepository.save(center1);
		centerRepository.save(center2);

		Truck truck1 = new Truck(
				1,
				13.73811900540664,
				100.49090925318582
		);
		Truck truck2 = new Truck(
				2,
				13.745664098170202,
				100.48543726905037
		);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse("2022-03-18 23:30:10", formatter);
		truck1.setLastUpdated(dateTime);
		truck2.setLastUpdated(dateTime);
		truckRepository.save(truck1);
		truckRepository.save(truck2);

	}

	public static void main(String[] args) {
		SpringApplication.run(CashboxApplication.class, args);
	}

}
