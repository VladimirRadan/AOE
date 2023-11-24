package utils;


import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.log.Log;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {


    private static final Logger logger = LogManager.getLogger(Utils.class);


    /**
     * Reads .env file, the main property file for setting project options
     * The file is located in project root
     */
    public static Dotenv dotEnv() {
        return Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
    }


    /**
     * Logs all console errors for easier debugging
     *
     * @param driver
     */
    public static void consoleLogError(WebDriver driver) {
        DevTools devTools = null;
        if (driver instanceof ChromeDriver) {
            devTools = ((ChromeDriver) driver).getDevTools();
        } else if (driver instanceof FirefoxDriver) {
            devTools = ((FirefoxDriver) driver).getDevTools();
        } else if (driver instanceof EdgeDriver) {
            devTools = ((EdgeDriver) driver).getDevTools();
        }
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(), logEntry -> {
            if (logEntry.getLevel().toString().equalsIgnoreCase("error")) {
                logger.error("CONSOLE ERROR MESSAGE: " + logEntry.getText() + "\n" + "Request URL: " + logEntry.getUrl());
            }
        });
    }


}//end class
