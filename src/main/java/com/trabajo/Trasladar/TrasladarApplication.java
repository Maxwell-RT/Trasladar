package com.trabajo.Trasladar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = org.springdoc.core.configuration.SpringDocHateoasConfiguration.class)
public class TrasladarApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrasladarApplication.class, args);
	}

}
