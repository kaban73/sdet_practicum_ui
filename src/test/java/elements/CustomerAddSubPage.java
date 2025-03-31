package elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.CustomerData;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.webdriver;

public class CustomerAddSubPage {
    @FindBy(xpath = "//form[@name='myForm']")
    private SelenideElement customerAddForm;
    @FindBy(xpath = "//input[@ng-model='fName']")
    private SelenideElement firstNameInput;
    @FindBy(xpath = "//input[@ng-model='lName']")
    private SelenideElement lastNameInput;
    @FindBy(xpath = "//input[@ng-model='postCd']")
    private SelenideElement postCodeInput;
    @FindBy(xpath = "//button[contains(text(), 'Add Customer') and @type='submit']")
    private SelenideElement customerAddButtonForm;

    @Step("Проверить отображение формы добавления клиента")
    public CustomerAddSubPage checkAddCustomerForm() {
        customerAddForm
                .shouldBe(visible);
        return this;
    }

    @Step("Ввести имя клиента: {firstName}")
    public CustomerAddSubPage setFirstName(String firstName) {
        firstNameInput
                .shouldBe(visible)
                .type(firstName);
        return this;
    }

    @Step("Ввести фамилию клиента: {lastName}")
    public CustomerAddSubPage setLastName(String lastName) {
        lastNameInput
                .shouldBe(visible)
                .type(lastName);
        return this;
    }

    @Step("Ввести почтовый код клиента: {postCode}")
    public CustomerAddSubPage setPostCode(String postCode) {
        postCodeInput
                .shouldBe(visible)
                .type(postCode);
        return this;
    }

    @Step("Нажать кнопку 'Add Customer'")
    public CustomerAddSubPage clickAddCustomerButton() {
        customerAddButtonForm
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Добавить нового клиента: {customer.firstName} {customer.lastName} (почтовый код: {customer.postCode})")
    public CustomerAddSubPage addNewCustomer(CustomerData customer) {
        return setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setPostCode(customer.getPostCode())
                .clickAddCustomerButton();
    }

    @Step("Проверить валидацию пустого поля имени")
    public CustomerAddSubPage checkEmptyFirstName() {
        firstNameInput.shouldHave(cssClass("ng-invalid"));
        return this;
    }

    @Step("Проверить валидацию пустого поля фамилии")
    public CustomerAddSubPage checkEmptyLastName() {
        lastNameInput.shouldHave(cssClass("ng-invalid"));
        return this;
    }

    @Step("Проверить валидацию пустого почтового кода")
    public CustomerAddSubPage checkEmptyPostCode() {
        postCodeInput.shouldHave(cssClass("ng-invalid"));
        return this;
    }

    @Step("Проверить сообщение об успешном добавлении клиента")
    public CustomerAddSubPage checkSuccessAlert() {
        Alert alert = webdriver().driver().switchTo().alert();
        assert(alert.getText().contains("Customer added successfully"));
        alert.accept();

        return this;
    }

    @Step("Проверить сообщение о возможном дубликате клиента")
    public CustomerAddSubPage checkDuplicateAlert() {
        Alert alert = webdriver().driver().switchTo().alert();
        assert(alert.getText().contains("Customer may be duplicate"));
        alert.accept();

        return this;
    }
}
