package Service;

import Domains.Customer;
import Interfaces.ICustomerDAL;

import java.util.*;

public class CustomerService {
    private final ICustomerDAL customerDAL;

    public CustomerService(ICustomerDAL customerDAL) {
        this.customerDAL = customerDAL;
    }

    // Create a new customer
    public boolean createCustomer(Customer customer) {
        return customerDAL.createCustomer(customer);
    }

    // Retrieve a customer by ID
    public Optional<Customer> getCustomerById(UUID id) {
        return customerDAL.getCustomerById(id);
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        return customerDAL.getAllCustomers();
    }

    // Update an existing customer
    public boolean updateCustomer(Customer updatedCustomer) {
        return customerDAL.updateCustomer(updatedCustomer);
    }

    // Delete a customer by ID
    public boolean deleteCustomer(UUID id) {
        return customerDAL.deleteCustomer(id);
    }

    // Flexible search method for any customer parameter
    public List<Customer> searchCustomers(Map<String, String> searchParams) {
        List<Customer> allCustomers = customerDAL.getAllCustomers();
        List<Customer> filteredCustomers = new ArrayList<>();

        for (Customer customer : allCustomers) {
            boolean matches = true;
            for (Map.Entry<String, String> entry : searchParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                switch (key.toLowerCase()) {
                    case "firstname":
                        if (!customer.get_firstName().equalsIgnoreCase(value)) {
                            matches = false;
                        }
                        break;
                    case "lastname":
                        if (!customer.get_lastName().equalsIgnoreCase(value)) {
                            matches = false;
                        }
                        break;
                    case "email":
                        if (!customer.get_email().equalsIgnoreCase(value)) {
                            matches = false;
                        }
                        break;
                    case "phone":
                        if (!customer.get_phone().equalsIgnoreCase(value)) {
                            matches = false;
                        }
                        break;
                    default:
                        System.out.println("Invalid search parameter: " + key);
                }
                if (!matches) break; // No need to check further if one parameter doesn't match
            }
            if (matches) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;
    }
}
