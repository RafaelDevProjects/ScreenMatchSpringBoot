package br.com.rafael_tech.screenMatchSpringBoot.dto;

import br.com.rafael_tech.screenMatchSpringBoot.model.Serie;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public record EpisodeDTO(
     String title,
     Integer seasonNumber,
     Integer episodeNumber) {
}
