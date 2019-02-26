
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class CalendarParse {

    public CalendarParse()
    {

    }

    public void parse() throws IOException, ParseException {
        File inputFile = new File("rooster.xml");
        CalendarEvent calendarEvent;
        Document doc = Jsoup.parse(inputFile, "UTF-8");
        for(int i = 0; i < 5;i++) {
            Element table = doc.select("table.scheduletable").get(i);
            Element date = table.select("th.tabletop").first();
            String day = date.text();
            Elements times = table.select("th.times");
            int j = 2;
            for(Element e : times)
            {
                String subject = table.select("span").get(j).text();
                String location = table.select("span").get(j+3).text();
                String time = e.text();
                String description = table.select("span").get(j+1).text() + " " + table.select("span").get(j+4).text();
                calendarEvent = new CalendarEvent(day, time, subject, location, description);
                calendarEvent.toFile();
                System.out.println(calendarEvent.toString());
                j+=7;
            }
        }
    }
}