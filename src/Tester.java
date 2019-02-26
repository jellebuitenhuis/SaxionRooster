import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;

public class Tester {

    static String  user;
    static String pass;

        public static void main(String[] args) throws Exception {
            CalendarParse calendarParse = new CalendarParse();
            int i = 0;
            String url;
            Scanner sc = new Scanner(System.in);
            System.out.println("What class are you in?");
            String userClass = sc.nextLine().trim();
            System.out.println("How many weeks of this quartile?(This week counts as 1)");
            int maxWeeks = sc.nextInt();
            getCreds();
            File f = new File("schedule.txt");
            f.delete();
            PrintWriter out = new PrintWriter(f);
            out.println("subject,location,start date,start time,end time,end date,description");
            out.close();
            while(!calendarParse.end() && i < maxWeeks) {
                url = "https://roosters.saxion.nl/schedule/group:" + userClass + "/week:" + i;
                WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED); //Initiate a WebClient variable.
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.getCookieManager().setCookiesEnabled(true);
                webClient = login(webClient);
                HtmlPage currentPage = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(10000);
                WebResponse response = currentPage.getWebResponse();
                currentPage = webClient.getPage("https://engine.surfconext.nl:443/authentication/sp/consume-assertion");
                webClient.waitForBackgroundJavaScript(10000);
                String content = response.getContentAsString();
                currentPage = webClient.getPage(url);

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

    private static void getCreds()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your username?");
        user = sc.nextLine();
        System.out.println("What is your password?");
        pass = sc.nextLine();
    }

    private static WebClient login(WebClient webClient) throws Exception
    {
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        HtmlPage currentPage = webClient.getPage("https://leren.saxion.nl/webapps/bb-auth-provider-shibboleth-bb_bb60/execute/shibbolethLogin"); //Load page at the STRING address.

        HtmlForm htmlForm = currentPage.getFirstByXPath("//*[@id=\"loginForm\"]");
        htmlForm.getInputByName("UserName").setValueAttribute(user);
        htmlForm.getInputByName("Password").setValueAttribute(pass);
        HtmlSpan htmlButton = htmlForm.getFirstByXPath("//*[@id=\"submitButton\"]");
        currentPage = (HtmlPage) htmlButton.click();
        webClient.waitForBackgroundJavaScript(10000);

        return webClient;
    }
}
