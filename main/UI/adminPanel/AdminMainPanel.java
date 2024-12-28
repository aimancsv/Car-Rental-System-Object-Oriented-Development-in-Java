package main.UI.adminPanel;

import main.Application;
import main.session.SessionContainer;
import main.UI.BasePage;
import main.UI.LogOut;

import javax.swing.*;

public class AdminMainPanel extends BasePage implements LogOut {
    private JButton carButton;
    private JButton bookingButton;
    private JButton dailyReport;
    private JButton logOutButton;
    private JPanel mainPanel;
    private JButton monthlyIncomeButton;

    public AdminMainPanel(Application application) {
        super(application);

        logOutButton.addActionListener(e -> this.logOut());
        carButton.addActionListener(e -> application.toCarMenu());
        bookingButton.addActionListener(e -> application.toAdminBookingPage());
        dailyReport.addActionListener(e -> application.toDailySalesReportPage());
        monthlyIncomeButton.addActionListener(e -> application.toCarMonthlyRevenueReportPage());

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
