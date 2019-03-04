import java.io.*;

public class CalendarEvent {

    public String date;
    public String starttime;
    public String endtime;
    public String subject;
    public String location;
    public String description;

    public CalendarEvent(String date, String starttime, String endtime, String subject, String location, String description)
    {
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.subject = subject;
        this.location = location;
        this.description = description;
    }

    public String toString()
    {
        System.out.println(date + " " + starttime + " " + endtime);
        return "";
    }

    public void toFile() throws IOException{
        FileWriter fileWriter = new FileWriter("schedule.txt", true);
        PrintWriter out = new PrintWriter(fileWriter);

        try {
            out.println(subject + "," + location + ","+ date + "," + starttime + "," + endtime + "," + date + "," + description);
        }
        finally {
            out.close();
        }
    }
}