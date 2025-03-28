package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.BeforeClass;


public class BaseTest {
    @BeforeClass
    public void init() {
        Configuration.baseUrl = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager";
        Configuration.browser = "firefox";
        Configuration.timeout = 5000;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
    }
}
