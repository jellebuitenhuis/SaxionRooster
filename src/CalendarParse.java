
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class CalendarParse {

    public static boolean end = false;

    public CalendarParse()
    {

    }

    public void parse() throws IOException, ParseException {
        File inputFile = new File("rooster.xml");
        CalendarEvent calendarEvent;
        Document doc = Jsoup.parse(inputFile, "UTF-8");
        Elements days = doc.select("days");
        Element week = doc.select("week").first();
        String quartileweek = week.select("quartile_week").first().text();
        quartileweek = quartileweek.substring(quartileweek.indexOf(".")+1).trim();
        if(quartileweek.equals("10"))
        {
            end = true;
        }

        for(Element d : days) {
            Elements subjects = d.select("entries");
            if(!subjects.text().isEmpty())
            {
                for(Element s : subjects) {
                    String day = s.select("date").text();
                    String starttime = s.select("start").text();
                    String endtime = s.select("end").text();
                    String subject = s.select("name").text();
                    String location = s.select("room").text();
                    String description = s.select("teachername").text();
                    calendarEvent = new CalendarEvent(day, starttime, endtime, subject, location, description);
                    calendarEvent.toFile();
                }
            }
        }
    }

    public boolean end()
    {
        return end;
    }
}