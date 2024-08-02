package br.com.rafael_tech.screenMatchSprintBoot.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(@JsonAlias("Title") String title,
                          @JsonAlias("Episode") Integer episodeNumber,
                          @JsonAlias("Plot") String plot,
                          @JsonAlias("Released") String realeaseDate,
                          @JsonAlias("imdbRating") String rating)  {
}
