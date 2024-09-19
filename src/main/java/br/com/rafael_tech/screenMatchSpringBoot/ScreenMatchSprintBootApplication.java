package br.com.rafael_tech.screenMatchSpringBoot;

import br.com.rafael_tech.screenMatchSpringBoot.main.Main;
import br.com.rafael_tech.screenMatchSpringBoot.repository.SerieRepository;
import br.com.rafael_tech.screenMatchSpringBoot.services.SerieService;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.support.Repositories;

import javax.sound.midi.MidiChannel;

@SpringBootApplication
public class ScreenMatchSprintBootApplication{

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchSprintBootApplication.class, args);
	}
}
