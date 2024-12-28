package main.UI.customerPanel;

import main.Application;
import main.model.account.Customer;
import main.session.SessionContainer;
import main.UI.BasePage;
import main.UI.LogOut;

import javax.swing.*;

public class CustomerPanel extends BasePage implements LogOut {
    private JPanel mainPanel;
    private JButton bookACarButton;
    private JButton bookingHistoryButton;
    private JButton logOutButton;
    private JButton updateProfileButton;

    public CustomerPanel(Application application) {
        super(application);

        logOutButton.addActionListener(e -> this.logOut());
        bookACarButton.addActionListener(e -> application.toBookCar());
        bookingHistoryButton.addActionListener(e -> application.toCustomerBookingHistory());
        updateProfileButton.addActionListener(e -> application.toCustomerEdit((Customer) application.getSessionContainer().getSession().getCurrentUser()));
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public SessionContainer getSessionContainer() {
        return application.getSessionContainer();
    }
}
