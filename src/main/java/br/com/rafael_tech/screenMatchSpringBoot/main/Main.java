package br.com.rafael_tech.screenMatchSpringBoot.main;

import br.com.rafael_tech.screenMatchSpringBoot.model.SeasonData;
import br.com.rafael_tech.screenMatchSpringBoot.model.Serie;
import br.com.rafael_tech.screenMatchSpringBoot.model.SerieData;
import br.com.rafael_tech.screenMatchSpringBoot.repository.SerieRepository;
import br.com.rafael_tech.screenMatchSpringBoot.services.ApiConsumption;
import br.com.rafael_tech.screenMatchSpringBoot.services.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=38198bea";
    private final List<SerieData> serieData = new ArrayList<>();
    private final ApiConsumption apiConsumption = new ApiConsumption();
    private final ConvertData convertData = new ConvertData();
    private SerieRepository repository;

    public Main(SerieRepository repository){
        this.repository = repository;
    }

    public void displayMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search Serie
                    2 - Search Episode
                    3 - Show searched series
                                    
                    0 - exit                                 
                    """;

            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchWebSerie();
                    break;
                case 2:
                    searchEpisodeForSerie();
                    break;
                case 3:
                    showSearchedSerie();
                    break;
                case 0:
                    System.out.println("exit...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void searchWebSerie() {
        SerieData data = getDadosSerie();
        Serie serie = new Serie(data);
        //serieData.add(data);
        repository.save(serie);
        System.out.println(data);
    }

    private SerieData getDadosSerie() {
        System.out.println("Type the serie that you want: ");
        var serieName = scanner.nextLine();
        var json = apiConsumption.getData(ADDRESS + serieName.replace(" ", "+") + API_KEY);
        SerieData data = convertData.getData(json, SerieData.class);
        return data;
    }

    private void searchEpisodeForSerie(){
        SerieData serieData = getDadosSerie();
        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= serieData.totalSeasons(); i++) {
            var json = apiConsumption.getData(ADDRESS + serieData.title().replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = convertData.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }
        seasons.forEach(System.out::println);
    }

    private void showSearchedSerie(){
        List<Serie> series = new ArrayList<>();
        series = serieData.stream()
                .map(d -> new Serie(d))
                        .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }


//        System.out.println("type the serie for search");
//        var serieName = scanner.nextLine();
//        String apiAdress = ADDRESS + serieName.replace(" ", "+") + API_KEY;
//        String json = apiConsumption.getData(apiAdress);
//        SerieData serieData = convertData.getData(json, SerieData.class);
//        System.out.println(serieData);
//
//        List<SeasonData> seasons = new ArrayList<>();
//
//        // adiciona a temporada a lista de temporadas
//		for (int i = 1; i <= serieData.totalSeasons(); i++) {
//			json = apiConsumption.getData(ADDRESS + serieName.replace(" ", "+") +"&season=" + i + API_KEY);
//			SeasonData seasonData = convertData.getData(json, SeasonData.class);
//            seasons.add(seasonData);
//		}
//
//        // mostra todas as temporadas
//        seasons.forEach(System.out::println);
//
//        // mostra todos os episodios mas não mostra a temporada em que os episodios estão
//        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println("episode " + e.episodeNumber() + ": " + e.title())));
//
//        // pega todos os episodios e coloca em uma lista
//        List<EpisodeData> episodesData = seasons.stream()
//                .flatMap(t -> t.episodes().stream())
//                .collect(Collectors.toList());
//
//        // mostra o top 10 episodios
//        System.out.println("\n Top 10 episodes");
//        episodesData.stream()
//                .filter(e -> !e.rating().equalsIgnoreCase("N/A")) // ignora todos as avaliações que estiverem como N/A
//                .sorted(Comparator.comparing(EpisodeData::rating).reversed()) // ordena a lista
//                .limit(10) // limita a lista a 10
//                .map(e -> e.title().toUpperCase()) // trasforma todos os titulos de Episodios em letras maiusculas
//                .forEach(System.out::println);
//
//        // lista de episodios e mostra a temporada de cada episodio
//        List<Episode> episodes = seasons.stream()
//                .flatMap(s -> s.episodes().stream()
//                    .map(d -> new Episode(s.seasonNumber(), d)) // cria um objeto do tipo episodio que tem o contrutor numero da temporada e os dados do episodio (EpisodeData)
//                ).collect(Collectors.toList());
//
//        episodes.forEach(System.out::println);
//
//        // Encontra um episodio em especifico e mostra na tela a temporada que esta o episosio e o nome do episodio
//        System.out.println("Title Episode: ");
//        var searchTitle = scanner.nextLine();
//        Optional<Episode> foundEpisode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(searchTitle.toUpperCase()))
//                .findFirst();
//        if(foundEpisode.isPresent()){
//            System.out.println("Episode found! ");
//            System.out.println("Temporada: " + foundEpisode.get().getSeasonNumber() + " Episode: " + foundEpisode.get().getTitle());
//        } else{
//            System.out.println("Not found");
//        }
////
////        System.out.println("What year do you want to watch the episodes from?");
////        var year = scanner.nextInt();
////        scanner.nextLine();
////
////        LocalDate searchDate = LocalDate.of(year, 1, 1);
////
////        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////        episodes.stream()
////                .filter(e -> e.getRealeaseDate() != null && e.getRealeaseDate().isAfter(searchDate))
////                .forEach(e -> System.out.println(
////                        "Season: " + e.getSeasonNumber() +
////                                " | Episode: " + e.getTitle() +
////                                " | Release year: " + e.getRealeaseDate().format(df)
////                ));
//
//        // pega todos os episodios e coloca em um map que tem uma chave e um valor, a chave é um numero inteiro e o valor é um double da media de avaliações de cada episodio
//        Map<Integer, Double> ratingForSeason = episodes.stream()
//                .filter(e -> e.getRating() > 0.0)
//                .collect(Collectors.groupingBy(Episode::getSeasonNumber,
//                        Collectors.averagingDouble(Episode::getRating)));
//        System.out.println(ratingForSeason);
//
//        // pega as estatisticas das avaliações de cada episodio; ex de saida: DoubleSummaryStatistics{count=28, sum=212,600000, min=6,500000, average=7,592857, max=9,000000}
//        DoubleSummaryStatistics est = episodes.stream()
//                .filter(e -> e.getRating() > 0.0)
//                .collect(Collectors.summarizingDouble(Episode::getRating));
//
//        System.out.println("Average: " + est.getAverage());
////        System.out.println("Best Episode: " + est.getMax());
////        System.out.println("Worst Episode: " + est.getMin());
////        System.out.println("number of reviews : " + est.getCount());
//
//
//    }
}
