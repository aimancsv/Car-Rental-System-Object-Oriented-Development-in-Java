package main.session;

import main.model.account.Role;
import main.model.account.Account;

public class Session {
    private boolean loggedIn = false;
    private Role role;
    private Account currentAccount;

    public Account getCurrentUser() {
        return currentAccount;
    }

    public void setCurrentUser(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
