package main.model.account;

public class Admin extends Account {
    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }
}
