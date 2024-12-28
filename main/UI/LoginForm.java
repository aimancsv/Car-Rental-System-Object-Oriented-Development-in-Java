package main.UI;

import main.Application;
import main.model.account.Role;
import main.model.account.Account;
import main.functions.LoginFunctions;
import main.exception.ValidationException;
import main.utils.PromptSwing;
import main.utils.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginForm extends BasePage {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private JPanel mainPanel;
    private final LoginFunctions loginFunctions;
    public LoginForm(Application application, LoginFunctions loginFunctions) {
        super(application);
        this.loginFunctions = loginFunctions;

        registerButton.addActionListener(e -> application.toCustomerRegister());
        initLoginButton();
    }
    @Override
    public void open() {
        super.open();
        setUpEnterToLogin();
    }
    private void setUpEnterToLogin() {
        mainPanel.getRootPane().setDefaultButton(loginButton);
    }

    private void initLoginButton() {
        loginButton.addActionListener(this::doLogin);
    }

    /** Validation*/
    private void doLogin(ActionEvent e) {
        try {
            Validator.validateNotEmpty(usernameField.getText(), "Username cannot be empty!");
            Validator.validateNotEmpty(String.valueOf(passwordField.getPassword()), "Password cannot be empty!");

            Optional<? extends Account> user = loginFunctions.login(usernameField.getText(), String.valueOf(passwordField.getPassword()));

            onLoginSuccess(user
                    .orElseThrow(()
                            -> new ValidationException("Username or Password is incorrect, try again!")
                    ));
        } catch (ValidationException ex) {
            onLoginFailure(ex.getMessage());
        }
    }

    /** Error message if Username or Password is wrong*/
    private void onLoginFailure(String errorMessage) {
        PromptSwing.promptMessageError(errorMessage);
        refreshData();
    }

    /** Message if Username and Password are correct*/
    private void onLoginSuccess(Account account) {
        PromptSwing.promptMessageInfo("Welcome Back!");
        application.getSessionContainer().setCurrentUser(account);

        if (account.getRole().equals(Role.ADMIN)) {
            application.toAdminMenu();
            return;
        }

        application.toCustomerMenu();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
