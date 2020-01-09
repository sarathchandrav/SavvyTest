import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class CallenderTest {
    private WebDriver webDriver;

    @BeforeTest
    public void openSite()  {
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://savvytime.com");
        try{
        Thread.sleep(3000);}
        catch(InterruptedException e){
            System.out.println("Error");
        }

    }

    @Test
    public void savvySearch() throws InterruptedException {

        webDriver.findElement(By.id("place-search")).sendKeys("India");
        //webDriver.findElement(By.xpath("//div[@id='home-quick-search-result']//a[@href='/local/india-mumbai']")).click();
        Thread.sleep(3000);
    }

    @AfterClass
    public void Done() {
        webDriver.quit();
    }
}
