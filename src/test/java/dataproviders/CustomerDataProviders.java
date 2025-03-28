package dataproviders;

import model.CustomerData;
import org.testng.annotations.DataProvider;

public class CustomerDataProviders {
    @DataProvider(name = "dataCustomers")
    public static Object[][] provideDataCustomers() {
        return new Object[][] {
                {CustomerData.generateRandomCustomer()},
                {CustomerData.generateRandomCustomer()},
                {CustomerData.generateRandomCustomer()}
        };
    }

    @DataProvider(name = "emptyFieldsCustomers")
    public static Object[][] provideEmptyFieldsCustomers() {
        CustomerData customer1 = CustomerData.generateRandomCustomer();
        customer1.setFirstName("");

        CustomerData customer2 = CustomerData.generateRandomCustomer();
        customer2.setLastName("");

        CustomerData customer3 = CustomerData.generateRandomCustomer();
        customer3.setPostCode("");

        return new Object[][] {
                {customer1, "firstName"},
                {customer2, "lastName"},
                {customer3, "postCode"}
        };
    }
}
