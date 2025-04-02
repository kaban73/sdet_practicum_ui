package pages;

import com.codeborne.selenide.SelenideElement;
import elements.CustomerAddSubPage;
import elements.CustomersSubPage;
import io.qameta.allure.Step;
import model.CustomerData;
import org.openqa.selenium.support.FindBy;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

public class ManagerPage {
    @FindBy(xpath = "//div[@class='center']")
    private SelenideElement buttonsDiv;
    @FindBy(xpath = "//button[@ng-click='addCust()']")
    private SelenideElement customerAddButton;
    @FindBy(xpath = "//button[@ng-click='showCust()']")
    private SelenideElement customerShowButton;

    @Step("Проверить отображение страницы менеджера")
    public ManagerPage checkManagerPage() {
        buttonsDiv
                .shouldBe(visible);
        return this;
    }

    @Step("Нажать кнопку 'Add Customer' для перехода к форме добавления клиента")
    public CustomerAddSubPage clickAddCustomerButton() {
        customerAddButton
                .shouldBe(visible)
                .shouldHave(text("Add Customer"))
                .click();
        return page(CustomerAddSubPage.class);
    }

    @Step("Нажать кнопку 'Customers' для перехода к списку клиентов")
    public CustomersSubPage clickShowCustomerButton() {
        customerShowButton
                .shouldBe(visible)
                .shouldHave(text("Customers"))
                .click();
        return page(CustomersSubPage.class);
    }

    @Step("Открыть список дефолтных кастомеров")
    public CustomersSubPage prepareDefaultCustomer() {
        return this
                .checkManagerPage()
                .clickShowCustomerButton()
                .checkCustomersTable();
    }

    @Step("Добавить нового кастомера и открыть список кастомеров")
    public CustomersSubPage prepareNewCustomer(CustomerData customer) {
        this
                .checkManagerPage()
                .clickAddCustomerButton()
                .addNewCustomer(customer);
        return this.prepareDefaultCustomer();
    }
}
