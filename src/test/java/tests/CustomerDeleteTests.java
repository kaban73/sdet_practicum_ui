package tests;

import com.codeborne.selenide.Configuration;
import dataproviders.CustomerDataProviders;
import elements.CustomersSubPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.CustomerData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;

import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Epic("Операции с кастомерами")
@Feature("Удаление кастомеров")
public class CustomerDeleteTests extends BaseTest {
    @BeforeMethod
    void initBeforeMethod() {
        open(Configuration.baseUrl);
    }

    @Test
    @Description("Нахождение имени, подходящего по условию, и удаление кастомера с этим именем, проверка на удаление этого кастомера")
    @Story("Удаление кастомера из начального списка кастомеров")
    public void delete_in_initial_customers() {
        prepareDefaultCustomer();

        CustomersSubPage customersSubPage = page(new CustomersSubPage());

        List<String> customersFirstNamesList = customersSubPage.getCustomersFirstNames();

        String deleteFirstName = findNameClosestToAverage(customersFirstNamesList);

        customersSubPage
                .deleteCustomerForFirstName(deleteFirstName)
                .searchCustomerForFirstName(deleteFirstName)
                .checkCustomerNotExistsForFirstName(deleteFirstName);
    }

    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class
    )
    @Description("Добавление нового кастомера, нахождение имени, подходящего по условию, и удаление кастомера с этим именем, проверка на удаление этого кастомера")
    @Story("Удаление кастомера из нового списка кастомеров")
    public void delete_in_new_customers(CustomerData customer) {
        prepareNewCustomer(customer);

        CustomersSubPage customersSubPage = page(new CustomersSubPage());

        List<String> customersFirstNamesList = customersSubPage.getCustomersFirstNames();

        String deleteFirstName = findNameClosestToAverage(customersFirstNamesList);

        customersSubPage
                .deleteCustomerForFirstName(deleteFirstName)
                .searchCustomerForFirstName(deleteFirstName)
                .checkCustomerNotExistsForFirstName(deleteFirstName);
    }

    private void prepareDefaultCustomer() {
        page(new ManagerPage())
                .checkManagerPage()
                .clickShowCustomerButton()
                .checkCustomersTable();
    }

    private void prepareNewCustomer(CustomerData customer) {
        page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .addNewCustomer(customer);
        prepareDefaultCustomer();
    }

    private String findNameClosestToAverage(List<String> names) {
        double average = names.stream()
                .mapToInt(String::length)
                .average()
                .orElse(0);

        return names.stream()
                .min(Comparator.comparingDouble(
                        name -> Math.abs(name.length() - average)
                ))
                .orElseThrow();
    }

    @AfterMethod
    public void cleanupAfterEachTest() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }
}
