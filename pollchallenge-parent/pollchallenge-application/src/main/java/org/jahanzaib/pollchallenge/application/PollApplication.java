package org.jahanzaib.pollchallenge.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.jahanzaib.pollchallenge")
public class PollApplication {
	public static void main(String[] args) {
		SpringApplication.run(PollApplication.class, args);
	}
}
