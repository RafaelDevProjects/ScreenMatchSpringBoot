package br.com.rafael_tech.screenMatchSpringBoot.model;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Double rating;
    private Integer totalSeasons;
    private String plot;
    private Category genre;
    private String actors;
    private String poster;

    public Serie(SerieData serieData){
        this.title = serieData.title();
        this.rating = OptionalDouble.of(Double.valueOf(serieData.rating())).orElse(0);
        this.totalSeasons = serieData.totalSeasons();
        this.plot = serieData.plot();
        this.genre = Category.fromString(serieData.genre().split(",")[0].trim());
        this.actors = serieData.actors();
        this.poster = serieData.poster();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return  "title=" + title +
                ", rating=" + rating +
                ", totalSeasons=" + totalSeasons +
                ", plot='" + plot +
                ", genre=" + genre +
                ", actors='" + actors +
                ", poster='" + poster +
                '}';
    }
}
