package br.com.rafael_tech.screenMatchSpringBoot.dto;

import br.com.rafael_tech.screenMatchSpringBoot.model.Category;

public record SerieDTO(
    Long id,
    String title,
    Double rating,
    Integer totalSeasons,
    String plot,
    Category genre,
    String actors,
    String poster) {
}
