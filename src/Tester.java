import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.util.Scanner;
import java.util.logging.Level;

public class Tester {
        public static void main(String[] args) throws Exception {
            /*WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED); //Initiate a WebClient variable.
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient = login(webClient);
            HtmlPage currentPage = webClient.getPage("https://roosters.saxion.nl/schedule/week:0/group:EHI1VSc");
            webClient.waitForBackgroundJavaScript(10000);
            WebResponse response = currentPage.getWebResponse();
            currentPage = webClient.getPage("https://engine.surfconext.nl:443/authentication/sp/consume-assertion");
            webClient.waitForBackgroundJavaScript(10000);
            String content = response.getContentAsString();
            currentPage = webClient.getPage("https://roosters.saxion.nl/schedule/week:0/group:EHI1VSc");

            PrintWriter out = new PrintWriter("rooster.xml");
            try
            {
                out.println(currentPage.asXml());
            }
            finally
            {
                out.close();
            }*/

            CalendarParse calendarParse = new CalendarParse();
            calendarParse.parse();


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

        String user;
        String pass;
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your username?");
        user = sc.nextLine();
        System.out.println("What is your password?");
        pass = sc.nextLine();

        HtmlForm htmlForm = currentPage.getFirstByXPath("//*[@id=\"loginForm\"]");
        htmlForm.getInputByName("UserName").setValueAttribute(user);
        htmlForm.getInputByName("Password").setValueAttribute(pass);
        HtmlSpan htmlButton = htmlForm.getFirstByXPath("//*[@id=\"submitButton\"]");
        currentPage = (HtmlPage) htmlButton.click();
        webClient.waitForBackgroundJavaScript(10000);

        return webClient;
    }
}
