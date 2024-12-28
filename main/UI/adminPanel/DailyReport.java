package main.UI.adminPanel;

import main.Application;
import main.model.booking.Payment;
import main.functions.PaymentFunctions;
import main.UI.BasePage;
import main.UI.tableUI.PaymentTableUI;
import main.utils.DatePicker;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class DailyReport extends BasePage {
    private JPanel mainPanel;
    private JDatePickerImpl paymentDatePicker;
    private JButton BackButton;
    private JTable paymentTable;
    private JLabel totalField;

    private final PaymentFunctions paymentFunctions;
    private PaymentTableUI paymentTableModel;

    public DailyReport(Application application, PaymentFunctions paymentFunctions) {
        super(application);
        this.paymentFunctions = paymentFunctions;

        BackButton.addActionListener(this::backToPreviousPage);
        paymentDatePicker.addActionListener(e -> this.loadData());
    }

    /** For loading payment data*/
    private void loadData() {
        LocalDate selectedDate = DatePicker.getDateFromDatePicker(paymentDatePicker);
        List<Payment> payments = paymentFunctions.getPaymentsByDate(selectedDate);
        paymentTableModel.refreshData(payments);
        paymentTable.addNotify();
        setTotal(payments.stream().map(Payment::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    /** For converting sales to total*/
    private void setTotal(BigDecimal total) {
        totalField.setText(String.format("Total: RM %s", total.setScale(2, RoundingMode.HALF_UP)));
    }

    private void createUIComponents() {
        paymentTableModel = new PaymentTableUI();
        paymentTable = new JTable(paymentTableModel);
        paymentDatePicker = DatePicker.createNewDatePicker();
    }

    @Override
    public void refreshData() {
        DatePicker.setDatePickerSelectedDate(paymentDatePicker, LocalDate.now());
        loadData();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }
}
