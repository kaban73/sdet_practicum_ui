package tests;

import com.codeborne.selenide.Configuration;
import elements.CustomersSubPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class CustomerSortTests extends BaseTest {
    /**
     *        *сортировка по имени*
     * 1. Тест на убывающую сортировку с исходными данными - Х
     * 2. Тест на возрастающую сортировку с исходными данными - Х
     * 3. Тест на убывающую сортироку с новыми данными - Х
     * 4. Тест на возрастающую сортировку с новыми данными - Х
     *
     */
    @BeforeMethod
    void openCustomerSubPage() {
        open(Configuration.baseUrl);
        page(new ManagerPage())
                .checkManagerPage()
                .clickShowCustomerButton()
                .checkCustomersTable();

        sleep(3000);
    }

    @Test
    public void customers_list_descending_sort_by_firstName() {
        List<String> customersDescendingList =
                page(new CustomersSubPage())
                .clickTheadFirstName()
                .getCustomersFirstNames();
        assertTrue(isSortedDescending(customersDescendingList));
    }

    @Test
    public void customers_list_ascending_sort_by_firstName() {
        List<String> customersDescendingList =
                page(new CustomersSubPage())
                        .clickTheadFirstName()
                        .clickTheadFirstName()
                        .getCustomersFirstNames();
        assertFalse(isSortedDescending(customersDescendingList));
    }

    private boolean isSortedDescending(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                return false;
            }
        }
        return true;
    }
}
