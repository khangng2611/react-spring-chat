package com.hcmut.chatterbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ChatterBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatterBoxApplication.class, args);
	}

}
