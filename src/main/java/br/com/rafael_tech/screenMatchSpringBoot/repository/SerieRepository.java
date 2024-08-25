package br.com.rafael_tech.screenMatchSpringBoot.repository;

import br.com.rafael_tech.screenMatchSpringBoot.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
