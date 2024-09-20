package br.com.rafael_tech.screenMatchSpringBoot.services;

import br.com.rafael_tech.screenMatchSpringBoot.dto.SerieDTO;
import br.com.rafael_tech.screenMatchSpringBoot.model.Serie;
import br.com.rafael_tech.screenMatchSpringBoot.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    private List<SerieDTO> convertToSerieDTO(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getRating(), s.getTotalSeasons(), s.getPlot(), s.getGenre(), s.getActors(), s.getPoster()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> getAllSeries(){
        return convertToSerieDTO(repository.findAll());
    }

    public List<SerieDTO> getTop5Series(){
        return convertToSerieDTO(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SerieDTO> getRealesesSeries(){
        return convertToSerieDTO(repository.RealeasedSeries());
    }

    public SerieDTO getForId(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitle(), s.getRating(), s.getTotalSeasons(), s.getPlot(), s.getGenre(), s.getActors(), s.getPoster());
        }
        return null;
    }
}
