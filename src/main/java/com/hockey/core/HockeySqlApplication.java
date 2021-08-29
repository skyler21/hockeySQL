package com.hockey.core;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HockeySqlApplication {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(HockeySqlApplication.class);
		
		logger.debug("HockeySQL application has started");
				
		SpringApplication.run(HockeySqlApplication.class, args);
	}

}
