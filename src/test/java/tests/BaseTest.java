package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import elements.CustomerAddSubPage;
import elements.CustomersSubPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.ManagerPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;


public class BaseTest {

    protected ManagerPage managerPage;
    protected CustomerAddSubPage customerAddSubPage;
    protected CustomersSubPage customersSubPage;
    @BeforeClass
    public void init() throws IOException {
        Properties properties = new Properties();
        try (
                InputStream input = getClass()
                        .getClassLoader()
                        .getResourceAsStream("config.properties")
                ) {
            properties.load(input);
        }

        Configuration.baseUrl = properties.getProperty("base.url");
        Configuration.browser = properties.getProperty("browser");
        Configuration.timeout = Integer.parseInt(properties.getProperty("timeout"));
        Configuration.headless = Boolean.parseBoolean(properties.getProperty("headless"));
        Configuration.reopenBrowserOnFail = Boolean.parseBoolean(properties.getProperty("reopenBrowserOnFail"));

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
    }

    @BeforeMethod
    public void setUp() {
        open(Configuration.baseUrl);
    }
    @AfterMethod
    public void tearDown() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        closeWebDriver();
    }
}
