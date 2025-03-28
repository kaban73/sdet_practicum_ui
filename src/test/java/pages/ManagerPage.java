package pages;

import com.codeborne.selenide.SelenideElement;
import elements.AddCustomerSubPage;
import elements.CustomersSubPage;
import org.openqa.selenium.support.FindBy;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.page;

public class ManagerPage {
    @FindBy(xpath = "//div[@class='center']")
    private SelenideElement buttonsDiv;
    @FindBy(xpath = "//button[@ng-click='addCust()']")
    private SelenideElement addCustomerButton;
    @FindBy(xpath = "//button[@ng-click='showCust()']")
    private SelenideElement showCustomerButton;

    public ManagerPage checkManagerPage() {
        buttonsDiv
                .shouldBe(visible);
        return this;
    }
    public AddCustomerSubPage clickAddCustomerButton() {
        addCustomerButton
                .shouldBe(visible)
                .shouldHave(text("Add Customer"))
                .click();
        return page(AddCustomerSubPage.class);
    }
    public CustomersSubPage clickShowCustomerButton() {
        showCustomerButton
                .shouldBe(visible)
                .shouldHave(text("Customers"))
                .click();
        return page(CustomersSubPage.class);
    }
}
