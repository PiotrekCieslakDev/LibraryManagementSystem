package DataAccessLayerJSON;

import Domains.Customer;
import Interfaces.ICustomerDAL;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerJSON implements ICustomerDAL {
    private final File file = new File("customers.json");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Customer> customers;

    public CustomerJSON() {
        this.customers = loadCustomersFromFile();
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customers.stream()
                .filter(customer -> customer.get_id().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Customer getCustomerByIndex(int index) {
        if (index >= 0 && index < customers.size()) {
            return customers.get(index);
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    @Override
    public boolean createCustomer(Customer customer) {
        if (getCustomerById(customer.get_id()) == null) {
            customers.add(customer);
            return saveCustomersToFile();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).get_id().equals(customer.get_id())) {
                customers.set(i, customer);
                return saveCustomersToFile();
            }
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(UUID id) {
        if (customers.removeIf(customer -> customer.get_id().equals(id))) {
            return saveCustomersToFile();
        }
        return false;
    }

    // Prywatna metoda do ładowania klientów z pliku JSON
    private List<Customer> loadCustomersFromFile() {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Customer>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Prywatna metoda do zapisywania klientów do pliku JSON
    private boolean saveCustomersToFile() {
        try {
            objectMapper.writeValue(file, customers);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
