package com.links.linkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.links.linkservice.config.EnvConfig;

@SpringBootApplication
public class LinkserviceApplication {

	public static void main(String[] args) {
		EnvConfig.loadEnv();
		SpringApplication.run(LinkserviceApplication.class, args);
	}
}
