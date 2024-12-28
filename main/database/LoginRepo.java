package main.database;

import main.model.account.Account;

import java.util.Optional;

public interface LoginRepo<T extends Account> extends Repo<String, T> {
    Optional<T> findByUsername(String username);
}
