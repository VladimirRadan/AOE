package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class VacanciesPage extends BasePage {

    private final By filterByKeywordField = By.id("filter-keyword");
    private final By filteringResultsList = By.xpath("//tr[contains(@class,'filter-list-item') and not(contains(@style, 'none'))]/td/a");
    private final By jobOfferingTexts = By.cssSelector(".ce-bodytext");


    public VacanciesPage(WebDriver driver) {
        super(driver);
    }


    public void filterByKeyword(String input) {
        typeIn(filterByKeywordField, input);
        getElementTest(By.cssSelector("body")).sendKeys(Keys.ENTER);
    }


    public List<String> getTextsOfFilteringResults() {
        return getElementList(filteringResultsList)
                .stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> listOfJobOfferingContent() {
        scrollDown(0, 300);
        clickWhenReady(filteringResultsList);
        return getElementList(jobOfferingTexts).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }


}//end class
