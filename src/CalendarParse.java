
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
        File inputFile = new File("rooster.xml");
        CalendarEvent calendarEvent;
        Document doc = Jsoup.parse(inputFile, "UTF-8");
        for(int i = 0; i < 5;i++) {
            Element table = doc.select("table.scheduletable").get(i);
            Element date = table.select("th.tabletop").first();
            String day = date.text();
            Elements times = table.select("th.times");
            for(Element e : times)
            {
                String time = e.text();
                calendarEvent = new CalendarEvent(day, time);
                calendarEvent.toFile();
                System.out.println(calendarEvent.toString());
            }
        }
    }
}
