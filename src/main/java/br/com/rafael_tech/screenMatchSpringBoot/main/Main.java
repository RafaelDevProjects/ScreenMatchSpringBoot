package br.com.rafael_tech.screenMatchSpringBoot.main;

import br.com.rafael_tech.screenMatchSpringBoot.model.Episode;
import br.com.rafael_tech.screenMatchSpringBoot.model.EpisodeData;
import br.com.rafael_tech.screenMatchSpringBoot.model.SeasonData;
import br.com.rafael_tech.screenMatchSpringBoot.model.SerieData;
import br.com.rafael_tech.screenMatchSpringBoot.services.ApiConsumption;
import br.com.rafael_tech.screenMatchSpringBoot.services.ConvertData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=38198bea";
    private ApiConsumption apiConsumption = new ApiConsumption();
    private ConvertData convertData = new ConvertData();

    public void displayMenu(){
        System.out.println("type the serie for search");
        var serieName = scanner.nextLine();
        String apiAdress = ADDRESS + serieName.replace(" ", "+") + API_KEY;
        String json = apiConsumption.getData(apiAdress);
        SerieData serieData = convertData.getData(json, SerieData.class);
        System.out.println(serieData);

        List<SeasonData> seasons = new ArrayList<>();

		for (int i = 1; i <= serieData.totalSeasons(); i++) {
			json = apiConsumption.getData(ADDRESS + serieName.replace(" ", "+") +"&season=" + i + API_KEY);
			SeasonData seasonData = convertData.getData(json, SeasonData.class);
            seasons.add(seasonData);
		}

        seasons.forEach(System.out::println);

//        for (int i = 0; i < serieData.totalSeasons(); i++) {
//            List<EpisodeData> seasonEpisodes = seasons.get(i).episodes();
//            System.out.println();
//            System.out.println("temporada " + (i + 1));
//            for (int j = 0; j < seasonEpisodes.size(); j++) {
//                System.out.println("Episode " + (j + 1) + ": " +seasonEpisodes.get(j).title());
//            }
//        }

        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println("episode " + e.episodeNumber() + ": " + e.title())));

        List<EpisodeData> episodesData = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episodes");
        episodesData.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                    .map(d -> new Episode(t.seasonNumber(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("What year do you want to watch the episodes from?");
        var year = scanner.nextInt();
        scanner.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodes.stream()
                .filter(e -> e.getRealeaseDate() != null && e.getRealeaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeasonNumber() +
                                " | Episode: " + e.getTitle() +
                                " | Release year: " + e.getRealeaseDate().format(df)
                ));

    }
}
