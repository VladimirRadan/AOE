package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.VacanciesPage;

import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Listeners(TestListener.class)
public class VacanciesTest extends BaseTest {

    /**
     * TC #01: {@link #filterTest}
     */

    VacanciesPage vacancies;
    HomePage homePage;

    @BeforeMethod
    public void initialize() {
        homePage = new HomePage(driver.get());
        vacancies = new VacanciesPage(driver.get());
    }

    @Test(description = "Filtering results by keyword. Case: Happy path. Expected: Results contain keyword, job offering content is displayed.")
    public void filterTest() {
        homePage.goToVacanciesSection()
                .filterByKeyword("frontend");

        assertThat(vacancies.getTextsOfFilteringResults().stream().allMatch(x -> x.toLowerCase().contains("frontend"))).isTrue();
        assertThat(vacancies.listOfJobOfferingContent().stream().allMatch(Predicate.not(String::isBlank))).isTrue();
    }

  


}
