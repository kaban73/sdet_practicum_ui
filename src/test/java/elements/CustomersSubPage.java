package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CustomersSubPage {
    @FindBy(xpath = "//input[@placeholder='Search Customer']")
    private SelenideElement customersPlaceholder;
    @FindBy(xpath = "//table[@class='table table-bordered table-striped']")
    private SelenideElement customersTable;

    public CustomersSubPage checkCustomersTable() {
        customersTable.shouldBe(visible);
        return this;
    }

    public CustomersSubPage checkCustomerExists(String firstName, String lastName, String postCode) {
        $(By.xpath("//tr[td[1]" +
                "[contains(., '"+firstName+"')]" +
                " and td[2][contains(., '"+lastName+"')]" +
                " and td[3][contains(., '"+postCode+"')]]"))
                .shouldBe(exist);
        return this;
    }

    public CustomersSubPage searchCustomer(String postCode) {
        customersPlaceholder.type(postCode);
        return this;
    }
}
