package br.com.rafael_tech.screenMatchSpringBoot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {
    //rota das series
    @GetMapping("/series")
    public String getSeries(){
        return "That are the series";
    }


}
