package com.delpipi.authservice;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.delpipi.entities.AppRole;
import com.delpipi.entities.AppUser;
import com.delpipi.repositories.AppUserRepository;
import com.delpipi.services.AccountService;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "com.delpipi.entities") // sans cela les entities ne sont pas visible pour creer la base donnée
@ComponentScan(basePackages="com.delpipi") // sans cela impossible de faire des requete HTTP
@EnableJpaRepositories(basePackageClasses = {AppUserRepository.class, AppUserRepository.class, })
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			accountService.addNewRole(new AppRole(null, "USER"));
			accountService.addNewRole(new AppRole(null, "ADMIN"));
			accountService.addNewRole(new AppRole(null, "CUSTOMER_MANAGER"));
			accountService.addNewRole(new AppRole(null, "PRODUCT_MANAGER"));
			accountService.addNewRole(new AppRole(null, "BILLS_MANAGER"));

			accountService.addNewUser(new AppUser(null, "user1", "123", new ArrayList<>()));
			accountService.addNewUser(new AppUser(null, "admin", "123", new ArrayList<>()));
			accountService.addNewUser(new AppUser(null, "user2", "123", new ArrayList<>()));
			accountService.addNewUser(new AppUser(null, "user3", "123", new ArrayList<>()));
			accountService.addNewUser(new AppUser(null, "user4", "123", new ArrayList<>()));
			
			accountService.addRoleToUser("user1", "USER");
			accountService.addRoleToUser("admin", "USER");
			accountService.addRoleToUser("admin", "ADMIN");
			accountService.addRoleToUser("user2", "USER");
			accountService.addRoleToUser("user2", "CUSTOMER_MANAGER");
			accountService.addRoleToUser("user3", "USER");
			accountService.addRoleToUser("user3", "PRODUCT_MANAGER");
			accountService.addRoleToUser("user4", "USER");
			accountService.addRoleToUser("user4", "BILLS_MANAGER");
		};
	}


}
