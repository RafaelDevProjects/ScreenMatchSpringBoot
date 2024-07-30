package br.com.rafael_tech.screenMatchSprintBoot;

import br.com.rafael_tech.screenMatchSprintBoot.model.SerieData;
import br.com.rafael_tech.screenMatchSprintBoot.services.ApiConsumption;
import br.com.rafael_tech.screenMatchSprintBoot.services.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchSprintBootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchSprintBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("first SpringProject without web");
		ApiConsumption apiConsumption = new ApiConsumption();
		var json = apiConsumption.getData("http://www.omdbapi.com/?t=Game+of+Thrones&Season=1&apikey=38198bea");
		System.out.println(json);

		ConvertData convertData = new ConvertData();
		SerieData serieDatas = convertData.getData(json, SerieData.class);
		System.out.println(serieDatas);
	}
}
