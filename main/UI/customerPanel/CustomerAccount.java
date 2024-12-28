package main.UI.customerPanel;

import main.Application;
import main.model.account.Customer;
import main.model.account.Gender;
import main.exception.ValidationException;
import main.UI.BasePage;
import main.utils.PromptSwing;
import main.utils.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CustomerAccount extends BasePage {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox genderComboBox;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JButton submitButton;
    private JButton backButton;
    private JPanel mainPanel;

    private final SuccessAction successAction;

    public CustomerAccount(Application application, SuccessAction successAction, boolean usernameEnabled) {
        super(application);
        this.successAction = successAction;

        usernameField.setEnabled(usernameEnabled);

        backButton.addActionListener(this::backToPreviousPage);
        submitButton.addActionListener(this::registerCustomer);
    }

    /** To register a new customer*/
    private void registerCustomer(ActionEvent event) {
        try {
            validateData();
            Customer customer = retrieveCustomer();
            successAction.doAction(customer);
            onRegisterSuccess(customer);
        } catch (ValidationException e) {
            onRegisterFail(e);
        }
    }


    /** Prompt if customer successfully registered*/
    private void onRegisterSuccess(Customer customer) {
        PromptSwing.promptMessageInfo("Successfully registered!");
        application.getSessionContainer().setCurrentUser(customer);
        backToPreviousPage();
    }

    /** Error message if register fail*/
    private void onRegisterFail(ValidationException e) {
        PromptSwing.promptMessageError(e.getMessage());
    }

    /** Collect customer data*/
    private Customer retrieveCustomer() {
        return new Customer(usernameField.getText(), String.valueOf(passwordField.getPassword()),
                Gender.valueOf(genderComboBox.getSelectedItem().toString()), Integer.parseInt(ageField.getText()),
                phoneField.getText(), emailField.getText(), addressField.getText());
    }

    /** data validation
     */
    private void validateData() throws ValidationException {
        validateUsername(usernameField.getText());
        validatePassword(String.valueOf(passwordField.getPassword()));
        validateAge(ageField.getText());
        validatePhone(phoneField.getText());
        validateEmail(emailField.getText());
        validateAddress(addressField.getText());
    }

    private void validateAddress(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Address cannot be empty!");
    }

    private void validateEmail(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Email cannot be empty!");
        Validator.validateMatchRegex(text, "^[A-Za-z0-9+_.-]+@(.+)$", "Please enter a valid email!");
    }

    private void validatePhone(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Phone number cannot be empty!");
        Validator.validateMatchRegex(text, "^(\\+?6?01)[0-46-9]-*[0-9]{7,8}$", "Please enter a valid phone number!");
    }

    private void validateAge(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Age cannot be empty!");
        Validator.validateIsInteger(text, "Age must be a number!");

        int age = Integer.parseInt(text);
        if (age <= 0 | age > 200)
            throw new ValidationException("Age is out of range!");
    }

    private void validatePassword(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Password cannot be empty!");
    }

    private void validateUsername(String text) throws ValidationException {
        Validator.validateNotEmpty(text, "Username cannot be empty!");
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        usernameField.setText("");
        passwordField.setText("");
        ageField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }

    public void initializeData(Customer customer) {
        usernameField.setText(customer.getUsername());
        passwordField.setText(customer.getPassword());
        ageField.setText(customer.getAge().toString());
        phoneField.setText(customer.getPhone());
        emailField.setText(customer.getEmail());
        addressField.setText(customer.getAddress());
    }

    @FunctionalInterface
    public interface SuccessAction {
        void doAction(Customer customer) throws ValidationException;
    }
}
