package com.LakshBanking.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
//telling Spring Boot to enable JPA Auditing, and to use a custom implementation for determining the current auditor
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservices Documentation for Laksh Bank",
				description = "This is In-detailed Account Services made with deign patterns which can be later modified as per the requirements",
				version = "v1.0.0",
				contact = @Contact(
						name = "Lakshya Acharya",
						email = "lakshyaacharya.engineer@gmail.com",
						url = "https://www.linkedin.com/in/lakshya-acharya/"
				),
				license = @License(
						name = "Owned By Lakshya Acharya",
						url = "https://www.linkedin.com/in/lakshya-acharya/"
				)
		)
)
//this helps in documentation of Swagger UI spring doc
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
