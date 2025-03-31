package tests;

import dataproviders.CustomerDataProviders;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.CustomerData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ManagerPage;
import utils.CustomerDataGenerator;

import static com.codeborne.selenide.Selenide.*;

@Epic("Операции с кастомерами")
@Story("Создание кастомеров")
public class CustomerCreateTests extends BaseTest {
    @BeforeMethod
    private void openAddCustomerForm() {
        managerPage = page(ManagerPage.class);
        customerAddSubPage = managerPage
                .checkManagerPage()
                .clickAddCustomerButton();
        customerAddSubPage.checkAddCustomerForm();
    }

    @Test(
            dataProvider = "dataCustomers",
            dataProviderClass = CustomerDataProviders.class
    )
    @Description("Создание нового Кастомера, а затем проверка на его добавление в список Кастомеров (П.1 чек-листа)")
    @Feature("Успешное создание кастомера")
    public void testCreateNewCustomerAndVerifyInList(CustomerData customer) {
        customerAddSubPage
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
    @Feature("Попытка создания дубликата кастомера")
    public void testCreateDuplicateCustomer() {
        CustomerData customer = CustomerDataGenerator.generateRandomCustomer();

        customerAddSubPage
                .addNewCustomer(customer)
                .checkSuccessAlert();

        customerAddSubPage
                .addNewCustomer(customer)
                .checkDuplicateAlert();
    }

    @Test
    @Description("Попытка создания Кастомера с пустым полем имени и проверка на сообщение об ошибке")
    @Feature("Попытка создания кастомера с пустым полем имени")
    public void testCreateCustomerWithEmptyFirstNameField() {
        CustomerData customer = CustomerDataGenerator.generateRandomCustomer();
        customer.setFirstName("");

        customerAddSubPage
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setPostCode(customer.getPostCode())
                .clickAddCustomerButton()
                .checkEmptyFirstName();

    }

    @Test
    @Description("Попытка создания Кастомера с пустым полем фамилии и проверка на сообщение об ошибке")
    @Feature("Попытка создания кастомера с пустым полем фамилии")
    public void testCreateCustomerWithEmptyLastNameField() {
        CustomerData customer = CustomerDataGenerator.generateRandomCustomer();
        customer.setLastName("");

        customerAddSubPage
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setPostCode(customer.getPostCode())
                .clickAddCustomerButton()
                .checkEmptyLastName();

    }

    @Test
    @Description("Попытка создания Кастомера с пустым полем почтового индекса и проверка на сообщение об ошибке")
    @Feature("Попытка создания кастомера с пустым полем почтового индекса")
    public void testCreateCustomerWithEmptyPostCodeField() {
        CustomerData customer = CustomerDataGenerator.generateRandomCustomer();
        customer.setPostCode("");

        customerAddSubPage
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setPostCode(customer.getPostCode())
                .clickAddCustomerButton()
                .checkEmptyPostCode();

    }
}
