package br.com.rafael_tech.screenMatchSpringBoot.repository;

import br.com.rafael_tech.screenMatchSpringBoot.model.Category;
import br.com.rafael_tech.screenMatchSpringBoot.model.Episode;
import br.com.rafael_tech.screenMatchSpringBoot.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    // Usando JPA
    Optional<Serie> findByTitleContainingIgnoreCase(String seriesName);
    List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, Double rating);
    List<Serie> findTop5ByOrderByRatingDesc();
    List<Serie> findByGenre(Category category);

    // List<Serie> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(int qntSeason, Double rating);

    // Usando JPQL (jAVA PERSISTENCE QUERY LANGUAGE)
    // Parametros são representados pelos ":" | A serie é representada pela classe Serie representada pelo s
    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :qntSeason AND s.rating >= :rating")
    List<Serie> seriesBySeasonsAndRating(int qntSeason, Double rating);

    // pega todos os episodios que contenham o trecho que o usuário passou
    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE e.title ILIKE %:excerptEpisode%")
    List<Episode> episodesForExecerpt(String excerptEpisode);

    // pega as top 5 episodios da serie pedida
    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie ORDER BY e.rating DESC LIMIT 5")
    List<Episode> topEpisodiosForSeries(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie AND YEAR (e.realeaseDate) >= :releaseYear")
    List<Episode> episodeBeforeDate(String releaseYear, Serie serie);

    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodes e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.realeaseDate) DESC LIMIT 5")
    List<Serie> RealeasedSeries();


}
