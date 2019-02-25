
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class CalendarParse {

    public CalendarParse()
    {

    }

    public void parse() throws IOException {
        String[] day = new String[5];
        File inputFile = new File("rooster.xml");
        Document doc = Jsoup.parse(inputFile, "UTF-8");
        for(int i = 0; i < 5;i++) {
            Element table = doc.select("table.scheduletable").get(i);
            Element date = table.select("th.tabletop").first();
            day[i] = date.text();
            Elements time = table.select("th.times");
            for(Element e : time)
            {
                day[i] += " " + e.text();
            }
            System.out.println(day[i]);
        }
    }
}
