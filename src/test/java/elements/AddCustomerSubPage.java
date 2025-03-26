package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;

public class AddCustomerSubPage {
    @FindBy(xpath = "//form[@name='myForm']")
    private SelenideElement addCustomerForm;
    @FindBy(xpath = "//input[@ng-model='fName']")
    private SelenideElement firstNameInput;
    @FindBy(xpath = "//input[@ng-model='lName']")
    private SelenideElement lastNameInput;
    @FindBy(xpath = "//input[@ng-model='postCd']")
    private SelenideElement postCodeInput;
    @FindBy(xpath = "//button[contains(text(), 'Add Customer') and @type='submit']")
    private SelenideElement addCustomerButtonForm;

    public AddCustomerSubPage checkAddCustomerForm() {
        addCustomerForm
                .shouldBe(visible);
        return this;
    }

    public AddCustomerSubPage setFirstName(String firstName) {
        firstNameInput
                .shouldBe(visible)
                .type(firstName);
        return this;
    }

    public AddCustomerSubPage setLastName(String lastName) {
        lastNameInput
                .shouldBe(visible)
                .type(lastName);
        return this;
    }

    public AddCustomerSubPage setPostCode(String postCode) {
        postCodeInput
                .shouldBe(visible)
                .type(postCode);
        return this;
    }

    public AddCustomerSubPage clickAddCustomerButton() {
        addCustomerButtonForm
                .shouldHave(visible)
                .click();
        return this;
    }

    public AddCustomerSubPage checkEmptyFirstName() {
        firstNameInput.shouldHave(cssClass("ng-invalid"));
        return this;
    }

}
