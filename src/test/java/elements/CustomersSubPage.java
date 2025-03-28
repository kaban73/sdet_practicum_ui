package elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
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
    public CustomersSubPage checkCustomersTable() {
        customersTable.shouldBe(visible, Duration.ofSeconds(3));
        return this;
    }

    public CustomersSubPage checkCustomerExists(CustomerData customer) {
        $(By.xpath("//tr[td[1]" +
                "[contains(., '"+customer.getFirstName()+"')]" +
                " and td[2][contains(., '"+customer.getLastName()+"')]" +
                " and td[3][contains(., '"+customer.getPostCode()+"')]]"))
                .shouldBe(exist);
        return this;
    }

    public CustomersSubPage checkCustomerNotExistsForFirstName(String firstName) {
        $(By.xpath("//tr[td[1][contains(., '"+firstName+"')]]"))
                .shouldNotBe(exist);
        return this;
    }

    public CustomersSubPage searchCustomerForPostCode(String postCode) {
        customersPlaceholder.type(postCode);
        return this;
    }

    public CustomersSubPage searchCustomerForFirstName(String firstName) {
        customersPlaceholder.type(firstName);
        return this;
    }

    public CustomersSubPage clickTheadFirstName() {
        theadFirstName
                .shouldBe(visible)
                .click();
        return this;
    }

    public List<String> getCustomersFirstNames() {
        List<String> list = firstTableColumn.texts();
        list.remove(0);
        return list;
    }
    
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
