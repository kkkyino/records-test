package com.records.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.records.api", "com.records.common"})
public class RecordsSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordsSpringApplication.class, args);
	}

}
