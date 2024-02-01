package com.aex.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AexPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(AexPlatformApplication.class, args);
	}

}
