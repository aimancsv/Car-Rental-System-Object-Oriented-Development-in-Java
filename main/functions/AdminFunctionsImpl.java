package main.functions;

import main.database.AdminRepo;
import main.model.account.Account;

import java.util.Optional;

public class AdminFunctionsImpl implements AdminFunctions {
    private final AdminRepo adminRepo;

    public AdminFunctionsImpl(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @Override
    public Optional<? extends Account> findByUsername(String userName) {
        return adminRepo.findByUsername(userName);
    }
}
