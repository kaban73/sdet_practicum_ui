package elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.CustomerData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CustomersSubPage {
    @FindBy(xpath = "//input[@placeholder='Search Customer']")
    private SelenideElement customersPlaceholder;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped']")
    private SelenideElement customersTable;
    @FindBy(xpath = "//a[contains(., 'First Name')]")
    private SelenideElement theadFirstName;
    @FindBy(xpath= "//tr//td[1]")
    private ElementsCollection firstTableColumn;

    @Step("Проверить отображение таблицы клиентов")
    public CustomersSubPage checkCustomersTable() {
        customersTable.shouldBe(visible, Duration.ofSeconds(3));
        return this;
    }

    @Step("Проверить существование клиента: {customer.firstName} {customer.lastName} (почтовый код: {customer.postCode})")
    public CustomersSubPage checkCustomerExists(CustomerData customer) {
        $(By.xpath("//tr[td[1]" +
                "[contains(., '"+customer.getFirstName()+"')]" +
                " and td[2][contains(., '"+customer.getLastName()+"')]" +
                " and td[3][contains(., '"+customer.getPostCode()+"')]]"))
                .shouldBe(exist);
        return this;
    }

    @Step("Проверить отсутствие клиента с именем: {firstName}")
    public CustomersSubPage checkCustomerNotExistsForFirstName(String firstName) {
        $(By.xpath("//tr[td[1][contains(., '"+firstName+"')]]"))
                .shouldNotBe(exist);
        return this;
    }

    @Step("Поиск клиента по почтовому коду: {postCode}")
    public CustomersSubPage searchCustomerForPostCode(String postCode) {
        customersPlaceholder.type(postCode);
        return this;
    }

    @Step("Поиск клиента по имени: {firstName}")
    public CustomersSubPage searchCustomerForFirstName(String firstName) {
        customersPlaceholder.type(firstName);
        return this;
    }

    @Step("Клик по заголовку 'First Name' для сортировки")
    public CustomersSubPage clickTheadFirstName() {
        theadFirstName
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Получить список имен клиентов из таблицы")
    public List<String> getCustomersFirstNames() {
        List<String> list = firstTableColumn.texts();
        list.remove(0);
        return list;
    }

    @Step("Удалить клиента с именем: {firstName}")
    public CustomersSubPage deleteCustomerForFirstName(String firstName) {
        $(By.xpath(
                "//tr[td[1][text()='"
                        + firstName +
                        "']]//button[contains(@ng-click, 'deleteCust')]"
        ))
                .shouldBe(visible)
                .click();

        return this;
    }
}
