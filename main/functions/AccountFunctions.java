package main.functions;

import main.model.account.Account;

import java.util.Optional;

public interface AccountFunctions {
    Optional<? extends Account> findByUsername(String userName);
}
