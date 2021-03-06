package parser;

import java.io.IOException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
class City{
    private String name;
    private String url;
    private int numberOfCitizens;
    private int yearOfFound;
    private float area;
    private String administrativeArea;
    public Coordinates coordinates;

    private static final int INFO_SIZE = 6;


    public static City parse(Element city) {
        String link = "https://uk.wikipedia.org%s";
        Elements info = city.select("td");
        if (info.size() == INFO_SIZE){
            City newCity = new City();
            Element anchor = info.get(1).select("a").get(0);

            newCity.setName(anchor.attr("title"));
            newCity.setUrl(String.format(link, anchor.attr("href")));
            newCity.setAdministrativeArea(info.get(2).select("a").attr("title"));
            newCity.setNumberOfCitizens(convertCitizens(info.get(3).text().replaceAll(" ", "")));
            newCity.setYearOfFound(convertYear(info.get(4).select("a").get(0).attr("title")));
            newCity.setArea(Float.parseFloat(info.get(5).text()));
            newCity.setCoordinates(new Coordinates(String.format(link, anchor.attr("href"))));
            return newCity;
        }
        return null;
    }


    private static int convertYear(String info){
        if (info.contains("-ті"))
            info = info.substring(0, info.indexOf("-ті"));
        else if(info.contains(" до"))
            info = info.substring(0, info.indexOf(" до"));
        else if(info.contains(" століття")){
            info = info.substring(0, info.indexOf(" століття"));
            info = Integer.toString(Integer.parseInt(info) - 1) + "50";
        }
        int converted = Integer.parseInt(info);
        return converted;}


    private static int convertCitizens(String area){
        if (area.contains("!"))
            area = area.substring(0, area.indexOf("!") - 1);

        area = area.replace("\u00a0","");
        return Integer.parseInt(area);
    }

    public String getWeather() throws IOException, UnirestException{
        String weather = Weather.generateWeather(this);
        return weather;
    }
}
