package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Coordinates {
    String url;

    Coordinates(String url){
        this.url = url;
    }

    public float get_cor(String way) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements coords = doc.select("span.geo");
        float cor = 0;
        if(coords.size() != 0 && way.equals("lon"))
            cor = Float.parseFloat(coords.get(0).text().substring(0, coords.get(0).text().indexOf(";")));
        else if(coords.size() != 0 && way.equals("lat"))
            cor = Float.parseFloat(coords.get(0).text().substring(coords.get(0).text().indexOf(";") + 2));

        return cor;
    }
}
