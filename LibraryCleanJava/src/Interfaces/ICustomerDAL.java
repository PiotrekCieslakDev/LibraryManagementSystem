package Interfaces;

import Domains.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Interface for customer database access, all methods are inherently abstract as it is an interface
public interface ICustomerDAL {
    //Retrievers
    public Optional<Customer> getCustomerById(UUID id);
    public List<Customer> getAllCustomers();
    public Optional<Customer> getCustomerByIndex(int index);

    //Create
    public boolean createCustomer(Customer customer);

    //Update
    public boolean updateCustomer(Customer updatedCustomer);

    //Delete
    public boolean deleteCustomer(UUID id);
}
