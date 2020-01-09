import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SavvyTimeTest {
    private WebDriver webDriver;
    List<WebElement> MainList;

    @BeforeTest
    public void openSite() throws InterruptedException {
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://savvytime.com");
        Thread.sleep(3000);
    }

    @Test
    public void savvySearch() throws InterruptedException {

        webDriver.findElement(By.id("place-search")).sendKeys("India");
        //webDriver.findElement(By.xpath("//div[@id='home-quick-search-result']//a[@href='/local/india-mumbai']")).click();
        Thread.sleep(3000);
    }

    @Test(description = "TestTime Converter", dependsOnMethods = {"savvySearch"})
    public void Converter() {
        MainList = webDriver.findElements(By.xpath("//ul[@class='nav navbar-nav']//li"));
        MainList.get(1).click();

        webDriver.findElement(By.id("time-search")).sendKeys("Hyderabad");
        webDriver.findElement(By.xpath("//a[@data-id='india-hyderabad']")).click();
        webDriver.findElement(By.id("time-search")).sendKeys("Japan");
        List<WebElement> JapanList = webDriver.findElements(By.className("list-group-item"));
        JapanList.get(0).click();

        WebElement Area = webDriver.findElement(By.xpath("//a[@href='/local/india-hyderabad']"));
        Assert.assertEquals(Area.getText(), "Hyderabad, India");
        WebElement Area2 = webDriver.findElement(By.xpath("//span[text()='JST']"));
        Assert.assertEquals(Area2.getText(), "JST");
    }

    @Test(description = "Test DateChanger", dependsOnMethods = {"Converter"})
    public void DateChange() throws InterruptedException {
        webDriver.findElement(By.xpath("//span[@class='input-group-addon']")).click();
        webDriver.findElement(By.xpath("//html/body/div[2]/div[1]/table/thead/tr[1]/th[2]")).click();
        webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/table/thead/tr/th[3]")).click();
        webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/table/tbody/tr/td/span[6]")).click();
        webDriver.findElement(By.xpath("/html/body/div[2]/div[1]/table/tbody/tr[4]/td[5]")).click();

        List<WebElement> Date = webDriver.findElements(By.xpath("//div[@class='tz-date']"));
        Assert.assertEquals(Date.get(0).getText(), "Thu, Jun 24");

        Thread.sleep(3000);
    }

    @Test(description = "Test SwapLocation", dependsOnMethods = {"DateChange"})
    public void Swap() throws InterruptedException {
        webDriver.findElement(By.xpath("//a[@class='swap-tz btn']")).click();
        Thread.sleep(4000);
        List<WebElement> SwapData = webDriver.findElements(By.xpath("//div[@class='td-name col-xs-6']"));

        Assert.assertEquals(SwapData.get(0).getText(), "JST");
        Assert.assertEquals(SwapData.get(2).getText(), "Hyderabad, India");
    }

    @Test(description = "Test DeleteLocation", dependsOnMethods = {"Swap"})
    public void DeleteLocation() throws InterruptedException {
        Thread.sleep(4000);
        List<WebElement> SelectLocation = webDriver.findElements(By.xpath("//div[@class='table-time row']"));
        SelectLocation.get(0).click();
        webDriver.findElement(By.xpath("//a[@class='delete-btn btn']")).click();
    }


    @AfterClass
    public void Done() {
        webDriver.quit();
    }


}