import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.WebClient;

import java.net.URLEncoder;
import java.util.List;


public class testClass {

    public static void main(String[] args) throws Exception {
        System.out.println("test");

        // create webclient
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true); //ignore ssl certificate
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        testClass test = new testClass();
        HtmlPage loginPage = test.loginForm(webClient);
        test.reportPage(webClient);

    }

    public void homePage() throws Exception {

        try{
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setUseInsecureSSL(true); //ignore ssl certificate
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

            final HtmlPage page = webClient.getPage("https://ezydurian.onpay.my/admin/login");

            webClient.waitForBackgroundJavaScriptStartingBefore(200);
            webClient.waitForBackgroundJavaScript(20000);

            final String pageAsXml = page.asXml();
            final String pageAsText = page.asText();

            System.out.println(pageAsXml);

        }
        catch (Exception e){ System.out.println("errors: "+e);}
    }

    public HtmlPage loginForm(WebClient webClient) throws Exception {

        try {
            // Get the first page
            final HtmlPage page1 = webClient.getPage("https://ezydurian.onpay.my/admin/login");
            webClient.waitForBackgroundJavaScriptStartingBefore(200);
            webClient.waitForBackgroundJavaScript(20000);

            // Get the form that we are dealing with and within that form,
            // find the submit button and the field that we want to change.
            final List<HtmlForm> formList = page1.getForms();
            final HtmlForm form = formList.get(0);
            System.out.println("start the form: "+ form+" HABIS");

            

            //click login button
            HtmlPage pageAfterClick = (HtmlPage)form.getButtonByName("login").click();

            //System.out.println(form.getOnSubmitAttribute());
            //System.out.println(pageAfterClick.asXml());

            //return next page
            return pageAfterClick;

        }
        catch (Exception e)
        {
            System.out.println("errors: "+e);
            return null;
        }


    }

    public void reportPage(WebClient webClient) throws Exception {

        try {
            final HtmlPage page = webClient.getPage("https://ezydurian.onpay.my/admin/reports/sales");
            webClient.waitForBackgroundJavaScriptStartingBefore(200);
            webClient.waitForBackgroundJavaScript(20000);

            //get the form to check/uncheck boxes
            final List<HtmlForm> formList = page.getForms();

            for (HtmlForm form : formList)
            {
                System.out.println("start forms: "+form);

                //uncheck/check the wanted value
                //System.out.println(form.getInputByName("c[form_code]"));

                form.getInputByName("c[form_code]").setAttribute("checked", "checked");

            }

            //generated button
            final HtmlButton button = page.getHtmlElementById("show");

            //click button
            HtmlPage pageAfterClick = button.click();
            //System.out.println(pageAfterClick.asText());

            //get the table
            final HtmlTable dataTable = (HtmlTable) pageAfterClick.getByXPath("//table[@class='table table-hover table-bordered table-condensed']").get(0);

            for (final HtmlTableRow row : dataTable.getRows()) {
                System.out.println("Found row");
                for (final HtmlTableCell cell : row.getCells()) {
                    System.out.println("   Found cell: " + cell.asText());
                }
            }

            System.out.println(dataTable);

        }catch (Exception e) {

            System.out.println("Errors: "+e);
        }


    }

}

