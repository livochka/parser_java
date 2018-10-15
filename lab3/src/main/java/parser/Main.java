package parser;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;


class Main{
    public static void main(String[] args) throws IOException, URISyntaxException, UnirestException {
        System.out.print("Введіть назву міста: ");
        Scanner s = new Scanner(System.in);
        String cityTitle = s.nextLine();
        City[] cities = getCities();
        boolean avail = false;
        for (City currentCity : cities) {
            if (currentCity.getName().equals(cityTitle)) {
                avail = true;
                allTheInfo(currentCity);
            }
        }
        if (!avail)
            System.out.println("На жаль, даного міста немає в базі, спробуйте ввести інше");
    }


    public static City[] getCities() throws IOException, URISyntaxException, UnirestException{
        Document doc = Jsoup.connect("https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)").get();
        Elements cities = doc.select("table tr");
        City[] ukrainianCities = new City[cities.size() - 1];
        int counter = 0;
        for (Element city : cities) {
            City newCity = City.parse(city);
            if (newCity != null){
                //System.out.println(newCity.getWeather());
                ukrainianCities[counter] = newCity;
                counter++;
            }
        }
        return ukrainianCities;
    }

    public static void allTheInfo(City city) throws IOException, URISyntaxException, UnirestException{
        System.out.println("Погода для міста " + city.getName() + "(" + city.getYearOfFound() + "р.), " +
                city.getAdministrativeArea());
        System.out.println(city.getWeather());
    }
}