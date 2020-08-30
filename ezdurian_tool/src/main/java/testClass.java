
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.WebClient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//webdriver class
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.chrome.ChromeDriver;


public class testClass {

    public static void main(String[] args) throws Exception {
        System.out.println("test");

        testClass test = new testClass();
        //test.SeleniumPulldata();
        test.SeleniumSendMsg();
        // create webclient
        /*final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true); //ignore ssl certificate
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        testClass test = new testClass();
        HtmlPage loginPage = test.loginForm(webClient);
        test.reportPage(webClient);
        */
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
            webClient.waitForBackgroundJavaScriptStartingBefore(2000);
            webClient.waitForBackgroundJavaScript(20000);

            // Get the form that we are dealing with and within that form,
            // find the submit button and the field that we want to change.
            final List<HtmlForm> formList = page1.getForms();
            final HtmlForm form = formList.get(0);
            System.out.println("start the form: "+ form+" HABIS");

            form.getInputByName("username").setValueAttribute("");
            form.getInputByName("password").setValueAttribute("");

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

                form.getInputByName("c[invoice_number]").setAttribute("checked", "checked");
                form.getInputByName("c[client_phone_number]").setAttribute("checked", "checked");
                form.getInputByName("c[client_address]").setAttribute("checked", "checked");
                form.getInputByName("c[extra_field_1]").setAttribute("checked", "checked");
                form.getInputByName("c[extra_field_2]").setAttribute("checked", "checked");
                form.getInputByName("c[products]").setAttribute("checked", "checked");
                form.getInputByName("c[confirmed_at]").setAttribute("checked", "checked");
                form.getInputByName("c[status]").setAttribute("checked", "checked");

            }

            //generated button
            final HtmlButton button = page.getHtmlElementById("show");

            //click button
            HtmlPage pageAfterClick = button.click();
            //System.out.println(pageAfterClick.asText());

            //get the table
            final HtmlTable dataTable = (HtmlTable) pageAfterClick.getByXPath("//table[@class='table table-hover table-bordered table-condensed']").get(0);

            for (final HtmlTableRow row : dataTable.getRows()) {

                if(row.getCell(1).asText().contains("NP"))
                {
                    for (final HtmlTableCell cell : row.getCells()) {
                        System.out.println("   Found cell: " + cell.asText());
                    }

                    String textMessage="Whatsapp / SMS merchant\ndelivery request form\n1. Sama-Sama Lokal by Maybank\n2. Restaurant / Gerai : ezydurian\n\nOrder:\n1. Recipient name: NP\n2. Recipient contact number: \n3. Delivery address:\n4. Pick up time: now \n\nThanks team Maybank  😊";

                    System.out.println(textMessage);
                }

            }

        }catch (Exception e) {

            System.out.println("Errors: "+e);
        }

    }

    public void SeleniumSendMsg() throws Exception {

        ChromeDriver driver = new ChromeDriver();
        driver.get("https://web.whatsapp.com/");
        Thread.sleep(7000);  // Let the user actually see something!
        //get search button
        WebElement boxSearch = driver.findElement(By.xpath("//button[@class=\"_3e4VU\"]"));
        //click search button
        boxSearch.click();
        //search contact
        String name = "KC Sheffield" ;
        WebElement searchContact = driver.findElement(By.xpath("//div[@class=\"_3FRCZ copyable-text selectable-text\"]"));
        searchContact.sendKeys(name);
        //open chat with the contact
        WebElement targetContact = driver.findElement(By.xpath("//span[@title="+ "\"" + name + "\"" +"]"));
        targetContact.click();

        //get textBox and send message
        WebElement textBox = driver.findElement(By.xpath("//div[@class=\"_3FRCZ copyable-text selectable-text\"][@contenteditable=\"true\"][@data-tab=\"1\"]"));

        for(int i = 0; i <=20 ; i++){
        textBox.sendKeys("Testing");
        textBox.sendKeys(Keys.ENTER);
        }
        textBox.sendKeys("Settle");
        textBox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        //boxSearch.click();
        //Thread.sleep(100);
        //boxSearch.sendKeys("KC");

        //System.out.println(boxSearch.isDisplayed());

        driver.quit();
    }

    public void SeleniumPulldata() throws Exception {

        //set the browser in the background
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        //make sure to pass options as the parameter
        ChromeDriver driver = new ChromeDriver(options);

        driver.get("https://ezydurian.onpay.my/admin/login");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));

        WebElement loginButton = driver.findElement(By.name("login"));

        username.sendKeys("");
        password.sendKeys("");

        loginButton.click();

        driver.get("https://ezydurian.onpay.my/admin/reports/sales");

        driver.findElement(By.name("c[invoice_number]")).click();
        driver.findElement(By.name("c[client_phone_number]")).click();
        driver.findElement(By.name("c[client_address]")).click();
        driver.findElement(By.name("c[extra_field_1]")).click();
        driver.findElement(By.name("c[extra_field_2]")).click();
        driver.findElement(By.name("c[products]")).click();
        driver.findElement(By.name("c[confirmed_at]")).click();
        driver.findElement(By.name("c[status]")).click();

        WebElement reportButton = driver.findElement(By.id("show"));

        reportButton.sendKeys(Keys.ENTER);

        // get the whole table
        WebElement reportTable = driver.findElement(By.xpath("//table/tbody"));

        // make a list of all rows (/tr) in the table
        List <WebElement> rows_table = reportTable.findElements(By.tagName("tr"));

        int table_size = rows_table.size();

        // initiate a list of data
        List <data> datas = new ArrayList<data>();

        for(int x =0 ; x < table_size; x++)
        {
            //for each row, get all the columns
            List <WebElement> column_row = rows_table.get(x).findElements(By.tagName("td"));

            //if No.Invois for NP, initiate data class, store the info and add into the list of data (datas)
            if(column_row.get(1).getText().contains("NP"))
            {
                data Data = new data();
                //System.out.println(rows_table.get(x).getText() + "\n");
                Data.setInvoice(column_row.get(1).getText());
                Data.setName(column_row.get(2).getText());
                //skip email
                Data.setNumber(column_row.get(4).getText());
                Data.setAdress(column_row.get(5).getText());
                Data.setField1(column_row.get(6).getText());
                Data.setField2(column_row.get(7).getText());
                Data.setProduct(column_row.get(8).getText());
                Data.setConfirmed_at(column_row.get(9).getText());
                Data.setStatus(column_row.get(10).getText());

                datas.add(Data);
            }

        }

        System.out.println(datas.get(1).getAdress());

        //Thread.sleep(5000);

        driver.quit();

    }

    public static class data {

        private String invoice;
        private String name;
        private String number;
        private String adress;
        private String field1;
        private String field2;
        private String product;
        private String confirmed_at;
        private String status;

        public void setInvoice (String value) {this.invoice = value;}
        public String getInvoice () { return invoice; }

        public void setName (String value) {this.name = value;}
        public String getName () { return name; }

        public void setNumber (String value) {this.number = value;}
        public String getNumber () { return number; }

        public void setAdress (String value) {this.adress = value;}
        public String getAdress () { return adress; }

        public void setField1 (String value) {this.field1 = value;}
        public String getField1 () { return field1; }

        public void setField2 (String value) {this.field2 = value;}
        public String getField2 () { return field2; }

        public void setProduct (String value) {this.product = value;}
        public String getProduct () { return product; }

        public void setConfirmed_at (String value) {this.confirmed_at = value;}
        public String getConfirmed_at () { return confirmed_at; }

        public void setStatus (String value) {this.status = value;}
        public String getStatus () { return status; }

    }
}

