package com.example.ipldashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.example" })
public class IplDashboardApplication {

	public static void main(String[] args) {
		// SpringApplication.run(IplDashboardApplication.class, args);
		System.exit(SpringApplication.exit(SpringApplication.run(IplDashboardApplication.class, args)));
	}

}
