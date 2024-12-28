package main.functions;

import main.database.AdminRepo;
import main.database.CustomerRepo;
import main.model.account.Customer;
import main.model.account.Account;
import main.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerFunctionsImpl implements CustomerFunctions {
    private final CustomerRepo customerRepo;
    private final AdminRepo adminRepo;

    public CustomerFunctionsImpl(CustomerRepo customerRepo, AdminRepo adminRepo) {
        this.customerRepo = customerRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public void registerCustomer(Customer customer) throws ValidationException {
        if (customerRepo.findByPrimaryKey(customer.getUsername()).isPresent() ||
                adminRepo.findByUsername(customer.getUsername()).isPresent())
            throw new ValidationException("User exists!");
        customerRepo.insert(customer);
    }

    @Override
    public Customer getByName(String customer) {
        return customerRepo.getByPrimaryKey(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.getLines().collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findByName(String text) {
        return customerRepo.findByPrimaryKey(text);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepo.update(customer);
    }

    @Override
    public Optional<? extends Account> findByUsername(String userName) {
        return customerRepo.findByUsername(userName);
    }
}
