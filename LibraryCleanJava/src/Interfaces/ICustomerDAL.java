package Interfaces;

import Domains.Customer;

import java.util.List;
import java.util.UUID;

public interface ICustomerDAL {
    //Retrievers
    public Customer getCustomerById(UUID id);
    public List<Customer> getAllCustomers();
    public Customer getCustomerByIndex(int index);

    //Create
    public boolean createCustomer(Customer customer);

    //Update
    public boolean updateCustomer(Customer updatedCustomer);

    //Delete
    public boolean deleteCustomer(UUID id);
}
