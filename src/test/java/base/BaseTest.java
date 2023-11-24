package base;

import core.Environment;
import driver.DriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.Optional;

import static utils.Utils.consoleLogError;

public class BaseTest {

    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        driver.set(DriverSetup.getInstance().getDriver());
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get().manage().window().maximize();
        new Environment(driver.get()).openBrowser();
        consoleLogError(driver.get());
    }

    protected void closeDriver(){
        Optional.ofNullable(driver.get()).ifPresent(WebDriver::quit);
        driver.remove();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        closeDriver();
    }

}//end class
