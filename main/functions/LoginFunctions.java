package main.functions;

import main.model.account.Account;

import java.util.Optional;

public interface LoginFunctions {
    Optional<? extends Account> login(String username, String password);
}
