package DataAccessLayerJSON;

import Domains.Customer;
import Interfaces.ICustomerDAL;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CustomerJSON implements ICustomerDAL {
    private final File file = new File(getClass().getClassLoader().getResource("data.json").getFile());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Customer> customers;

    public CustomerJSON() {
        this.customers = loadCustomersFromFile();
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        return customers.stream()
                .filter(customer -> customer.get_id().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Customer> getCustomerByIndex(int index) {
        if (index >= 0 && index < customers.size()) {
            return Optional.of(customers.get(index));
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    @Override
    public boolean createCustomer(Customer customer) {
        if (customers.stream().noneMatch(c -> c.get_id().equals(customer.get_id()))) {
            customers.add(customer);
            return saveToFile();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).get_id().equals(customer.get_id())) {
                customers.set(i, customer);
                return saveToFile();
            }
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(UUID id) {
        if (customers.removeIf(customer -> customer.get_id().equals(id))) {
            return saveToFile();
        }
        return false;
    }

    // Private method to load all customers from JSON
    private List<Customer> loadCustomersFromFile() {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            LibraryData libraryData = objectMapper.readValue(file, LibraryData.class);
            return libraryData.getCustomers();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Private method to save all customers back to JSON
    private boolean saveToFile() {
        try {
            LibraryData libraryData = objectMapper.readValue(file, LibraryData.class);
            libraryData.setCustomers(customers);
            objectMapper.writeValue(file, libraryData);  // Write the updated LibraryData back to the file
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
