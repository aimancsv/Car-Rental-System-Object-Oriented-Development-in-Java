package main.session;

import main.model.account.Account;

public interface SessionContainer {
    Session getSession();

    void setCurrentUser(Account account);

}
