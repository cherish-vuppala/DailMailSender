package com.cherish.mailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaMailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaMailSenderApplication.class, args);
	}

}
