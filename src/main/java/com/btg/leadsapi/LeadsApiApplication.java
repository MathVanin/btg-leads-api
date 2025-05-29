package com.btg.leadsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.btg.leadsapi")
public class LeadsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LeadsApiApplication.class, args);
	}
}
