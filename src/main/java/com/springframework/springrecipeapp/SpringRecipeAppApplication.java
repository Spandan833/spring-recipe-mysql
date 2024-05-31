package com.springframework.springrecipeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringRecipeAppApplication {

	//Spandan 26/5/24
	public static void main(String[] args) {
		SpringApplication.run(SpringRecipeAppApplication.class, args);
	}

}
