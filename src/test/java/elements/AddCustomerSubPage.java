package elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import static com.codeborne.selenide.Condition.visible;

public class AddCustomerSubPage {
    @FindBy(xpath = "//form[@name='myForm']")
    private SelenideElement addCustomerForm;
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private SelenideElement firstNameInput;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private SelenideElement lastNameInput;
    @FindBy(xpath = "//input[@placeholder='Post Code']")
    private SelenideElement postCodeInput;
    @FindBy(xpath = "//button[contains(text(), 'Add Customer') and @type='submit']")
    private SelenideElement addCustomerButtonForm;

    public AddCustomerSubPage checkAddCustomerForm() {
        addCustomerForm
                .shouldBe(visible);
        return this;
    }

    public AddCustomerSubPage addNewCustomer(String firstName, String lastName, String postCode) {
        firstNameInput
                .shouldBe(visible)
                .type(firstName);

        lastNameInput
                .shouldBe(visible)
                .type(lastName);

        postCodeInput
                .shouldBe(visible)
                .type(postCode);

        addCustomerButtonForm
                .shouldBe(visible)
                .click();

        return this;
    }

}
