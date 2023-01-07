package com.hidhayaths.springkeycloakoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
public class SpringKeycloakOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringKeycloakOauth2Application.class, args);
	}

}
