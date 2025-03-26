package tests;

import com.codeborne.selenide.Configuration;
import dataproviders.CustomerDataProviders;
import elements.AddCustomerSubPage;
import model.CustomerData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;
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

    @Test(dataProvider = "dataCustomers", dataProviderClass = CustomerDataProviders.class)
    public void create_new_customer_and_check_him(CustomerData customer) {
        click_addCustomer();

        ManagerPage managerPage = page(new ManagerPage());

        managerPage
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm()
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setPostCode(customer.getPostCode())
                .clickAddCustomerButton()
                .checkSuccessAlert();

        managerPage
                .clickShowCustomerButton()
                .checkCustomersTable()
                .searchCustomer(customer.getPostCode())
                .checkCustomerExists(customer);

        sleep(3000);
    }

    @Test
    public void create_duplicate_customer() {
        click_addCustomer();

        CustomerData customer = CustomerData.generateRandomCustomer();

        AddCustomerSubPage addCustomerSubPage = page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm()
                .addNewCustomer(customer)
                .checkSuccessAlert();



        addCustomerSubPage
                .addNewCustomer(customer)
                .checkDuplicateAlert();
    }

    @Test(dataProvider = "emptyFieldsCustomers", dataProviderClass = CustomerDataProviders.class)
    public void try_create_new_customer_but_empty_field(CustomerData customer, String fieldName) {
        click_addCustomer();

        AddCustomerSubPage addCustomerSubPage = page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm()
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setPostCode(customer.getPostCode());

        switch (fieldName) {
            case "firstName":
                addCustomerSubPage.checkEmptyFirstName();
                break;
            case "lastName":
                addCustomerSubPage.checkEmptyLastName();
                break;
            case "postCode":
                addCustomerSubPage.checkEmptyPostCode();
                break;
        }

        addCustomerSubPage
                .clickAddCustomerButton();
    }
}
