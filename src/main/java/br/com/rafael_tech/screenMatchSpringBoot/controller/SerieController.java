package br.com.rafael_tech.screenMatchSpringBoot.controller;

import br.com.rafael_tech.screenMatchSpringBoot.dto.EpisodeDTO;
import br.com.rafael_tech.screenMatchSpringBoot.dto.SerieDTO;
import br.com.rafael_tech.screenMatchSpringBoot.model.Category;
import br.com.rafael_tech.screenMatchSpringBoot.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> getSeries(){
        return service.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getSeriesTop5(){
        return service.getTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getTop5RealesesSeries(){
        return service.getRealesesSeries();
    }

    @GetMapping("/{id}")
    public SerieDTO getForId(@PathVariable Long id){
        return service.getForId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id){
        return service.getAllEpisodesFromSerie(id);
    }

    @GetMapping("/{id}/temporadas/{number}")
    public List<EpisodeDTO> getSeasonEpisodes(@PathVariable Long id, @PathVariable Long number){
        return service.getSeasonFromSerie(id, number);
    }

    @GetMapping("/categoria/{nameCategory}")
    public List<SerieDTO> getSerieForCategory(@PathVariable String nameCategory){
        return service.getSeriesForCategory(nameCategory);
    }


}
