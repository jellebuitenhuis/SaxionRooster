import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tester {

        public static void main(String[] args) throws Exception {
            CalendarParse calendarParse = new CalendarParse();
            int i = 0;
            String url;
            Scanner sc = new Scanner(System.in);
            System.out.println("What class are you in?");
            String userClass = sc.nextLine().trim();
            System.out.println("How many weeks of this quartile?(This week counts as 1)");
            int maxWeeks = sc.nextInt();
            File f = new File("schedule.txt");
            f.delete();
            PrintWriter p = new PrintWriter(f);
            p.println("subject,location,start date,start time,end time,end date,description");
            p.close();
            while(!calendarParse.end() && i < maxWeeks) {
                url = "https://api.roosters.saxion.nl/v2/groups/schedule.xml?group=" + userClass;
                WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.getCookieManager().setCookiesEnabled(true);
                XmlPage currentPage = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(1000);
                PrintWriter out = new PrintWriter("rooster.xml");
                try {
                    out.println(currentPage.asXml());
                } finally {
                    out.close();
                }
                calendarParse.parse();
                i++;
            }


        }
}
