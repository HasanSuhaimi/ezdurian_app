
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
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class testClass {

    public static void main(String[] args) throws Exception {
        System.out.println("test");

        testClass test = new testClass();
        List<data> data = test.SeleniumPulldata();
        //test.SeleniumSendMsg(data);

    }

    public void SeleniumSendMsg(List<data> data) throws Exception {

        ChromeDriver driver = new ChromeDriver();
        driver.get("https://web.whatsapp.com/");
        Thread.sleep(10000);  // Let the user actually see something!
        //get search button
        WebElement boxSearch = driver.findElement(By.xpath("//button[@class=\"_3e4VU\"]"));
        //click search button
        boxSearch.click();

        //search contact
        String name = "" ;
        WebElement searchContact = driver.findElement(By.xpath("//div[@class=\"_3FRCZ copyable-text selectable-text\"]"));
        searchContact.sendKeys(name);
        Thread.sleep(3000);

        //open chat with the contact
        WebElement targetContact = driver.findElement(By.xpath("//span[@title="+ "\"" + name + "\"" +"]"));
        targetContact.click();

        //get textBox and send message
        WebElement textBox = driver.findElement(By.xpath("//div[@class=\"_3FRCZ copyable-text selectable-text\"][@contenteditable=\"true\"][@data-tab=\"1\"]"));

        //textBox.sendKeys("");

        for(int i = 0; i <data.size() ; i++)
        {
            Actions action = new Actions(driver);
            Action texting = data.get(i).getTextAction(action,textBox);
            texting.perform();
        }

        Thread.sleep(5000);
        driver.quit();
    }

    //print return a list of data for invoice NP
    public List<data> SeleniumPulldata() throws Exception {

        //set the browser in the background
        //ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);

        //make sure to pass options as the parameter
        ChromeDriver driver = new ChromeDriver();

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
                //System.out.println(column_row.get(12).getText() + "\n");
                Data.setIndex(column_row.get(0).getText());
                Data.setInvoice(column_row.get(1).getText());
                Data.setName(column_row.get(2).getText());
                //skip email
                Data.setNumber(column_row.get(4).getText());
                Data.setAdress(column_row.get(5).getText());
                Data.setField1(column_row.get(6).getText());
                Data.setField2(column_row.get(7).getText());
                Data.setProduct(column_row.get(8).getText());
                Data.setConfirmed_at(column_row.get(11).getText());
                Data.setStatus(column_row.get(12).getText());

                datas.add(Data);
            }

        }

        //driver.quit();

        for(int x = 0 ; x < datas.size(); x++){
            System.out.println("####\n"+datas.get(x).getConfirmed_at()+" : "+datas.get(x).getStatus()+"\n####\n" + datas.get(x).getTextMessage() + "\n");
        }

        //return list of data
        return datas;
    }

    public static class data {

        private String index;
        private String invoice;
        private String name;
        private String number;
        private String adress;
        private String field1;
        private String field2;
        private String product;
        private String confirmed_at;
        private String status;

        public void setIndex (String value) {this.index = value;}
        public String getIndex () { return index; }

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

        public String getTextMessage () {

            String textMessage="Whatsapp / SMS merchant\n" +
                    "delivery request form\n" +
                    "1. Sama-Sama Lokal by Maybank\n" +
                    "2. Restaurant / Gerai : ezydurian\n\n" +
                    "Order:\n" +
                    "1. Recipient name: "+"NP-"+index +" "+ name + "\n" +
                    "2. Recipient contact number: "+ number + "\n" +
                    "3. Delivery address: "+ adress + "\n" +
                    "4. Pick up time: now \n\n" +
                    "Thanks team Maybank  :)";

            return textMessage;
        }

        public Action getTextAction (Actions action, WebElement textBox) {

            //create a series of action
            Action texting = action.moveToElement(textBox).sendKeys("Whatsapp / SMS merchant").keyDown(Keys.SHIFT).sendKeys("\n").
                    keyUp(Keys.SHIFT).sendKeys("delivery request form").keyDown(Keys.SHIFT).sendKeys("\n").
                    keyUp(Keys.SHIFT).sendKeys("1. Sama-Sama Lokal by Maybank").keyDown(Keys.SHIFT).sendKeys("\n").
                    keyUp(Keys.SHIFT).sendKeys("2. Restaurant / Gerai : ezydurian").keyDown(Keys.SHIFT).sendKeys("\n\n").
                    keyUp(Keys.SHIFT).sendKeys("Order:").keyDown(Keys.SHIFT).sendKeys("\n").
                    keyUp(Keys.SHIFT).sendKeys("1. Recipient name: "+"NP-"+index +" "+ name).keyDown(Keys.SHIFT).sendKeys("\n").
                    keyUp(Keys.SHIFT).sendKeys("2. Recipient contact number: "+ number).keyDown(Keys.SHIFT).sendKeys("\n").
                    keyUp(Keys.SHIFT).sendKeys("3. Delivery address: "+ adress).keyDown(Keys.SHIFT).sendKeys("\n").
                    keyUp(Keys.SHIFT).sendKeys("4. Pick up time: now ").keyDown(Keys.SHIFT).sendKeys("\n\n").
                    keyUp(Keys.SHIFT).sendKeys("Thanks team Maybank :)\n")
                    .build();

            return texting;

        }

    }
}

