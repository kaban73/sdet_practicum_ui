package tests;

import static com.codeborne.selenide.Selenide.*;
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
import java.util.List;
import java.util.stream.IntStream;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

@Epic("Операции с кастомерами")
@Story("Сортировка кастомеров")
public class CustomerSortTests extends BaseTest {
    @BeforeMethod
    void initBeforeMethod() {
        managerPage = page(ManagerPage.class);
        customersSubPage = page(CustomersSubPage.class);
        managerPage.checkManagerPage();
    }
    @Test
    @Description("Проверка сортировки дефолтных кастомеров по убыванию")
    @Feature("Сортировка списка кастомеров по убыванию")
    public void testDefaultCustomersSortedDescending() {
        managerPage.prepareDefaultCustomer();

        List<String> customersDescendingList =
                customersSubPage
                .clickTheadFirstName()
                .getCustomersFirstNames();
        assertTrue(isSortedDescending(customersDescendingList));
    }

    @Test
    @Description("Проверка сортировки дефолтных кастомеров по возрастанию")
    @Feature("Сортировка списка кастомеров по возрастанию")
    public void testDefaultCustomersSortedAscending() {
        managerPage.prepareDefaultCustomer();

        List<String> customersDescendingList =
                customersSubPage
                        .clickTheadFirstName()
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertFalse(isSortedDescending(customersDescendingList));
    }

    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class
    )
    @Description("Добавление нового кастомера и проверка сортировки кастомеров по убыванию")
    @Feature("Сортировка нового списка кастомеров по убыванию")
    public void testNewCustomersSortedDescending(CustomerData customer) {
        managerPage.prepareNewCustomer(customer);

        List<String> customersDescendingList =
                customersSubPage
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertTrue(isSortedDescending(customersDescendingList));
    }

    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class
    )
    @Description("Добавление нового кастомера и проверка сортировки кастомеров по возрастанию")
    @Feature("Сортировка нового списка кастомеров по возрастанию")
    public void testNewCustomersSortedAscending(CustomerData customer) {
        managerPage.prepareNewCustomer(customer);

        List<String> customersDescendingList =
                customersSubPage
                        .clickTheadFirstName()
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertFalse(isSortedDescending(customersDescendingList));
    }

    private boolean isSortedDescending(List<String> list) {
        return IntStream.range(0, list.size() - 1)
                .allMatch(i -> list.get(i).toLowerCase()
                        .compareTo(list.get(i + 1).toLowerCase()) >= 0);
    }
}
