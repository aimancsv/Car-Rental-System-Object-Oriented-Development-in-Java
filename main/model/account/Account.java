package main.model.account;

import main.database.Model;

public abstract class Account implements Model<String> {
    private String username;
    private String password;
    private Role role;

    public Account(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getPrimaryKey() {
        return getUsername();
    }

    @Override
    public void setPrimaryKey(String pk) {
        setUsername(pk);
    }
}
