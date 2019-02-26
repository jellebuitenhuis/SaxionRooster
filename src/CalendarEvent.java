import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CalendarEvent {

    public String date;
    public String time;
    public String subject;
    public String location;
    public String description;

    public CalendarEvent(String date, String time, String subject, String location, String description)
    {
        this.date = date;
        this.time = time;
        this.subject = subject;
        this.location = location;
        this.description = description;
    }

    public String toString()
    {
        System.out.println(date + " " + time);
        return "";
    }

    public void toFile() throws IOException, ParseException {
        FileWriter fileWriter = new FileWriter("schedule.txt", true);
        PrintWriter out = new PrintWriter(fileWriter);
        String starttime = time.substring(0, time.indexOf("-")).trim();
        String endtime = time.substring(time.indexOf("-")+1).trim();

        Scanner sc = new Scanner(date);
        sc.skip("[^0-9]*");
        int startday = sc.nextInt();
        String startday2 = String.valueOf(startday);
        String month = sc.next();
        String year = sc.next();
        Date date = new SimpleDateFormat("MMMM").parse(month);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        month = String.valueOf(cal.get(Calendar.MONTH)+1);
        String eventdate = startday2+"/"+month+"/"+year;
        try {
            out.println(subject + "," + location + ","+ eventdate + "," + starttime + "," + endtime + "," + eventdate + "," + description);
        }
        finally {
            out.close();
        }
    }
}