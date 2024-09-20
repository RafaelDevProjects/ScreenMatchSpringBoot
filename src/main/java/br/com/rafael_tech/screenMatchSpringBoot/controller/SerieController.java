package br.com.rafael_tech.screenMatchSpringBoot.controller;

import br.com.rafael_tech.screenMatchSpringBoot.dto.SerieDTO;
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
    private SerieService Serieservice;

    @GetMapping
    public List<SerieDTO> getSeries(){
        return Serieservice.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getSeriesTop5(){
        return Serieservice.getTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getTop5RealesesSeries(){
        return Serieservice.getRealesesSeries();
    }

    @GetMapping("/{id}")
    public SerieDTO getForId(@PathVariable Long id){
        return Serieservice.getForId(id);
    }


}
