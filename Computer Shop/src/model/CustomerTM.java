package model;

public class CustomerTM {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerContact;
    private String customerCity;

    public CustomerTM(String customerId, String customerName, String customerAddress, String customerContact, String customerCity) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerContact = customerContact;
        this.customerCity = customerCity;
    }

    public CustomerTM() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customerContact='" + customerContact + '\'' +
                ", customerCity='" + customerCity + '\'' +
                '}';
    }
}
