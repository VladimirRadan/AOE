package pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utils;

import java.awt.*;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static utils.Utils.dotEnv;

public class BasePage {

    protected WebDriver driver;
    private WebDriverWait wait;

    private static final Logger logger = LogManager.getLogger(BasePage.class.getName());


    public BasePage(WebDriver driver) {
        this.driver = driver;
        int waitDuration = Integer.parseInt(dotEnv().get("EXPLICIT_WAIT_DURATION", "10"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
    }

    /**
     * Waits and returns webelement based on the passed locator
     *
     * @param locator
     * @return
     */
    protected WebElement getElement(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element could not be found By locator [" + locator.toString() + "] in specified time.");
            e.printStackTrace();
        }
        return null;
    }

    protected WebElement getElement2(By locator) {
        WebElement element = null;
        try {
            return element = wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(locator),
                    ExpectedConditions.presenceOfAllElementsLocatedBy(locator),
                    ExpectedConditions.elementToBeClickable(locator)
            )) ? element : null;
        } catch (Exception e) {
            logger.error("Element could not be found By locator [" + locator.toString() + "] in specified time.");
            e.printStackTrace();
        }
        return null;
    }

    protected WebElement getElementTest(By locator) {
        List<WebElement> elementList = driver.findElements(locator);
        try {
            return elementList.stream()
                    .map(a -> wait.until(ExpectedConditions.elementToBeClickable(a)))
                    .map(a -> wait.until(ExpectedConditions.visibilityOf(a)))
                    //.map(a -> Arrays.asList(wait.until(ExpectedConditions.elementToBeClickable(a)),wait.until(ExpectedConditions.visibilityOf(a))))
                    .findFirst().get();
        } catch (Exception e) {
            logger.error("Element could not be found By locator [" + locator.toString() + "] in specified time.");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Waits and returns the list of webelements based on the passed locator
     *
     * @param locator
     * @return
     */
    protected List<WebElement> getElementList(By locator) {
        List<WebElement> elementList;
        try {
            elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            if (elementList != null && !elementList.isEmpty()) {
                return elementList;
            }
        } catch (Exception e) {
            logger.error("Element could not be found By locator [" + locator.toString() + "] in specified time.");
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Inputs text into the webelement
     *
     * @param locator
     * @param inputText
     * @return
     */
    protected BasePage typeIn(By locator, String inputText) {
        WebElement element = getElement2(locator);
        try {
            element.clear();
            element.sendKeys(inputText);
            logger.info("Text [" + inputText + "] HAS BEEN WRITTEN in element [" + locator + "]");
        } catch (Exception e) {
            logger.info("Element [" + locator.toString() + "] is present but not clickable in given time");
        }
        return this;
    }

    /**
     * Waits and clicks on webelement
     *
     * @param locator
     * @return
     */
    protected BasePage clickWhenReady(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (ElementClickInterceptedException ex) {
            logger.warn("WARNING - Tried to CLICK [" + locator + "] but ElementClickInterceptedException happened -> RETRY");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", getElementTest(locator));
        } catch (StaleElementReferenceException staleException) {
            logger.warn("WARNING - Tried to CLICK [" + locator + "] but StaleElementReferenceException happened -> RETRY");
            getElementTest(locator).click();
            staleException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Unable to click on " + locator.toString() + " in specified time");
        }
        return this;
    }

    /**
     * Chain hover of webelements
     *
     * @param locators
     * @return
     */
    protected BasePage dropDown(By... locators) {
        Actions actions = new Actions(driver);
        List<By> elements = new ArrayList<>(Arrays.asList(locators));
        try {
            elements.stream()
                    .map(e -> wait.until(ExpectedConditions.elementToBeClickable(e)))
                    .forEach(e -> actions.moveToElement(e).build().perform());
        } catch (Exception e) {
            elements.stream()
                    .map(d -> wait.until(ExpectedConditions.visibilityOfElementLocated(d)))
                    .forEach(d -> actions.moveToElement(d).build().perform());
        }
        return this;
    }

    /**
     * Scrolls by x or y axis
     *
     * @param x_Pixels
     * @param y_Pixels
     */
    protected void scrollDown(int x_Pixels, int y_Pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("window.scrollBy(" + x_Pixels + "," + y_Pixels + ")");
        } catch (Exception e) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            e.printStackTrace();
        }
    }


}//end class
