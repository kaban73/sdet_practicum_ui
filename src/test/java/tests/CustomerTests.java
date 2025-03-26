package tests;

import com.codeborne.selenide.Configuration;
import elements.AddCustomerSubPage;
import org.openqa.selenium.Alert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;
import java.util.Random;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Selenide.*;

public class CustomerTests extends BaseTest {
    @BeforeMethod
    void initBeforeMethod() {
        open(Configuration.baseUrl);
    }

    @Test
    public void check_managerPage_is_visible() {
        page(new ManagerPage())
                .checkManagerPage();
        sleep(3000);
    }
    @Test
    public void click_addCustomer() {
        page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm();
        sleep(3000);
    }

    @Test
    public void create_new_customer_and_check_him() {
        click_addCustomer();

        ManagerPage managerPage = page(new ManagerPage());
        String postCode = generateRandomPostCode();
        String firstName = generateFirstNameFromPostCode(postCode);
        String lastName = "Test LastName";

        managerPage
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPostCode(postCode)
                .clickAddCustomerButton();

        Alert alert = webdriver().driver().switchTo().alert();
        assert(alert.getText().contains("Customer added successfully"));
        alert.accept();

        managerPage
                .clickShowCustomerButton()
                .checkCustomersTable()
                .searchCustomer(postCode)
                .checkCustomerExists(firstName, lastName, postCode);

        sleep(3000);
    }

    @Test
    public void create_duplicate_customer() {
        click_addCustomer();

        String postCode = generateRandomPostCode();
        String firstName = generateFirstNameFromPostCode(postCode);
        String lastName = "Test LastName";

        AddCustomerSubPage addCustomerSubPage = page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPostCode(postCode)
                .clickAddCustomerButton();

        Alert alert = webdriver().driver().switchTo().alert();
        assert(alert.getText().contains("Customer added successfully"));
        alert.accept();

        addCustomerSubPage
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPostCode(postCode)
                .clickAddCustomerButton();

        alert = webdriver().driver().switchTo().alert();
        assert(alert.getText().contains("Customer may be duplicate"));
        alert.accept();

        sleep(3000);
    }

    @Test
    public void try_create_new_customer_but_empty_field() {
        click_addCustomer();

        String postCode = generateRandomPostCode();
        String lastName = "Test LastName";

        page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm()
                .setLastName(lastName)
                .setPostCode(postCode)
                .clickAddCustomerButton()
                .checkEmptyFirstName();

        sleep(3000);
    }

    private String generateRandomPostCode() {
        return new Random().ints(10, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

    private String generateFirstNameFromPostCode(String postCode) {
        StringBuilder firstName = new StringBuilder();
        for (int i = 0; i < postCode.length(); i += 2) {
            int num = Integer.parseInt(postCode.substring(i, Math.min(i + 2, postCode.length())));
            char c = (char) ('a' + (num % 26));
            firstName.append(c);
        }
        return firstName.toString();
    }
}
