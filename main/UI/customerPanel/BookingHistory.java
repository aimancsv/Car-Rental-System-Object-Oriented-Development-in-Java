package main.UI.customerPanel;

import main.Application;
import main.model.booking.Booking;
import main.model.booking.BookingCondition;
import main.functions.BookingFunctions;
import main.UI.BasePage;
import main.UI.tableUI.CustomerBookingTableUI;
import main.utils.PromptSwing;

import javax.swing.*;

public class BookingHistory extends BasePage {
    private JPanel mainPanel;
    private JTable bookingTable;
    private JButton openReceiptButton;
    private JButton backButton;

    private CustomerBookingTableUI bookingTableModel;

    private final BookingFunctions bookingFunctions;


    public BookingHistory(Application application, BookingFunctions bookingFunctions) {
        super(application);
        this.bookingFunctions = bookingFunctions;

        backButton.addActionListener(this::backToPreviousPage);
        bookingTable.getSelectionModel().addListSelectionListener(e -> onItemSelected());
        openReceiptButton.addActionListener(e -> openReceipt());
    }

    private void openReceipt() {
        int selectedRow = bookingTable.getSelectedRow();
        Booking booking = bookingTableModel.getModelAt(selectedRow);

        if (booking.getStatus().equals(BookingCondition.UNCONFIRMED)) {
            PromptSwing.promptMessageError("Booking has not been confirmed yet!");
            return;
        }

        application.toPaymentDetailPage(booking);
    }

    private void onItemSelected() {
        int[] selectedRows = bookingTable.getSelectedRows();
        if (selectedRows.length == 1) {
            openReceiptButton.setEnabled(true);
            return;
        }

        openReceiptButton.setEnabled(false);
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        bookingTableModel = new CustomerBookingTableUI();
        bookingTable = new JTable(bookingTableModel);
    }

    @Override
    public void refreshData() {
        bookingTable.clearSelection();
        bookingTableModel.refreshData(bookingFunctions.getAllBookingsByCustomer(getCurrentUserName()));

        onItemSelected();
    }
}
