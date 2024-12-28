package main.functions;

import main.model.account.Customer;
import main.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public interface CustomerFunctions extends AccountFunctions {
    void registerCustomer(Customer customer) throws ValidationException;
    Customer getByName(String customer);
    List<Customer> getAllCustomers();
    Optional<Customer> findByName(String text);
    void updateCustomer(Customer customer);
}
