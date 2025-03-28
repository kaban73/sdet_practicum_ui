package tests;

import com.codeborne.selenide.Configuration;
import dataproviders.CustomerDataProviders;
import elements.AddCustomerSubPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.CustomerData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;
import static com.codeborne.selenide.Selenide.*;

@Epic("Операции с кастомерами")
@Feature("Создание кастомеров")
public class CustomerCreateTests extends BaseTest {
    @BeforeMethod
    private void openAddCustomerForm() {
        open(Configuration.baseUrl);
        page(new ManagerPage())
                .checkManagerPage()
                .clickAddCustomerButton()
                .checkAddCustomerForm();
    }
    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class
    )
    @Description("Создание нового Кастомера, а затем проверка на его добавление в список Кастомеров (П.1 чек-листа)")
    @Story("Успешное создание кастомера")
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
                .searchCustomerForPostCode(customer.getPostCode())
                .checkCustomerExists(customer);

        sleep(3000);
    }

    @Test
    @Description("Создание нового Кастомера, а после попытка создания Кастомера с такими же данными и проверка на сообщение о дубликате")
    @Story("Попытка создания дубликата кастомера")
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
            dataProviderClass = CustomerDataProviders.class
    )
    @Description("Попытка создания Кастомера с одним пустым полем и проверка на сообщение об ошибке")
    @Story("Попытка создания кастомера с пустыми полями")
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

    @AfterMethod
    public void cleanupAfterEachTest() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }
}
