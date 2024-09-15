package br.com.rafael_tech.screenMatchSpringBoot;

import br.com.rafael_tech.screenMatchSpringBoot.main.Main;
import br.com.rafael_tech.screenMatchSpringBoot.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchSprintBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchSprintBootApplication.class, args);
	}
}
