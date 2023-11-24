package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static core.Constants.DEV_URL;
import static utils.Utils.dotEnv;


public class Environment {

    private WebDriver driver;

    private String homeUrl;
    private static final Logger logger = LogManager.getLogger(Environment.class.getName());


    public Environment(WebDriver driver) {
        this.driver = driver;
    }


    /**
     * Opening URL set in the .env property file
     */
    public void openBrowser() {
        if (dotEnv().get("ENV", "dev").equalsIgnoreCase("dev")) {
            homeUrl = DEV_URL;
        }
        logger.info("Opening browser [ "+ dotEnv().get("BROWSER")+ " ] and navigating to: " + homeUrl);
        driver.get(homeUrl);
    }



}//end class
