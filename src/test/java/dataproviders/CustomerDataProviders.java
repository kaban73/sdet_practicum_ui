package dataproviders;

import org.testng.annotations.DataProvider;
import utils.CustomerDataGenerator;

public class CustomerDataProviders {
    @DataProvider(name = "dataCustomers")
    public static Object[][] provideDataCustomers() {
        return new Object[][] {
                {CustomerDataGenerator.generateRandomCustomer()},
                {CustomerDataGenerator.generateRandomCustomer()},
                {CustomerDataGenerator.generateRandomCustomer()}
        };
    }
}
