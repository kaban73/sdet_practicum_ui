package model;

import utils.CustomerDataGenerator;

public class CustomerData {
    private String firstName;
    private String lastName;
    private String postCode;

    public CustomerData(String firstName, String lastName, String postCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPostCode() { return postCode; }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public static CustomerData generateRandomCustomer() {
        String postCode = CustomerDataGenerator.generateRandomPostCode();
        String firstName = CustomerDataGenerator.generateFirstNameFromPostCode(postCode);
        return new CustomerData(firstName, "TestLastName", postCode);
    }
}
