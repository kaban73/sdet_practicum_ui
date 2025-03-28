package tests;

import dataproviders.CustomerDataProviders;
import elements.AddCustomerSubPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.CustomerData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;
import static com.codeborne.selenide.Selenide.*;

@Epic("Операции с кастомерами")
@Feature("Создание кастомеров")
public class CustomerCreateTests extends BaseTest {
    private ManagerPage managerPage;
    private AddCustomerSubPage addCustomerSubPage;
    @BeforeMethod
    private void openAddCustomerForm() {
        managerPage = page(ManagerPage.class);
        addCustomerSubPage = managerPage
                .checkManagerPage()
                .clickAddCustomerButton();
        addCustomerSubPage.checkAddCustomerForm();
    }
    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class
    )
    @Description("Создание нового Кастомера, а затем проверка на его добавление в список Кастомеров (П.1 чек-листа)")
    @Story("Успешное создание кастомера")
    public void create_new_customer_and_check_him(CustomerData customer) {
        addCustomerSubPage
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
    }

    @Test
    @Description("Создание нового Кастомера, а после попытка создания Кастомера с такими же данными и проверка на сообщение о дубликате")
    @Story("Попытка создания дубликата кастомера")
    public void create_duplicate_customer() {
        CustomerData customer = CustomerData.generateRandomCustomer();

        addCustomerSubPage
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
        addCustomerSubPage
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
