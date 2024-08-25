package br.com.rafael_tech.screenMatchSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(@JsonAlias("Title") String title,
                        @JsonAlias("imdbRating") String rating,
                        @JsonAlias("TotalSeasons") Integer totalSeasons,
                        @JsonAlias("Plot") String plot,
                        @JsonAlias("Genre") String genre,
                        @JsonAlias("Actors") String actors,
                        @JsonAlias("Poster") String poster) {
}
