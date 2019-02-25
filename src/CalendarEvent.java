import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CalendarEvent {

    public String date;
    public String time;

    public CalendarEvent(String date, String time)
    {
        this.date = date;
        this.time = time;
    }

    public String toString()
    {
        System.out.println(date + " " + time);
        return "";
    }

    public void toFile() throws IOException {
        FileWriter fileWriter = new FileWriter("schedule.txt", true);
        PrintWriter out = new PrintWriter(fileWriter);
        try {
            out.println(date + " " + time);
        }
        finally {
            out.close();
        }
    }
}
