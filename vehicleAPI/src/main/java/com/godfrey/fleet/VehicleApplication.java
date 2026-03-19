package com.godfrey.fleet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VehicleApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(VehicleApplication.class, args);
	}

//	@Bean
//	CommandLineRunner generatePassword(PasswordEncoder encoder) {
//		return args -> System.out.println(encoder.encode("admin123"));
//	}

}
