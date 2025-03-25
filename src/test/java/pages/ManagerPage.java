package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class ManagerPage {
    @FindBy(xpath = "//button[@ng-click='addCust()']")
    private SelenideElement addCustomerButton;

    public ManagerPage checkManagerPage() {
        addCustomerButton
                .shouldBe(visible)
                .shouldHave(text("Add Customer"));
        return this;
    }
}
