package com.btg.leads_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.btg.leads_api")
public class LeadsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeadsApiApplication.class, args);
	}

}
