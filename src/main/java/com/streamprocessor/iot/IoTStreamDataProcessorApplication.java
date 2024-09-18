package com.streamprocessor.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IoTStreamDataProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTStreamDataProcessorApplication.class, args);
	}

}
