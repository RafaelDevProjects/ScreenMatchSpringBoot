package br.com.rafael_tech.screenMatchSpringBoot.repository;

import br.com.rafael_tech.screenMatchSpringBoot.model.Category;
import br.com.rafael_tech.screenMatchSpringBoot.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainingIgnoreCase(String seriesName);
    List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actor, Double rating);
    List<Serie> findTop5ByOrderByRatingDesc();
    List<Serie> findByGenre(Category category);
    List<Serie> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(int qntSeason, Double rating);
}
