package main.functions;

import main.database.AdminRepo;
import main.database.CustomerRepo;
import main.model.account.Admin;
import main.model.account.Account;

import java.util.Optional;

public class LoginFunctionsImpl implements LoginFunctions {

    private final AdminRepo adminRepo;
    private final CustomerRepo customerRepo;

    public LoginFunctionsImpl(AdminRepo adminRepo, CustomerRepo customerRepo) {
        this.adminRepo = adminRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public Optional<? extends Account> login(String username, String password) {
        Optional<Admin> admin = adminRepo.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));

        if (admin.isPresent())
            return admin;

        return customerRepo.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));

    }
}
