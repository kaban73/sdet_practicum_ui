package tests;

import dataproviders.CustomerDataProviders;
import elements.CustomersSubPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.CustomerData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;

import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Epic("Операции с кастомерами")
@Story("Удаление кастомеров")
public class CustomerDeleteTests extends BaseTest {
    @BeforeMethod
    void initBeforeMethod() {
        managerPage = page(ManagerPage.class);
        customersSubPage = page(CustomersSubPage.class);
        managerPage.checkManagerPage();
    }

    @Test
    @Description("Нахождение имени, подходящего по условию, и удаление кастомера с этим именем, проверка на удаление этого кастомера")
    @Feature("Удаление кастомера из начального списка кастомеров")
    public void testDeleteCustomerFromInitialList() {
        managerPage.prepareDefaultCustomer();

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
    @Feature("Удаление кастомера из нового списка кастомеров")
    public void testDeleteCustomerFromNewList(CustomerData customer) {
        managerPage.prepareNewCustomer(customer);

        List<String> customersFirstNamesList = customersSubPage.getCustomersFirstNames();

        String nameForDelete = findNameClosestToAverage(customersFirstNamesList);

        customersSubPage
                .deleteCustomerForFirstName(nameForDelete)
                .searchCustomerForFirstName(nameForDelete)
                .checkCustomerNotExistsForFirstName(nameForDelete);
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
}
