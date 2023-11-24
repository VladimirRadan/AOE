package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static utils.Utils.dotEnv;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DriverSetup {

    private String browser = dotEnv().get("BROWSER", "chrome");

    private static final DriverSetup instance = new DriverSetup();

    public static DriverSetup getInstance(){
        return instance;
    }

    private final Map<String, Supplier<WebDriver>> driverMap = new HashMap<>();


    private final Supplier<WebDriver> chrome = () -> WebDriverManager.chromedriver().create();

    private final Supplier<WebDriver> firefox = () -> WebDriverManager.firefoxdriver().create();

    private final Supplier<WebDriver> edge = () -> WebDriverManager.edgedriver().create();

    {
        driverMap.put("chrome", chrome);
        driverMap.put("firefox", firefox);
        driverMap.put("edge", edge);
    }

    public synchronized WebDriver getDriver(){
        return driverMap.get(browser).get();
    }










}//end class
