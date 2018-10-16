package parser;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@Setter
@Getter
public class Weather {


    public static String generateWeather(City city)throws IOException, UnirestException {
        String weather_url = "http://api.apixu.com/v1/current.json?key=f6da3a783d34446f8f4120423180410&q=" +
                Float.toString(city.coordinates.get_cor("lon")) + "," + Float.toString(city.coordinates.get_cor("lat"));
        HttpResponse<JsonNode> jsonResponse = Unirest.post(weather_url).asJson();
        JSONObject myObj = jsonResponse.getBody().getObject();
        String weather;
        try {
            weather = "Температура " + Double.toString(myObj.getJSONObject("current").getDouble("temp_c")) +
                    ", " + myObj.getJSONObject("current").getJSONObject("condition").getString("text") +
                    "\nШвидкість вітру - " + Double.toString(myObj.getJSONObject("current").getDouble("wind_kph")) +
                    " км/год, а вологість - " + Integer.toString(myObj.getJSONObject("current").getInt("humidity")) +
                    "%";
        }
        catch (JSONException e){
            weather = "На даний момент немає інформації про погоду у даному місті";
        }
        return weather;
    }




}
