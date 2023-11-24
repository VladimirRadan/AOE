package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By careersNavLink = By.xpath("//a[@data-qa='nav_main_5']/..");
    private final By vacanciesLink = By.xpath("//a[@data-qa='nav_main_5_4']");
    private final By acceptCookies = By.id("onetrust-accept-btn-handler");

    public HomePage(WebDriver driver) {
        super(driver);
    }


    public VacanciesPage goToVacanciesSection() {
        clickWhenReady(acceptCookies)
                .dropDown(careersNavLink, vacanciesLink)
                .clickWhenReady(vacanciesLink);
        return new VacanciesPage(driver);
    }


}//end class
