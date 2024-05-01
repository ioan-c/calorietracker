package com.mj.calorietracker;

import com.mj.calorietracker.repository.AppRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan
@PropertySource("classpath:git.properties")
@EnableJpaRepositories(repositoryBaseClass = AppRepositoryImpl.class)
public class CalorietrackerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CalorietrackerApplication.class, args);
	}

}
