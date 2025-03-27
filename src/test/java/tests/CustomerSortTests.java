package tests;

import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.*;
import dataproviders.CustomerDataProviders;
import elements.CustomersSubPage;
import model.CustomerData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;
import java.util.List;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class CustomerSortTests extends BaseTest {
    @BeforeMethod
    void initBeforeMethod() {
        open(Configuration.baseUrl);
    }
    @Test(
            description = "Проверка сортировки дефолтных кастомеров по убыванию"
    )
    public void customers_list_descending_sort_by_firstName() {
        prepareDefaultCustomer();

        List<String> customersDescendingList =
                page(new CustomersSubPage())
                .clickTheadFirstName()
                .getCustomersFirstNames();
        assertTrue(isSortedDescending(customersDescendingList));
    }

    @Test(
            description = "Проверка сортировки дефолтных кастомеров по возрастанию"
    )
    public void customers_list_ascending_sort_by_firstName() {
        prepareDefaultCustomer();

        List<String> customersDescendingList =
                page(new CustomersSubPage())
                        .clickTheadFirstName()
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertFalse(isSortedDescending(customersDescendingList));
    }

    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class,
            description = "Добавление нового кастомера и проверка сортировки кастомеров по убыванию"
    )
    public void customers_list_descending_sort_by_firstName_with_new_customers(CustomerData customer) {
        prepareNewCustomer(customer);

        List<String> customersDescendingList =
                page(new CustomersSubPage())
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertTrue(isSortedDescending(customersDescendingList));
    }

    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class,
            description = "Добавление нового кастомера и проверка сортировки кастомеров по возрастанию"
    )
    public void customers_list_ascending_sort_by_firstName_with_new_customers(CustomerData customer) {
        prepareNewCustomer(customer);

        List<String> customersDescendingList =
                page(new CustomersSubPage())
                        .clickTheadFirstName()
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertFalse(isSortedDescending(customersDescendingList));
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

    private boolean isSortedDescending(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).toLowerCase().compareTo(list.get(i + 1).toLowerCase()) < 0) {
                return false;
            }
        }
        return true;
    }

    @AfterMethod
    public void cleanupAfterEachTest() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }
}
