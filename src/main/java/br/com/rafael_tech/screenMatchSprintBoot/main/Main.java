package br.com.rafael_tech.screenMatchSprintBoot.main;

import br.com.rafael_tech.screenMatchSprintBoot.model.EpisodeData;
import br.com.rafael_tech.screenMatchSprintBoot.model.SeasonData;
import br.com.rafael_tech.screenMatchSprintBoot.model.SerieData;
import br.com.rafael_tech.screenMatchSprintBoot.services.ApiConsumption;
import br.com.rafael_tech.screenMatchSprintBoot.services.ConvertData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    }
}
