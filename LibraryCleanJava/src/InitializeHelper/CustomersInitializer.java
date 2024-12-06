package InitializeHelper;

import Domains.Customer;
import Interfaces.ICustomerDAL;

import java.util.ArrayList;
import java.util.List;

public class CustomersInitializer {
    private final ICustomerDAL customerDAL;

    public CustomersInitializer(ICustomerDAL customerDAL) {
        this.customerDAL = customerDAL;
        initializeCustomers();
    }

    private void initializeCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alice", "Smith", "alice@example.com", "111-222-3333", new ArrayList<>()));
        customers.add(new Customer("Bob", "Johnson", "bob@example.com", "444-555-6666", new ArrayList<>()));
        customers.add(new Customer("Charlie", "Williams", "charlie@example.com", "777-888-9999", new ArrayList<>()));
        customers.add(new Customer("Diana", "Jones", "diana@example.com", "000-111-2222", new ArrayList<>()));
        customers.add(new Customer("Edward", "Brown", "edward@example.com", "333-444-5555", new ArrayList<>()));
        customers.add(new Customer("Fiona", "Garcia", "fiona@example.com", "666-777-8888", new ArrayList<>()));
        customers.add(new Customer("George", "Martinez", "george@example.com", "999-000-1111", new ArrayList<>()));
        customers.add(new Customer("Hannah", "Davis", "hannah@example.com", "222-333-4444", new ArrayList<>()));
        customers.add(new Customer("Ian", "Rodriguez", "ian@example.com", "555-666-7777", new ArrayList<>()));
        customers.add(new Customer("Jasmine", "Hernandez", "jasmine@example.com", "888-999-0000", new ArrayList<>()));

        customers.forEach(customerDAL::createCustomer);
    }
}
