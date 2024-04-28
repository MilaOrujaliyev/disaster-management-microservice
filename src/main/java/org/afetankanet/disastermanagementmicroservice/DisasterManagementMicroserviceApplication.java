package org.afetankanet.disastermanagementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DisasterManagementMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisasterManagementMicroserviceApplication.class, args);
	}

}
