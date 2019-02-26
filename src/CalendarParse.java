
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
        Element span11 = doc.select("div.span11").first();
        Elements weeknumber = span11.select("h4");
        for(Element p : weeknumber)
        {
            String week = p.select("small").text();
            week = week.substring(week.indexOf(".")+1).trim();
            System.out.println(week);
            if(week.equals("10"))
            {
                end = true;
            }
        }
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

    public boolean end()
    {
        return end;
    }
}