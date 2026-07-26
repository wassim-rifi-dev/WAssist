package dev.wassim.wassist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WassistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WassistApplication.class, args);
	}

}
