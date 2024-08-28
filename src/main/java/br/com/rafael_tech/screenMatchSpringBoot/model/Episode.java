package br.com.rafael_tech.screenMatchSpringBoot.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.SortedMap;

@Entity
@Table(name = "episodios")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private LocalDate realeaseDate;
    private Double rating;
    @ManyToOne
    private Serie serie;

    public Episode(){};

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
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


    @Override
    public String toString() {
        return "season: " + seasonNumber +
                " | title: " + title +
                " | episodeNumber=" + episodeNumber +
                " | realeaseDate=" + realeaseDate +
                " | rating=" + rating;
    }
}
