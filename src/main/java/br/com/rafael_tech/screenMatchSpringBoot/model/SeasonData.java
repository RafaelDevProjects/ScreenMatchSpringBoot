package br.com.rafael_tech.screenMatchSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Season") Integer seasonNumber,
                         @JsonAlias("Episodes") ArrayList<EpisodeData> episodes) {
}
