package br.com.rafael_tech.screenMatchSpringBoot.main;

import br.com.rafael_tech.screenMatchSpringBoot.model.*;
import br.com.rafael_tech.screenMatchSpringBoot.repository.SerieRepository;
import br.com.rafael_tech.screenMatchSpringBoot.services.ApiConsumption;
import br.com.rafael_tech.screenMatchSpringBoot.services.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=38198bea";
    private final ApiConsumption apiConsumption = new ApiConsumption();
    private final ConvertData convertData = new ConvertData();

    private final SerieRepository repository;
    private List<Serie> series = new ArrayList<>();

    public Main(SerieRepository repository){
        this.repository = repository;
    }

    public void displayMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search Series
                    2 - Search Episode for series
                    3 - Show searched series
                    4 - Search Series from Title
                    5 - Search Series from actor
                    6 - Top 5 Series
                    7 - Search Series from category
                    8 - Search for series with less than X seasons
                    8 - Search for episode with excerpt
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
                case 4:
                    searchSerieForTitle();
                    break;
                case 5:
                    searchSeriesForActor();
                    break;
                case 6:
                    searchTop5Series();
                    break;
                case 7:
                    searchSerieForCategory();
                    break;
                case 8:
                    filterByNumberOfSeasons();
                case 9:
                    searchEpisodeForExcerpt();
                case 0:
                    System.out.println("exit...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }


    /**
     * Busca uma série e salva no banco de dados.
     */
    private void searchWebSerie() {
        SerieData data = getDadosSerie();
        Serie serie = new Serie(data);
        repository.save(serie);
        System.out.println(data);
    }

    /**
     * Solicita o nome da série e busca seus dados via API.
     * @return dados da série.
     */
    private SerieData getDadosSerie() {
        System.out.println("Type the series that you want: ");
        var serieName = scanner.nextLine();
        var json = apiConsumption.getData(ADDRESS + serieName.replace(" ", "+") + API_KEY);
        return convertData.getData(json, SerieData.class);
    }

    /**
     * Exibe todos os episódios de uma série selecionada.
     */
    private void searchEpisodeForSerie() {
        showSearchedSerie();
        System.out.println("Chose one series to see all episodes: ");
        var serieName = scanner.nextLine();

        Optional<Serie> serie = repository.findByTitleContainingIgnoreCase(serieName);

        if(serie.isPresent()) {
            var serieFounded = serie.get();
            List<SeasonData> seasonsData = new ArrayList<>();

            for (int i = 1; i <= serieFounded.getTotalSeasons(); i++) {
                var json = apiConsumption.getData(ADDRESS + serieFounded.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData seasonData = convertData.getData(json, SeasonData.class);
                seasonsData.add(seasonData);
            }
            seasonsData.forEach(System.out::println);

            List<Episode> episodes = seasonsData.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.seasonNumber(), e)))
                    .collect(Collectors.toList());
            serieFounded.setEpisodes(episodes);
            repository.save(serieFounded);
        } else {
            System.out.println("Series not Found");
        }
    }

    /**
     * Exibe todas as séries armazenadas, ordenadas por gênero.
     */
    private void showSearchedSerie() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    /**
     * Busca uma série pelo título e exibe os dados se encontrada.
     */
    private void searchSerieForTitle() {
        System.out.println("Search a series from Title: ");
        var serieName = scanner.nextLine();
        Optional<Serie> searchedSerie = repository.findByTitleContainingIgnoreCase(serieName);

        if (searchedSerie.isPresent()) {
            System.out.println("Series Data: " + searchedSerie.get());
        } else {
            System.out.println("Series not found");
        }
    }

    /**
     *  Busca series na quais um ator ja participou, a serie precisa ter uma avaliacao minima e um ator para ser buscada.
     */
    private void searchSeriesForActor() {
       System.out.println("Type a Actor that you like for search the series that he has participated: ");
        var actor = scanner.nextLine();
        System.out.println("Enter the minimum rating that films must have: ");
        var rating = scanner.nextDouble();

        List<Serie> fSeries = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actor, rating);
        System.out.println("Series that " + actor + " has worked: ");
        fSeries.forEach(s ->
                System.out.println(s.getTitle() + " rating: " + s.getRating()));

    }

    /**
     *  Busca o as top 5 series no banco de dados
     */
    private void searchTop5Series() {
        // acha o top 5 na ordem decrecente
        List<Serie> topFive = repository.findTop5ByOrderByRatingDesc();
        topFive.forEach(s ->
                System.out.println(s.getTitle() + " rating: " + s.getRating()));
    }

    /**
     *  Busca uma serie por categoria
     */
    private void searchSerieForCategory() {
        System.out.println("Enter the category: ");
        var genreName = scanner.nextLine();
        try {
            Category category = Category.fromString(genreName);
            List<Serie> seriesForCategory = repository.findByGenre(category);
            System.out.println("Series for Category " + genreName);
            if (seriesForCategory.isEmpty()) {
                System.out.println("We don't have a series with this category yet");
            } else {
                seriesForCategory.forEach(s ->
                        System.out.println(s.getTitle() + " - Category: " + s.getGenre()));
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     *  Busca uma serie com menos  temporadas do que o usuário digita, o usuario também digita o numero mínimo das avaliações
     */
    private void filterByNumberOfSeasons() {
        System.out.println("Type a number of season that you want ");
        int qntSeason = scanner.nextInt();
        System.out.println("Enter the minimum rating that films must have: ");
        var rating = scanner.nextDouble();
        List<Serie> filterSeries = repository.seriesBySeasonsAndRating(qntSeason, rating);
        System.out.println("-----Filter Series-----");
        filterSeries.forEach(s ->
                System.out.println(s.getTitle() + " - Number of Seasons: " + s.getTotalSeasons() + " | Rating: " + s.getRating()));
    }

    /**
     * Busca um Episodio pelo trecho
     */
    private void searchEpisodeForExcerpt() {
        System.out.println("What is the name of Episode? ");
        var excerptEpisode = scanner.nextLine();
        List<Episode> episodeFounded = repository.episodesForExecerpt(excerptEpisode);
        episodeFounded.forEach(System.out::println);
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
