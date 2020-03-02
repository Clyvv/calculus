package com.example.calculus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.calculus.service.CalculusService;
import com.example.calculus.service.CalculusServiceLocalImpl;

@SpringBootApplication
public class CalculusApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculusApplication.class, args);
	}

	@Bean
	public CalculusService getCalculusService() {
		return new CalculusServiceLocalImpl();
	}


}
