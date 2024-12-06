package DataAccessLayer;

import Domains.Customer;
import Interfaces.ICustomerDAL;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerDAL implements ICustomerDAL {
    private final List<Customer> customers;

    public CustomerDAL() {
        this.customers = new ArrayList<>();
    }

    // Retrieve a customer by ID
    @Override
    public Customer getCustomerById(UUID id) {
        return customers.stream()
                .filter(customer -> customer.get_id().equals(id))
                .findFirst();
    }

    // Retrieve all customers
    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    // Create a new customer
    @Override
    public boolean createCustomer(Customer customer) {
        return customers.add(customer);
    }

    // Update an existing customer
    @Override
    public boolean updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).get_id().equals(updatedCustomer.get_id())) {
                customers.set(i, updatedCustomer);
                return true;
            }
        }
        return false;
    }

    // Delete a customer by ID
    @Override
    public boolean deleteCustomer(UUID id) {
        return customers.removeIf(customer -> customer.get_id().equals(id));
    }
}
