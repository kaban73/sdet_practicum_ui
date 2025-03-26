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

    @BeforeMethod
    private void openAddCustomerForm() {
        page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm();
    }
    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class,
            description = "Создание нового Кастомера, а затем проверка на его добавление в список Кастомеров (П.1 чек-листа)"
    )
    public void create_new_customer_and_check_him(CustomerData customer) {
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

    @Test(
            description = "Создание нового Кастомера, а после попытка создания Кастомера с такими же данными и проверка на сообщение о дубликате"
    )
    public void create_duplicate_customer() {
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

    @Test(
            dataProvider = "emptyFieldsCustomers",
            dataProviderClass = CustomerDataProviders.class,
            description = "Попытка создания Кастомера с одним пустым полем и проверка на сообщение об ошибке"
    )
    public void try_create_new_customer_but_empty_field(CustomerData customer, String fieldName) {
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
