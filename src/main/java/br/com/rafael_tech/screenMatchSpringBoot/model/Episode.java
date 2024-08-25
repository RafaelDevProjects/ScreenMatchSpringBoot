package br.com.rafael_tech.screenMatchSpringBoot.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.SortedMap;

public class Episode {
    private String title;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private LocalDate realeaseDate;
    private Double rating;
    private String plot;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.seasonNumber = seasonNumber;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.episodeNumber();
        try {
            this.rating = Double.valueOf(episodeData.rating());
            this.realeaseDate = LocalDate.parse(episodeData.realeaseDate());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        } catch (DateTimeParseException e) {
            this.realeaseDate = null;
        }
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public LocalDate getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(LocalDate realeaseDate) {
        this.realeaseDate = realeaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "season: " + seasonNumber +
                " | title: " + title +
                " | episodeNumber=" + episodeNumber +
                " | realeaseDate=" + realeaseDate +
                " | rating=" + rating;
    }
}
