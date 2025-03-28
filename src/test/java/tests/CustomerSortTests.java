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
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

@Epic("Операции с кастомерами")
@Feature("Сортировка кастомеров")
public class CustomerSortTests extends BaseTest {
    private ManagerPage managerPage;
    private CustomersSubPage customersSubPage;
    @BeforeMethod
    void initBeforeMethod() {
        managerPage = page(ManagerPage.class);
        customersSubPage = page(CustomersSubPage.class);
        managerPage.checkManagerPage();
    }
    @Test
    @Description("Проверка сортировки дефолтных кастомеров по убыванию")
    @Story("Сортировка списка кастомеров по убыванию")
    public void customers_list_descending_sort_by_firstName() {
        prepareDefaultCustomer();

        List<String> customersDescendingList =
                customersSubPage
                .clickTheadFirstName()
                .getCustomersFirstNames();
        assertTrue(isSortedDescending(customersDescendingList));
    }

    @Test
    @Description("Проверка сортировки дефолтных кастомеров по возрастанию")
    @Story("Сортировка списка кастомеров по возрастанию")
    public void customers_list_ascending_sort_by_firstName() {
        prepareDefaultCustomer();

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
    @Story("Сортировка нового списка кастомеров по убыванию")
    public void customers_list_descending_sort_by_firstName_with_new_customers(CustomerData customer) {
        prepareNewCustomer(customer);

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
    @Story("Сортировка нового списка кастомеров по возрастанию")
    public void customers_list_ascending_sort_by_firstName_with_new_customers(CustomerData customer) {
        prepareNewCustomer(customer);

        List<String> customersDescendingList =
                customersSubPage
                        .clickTheadFirstName()
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertFalse(isSortedDescending(customersDescendingList));
    }

    private void prepareDefaultCustomer() {
        managerPage
                .checkManagerPage()
                .clickShowCustomerButton()
                .checkCustomersTable();
    }

    private void prepareNewCustomer(CustomerData customer) {
        managerPage
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
}
