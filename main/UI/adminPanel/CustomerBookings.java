package main.UI.adminPanel;

import main.Application;
import main.model.booking.Booking;
import main.model.booking.BookingCondition;
import main.model.booking.Payment;
import main.model.account.Customer;
import main.functions.BookingFunctions;
import main.functions.CustomerFunctions;
import main.functions.PaymentFunctions;
import main.UI.BasePage;
import main.UI.tableUI.BookingTableUI;
import main.utils.PromptSwing;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBookings extends BasePage {
    private JTable bookingTable;
    private JComboBox<String> customerComboBox;
    private JButton backButton;
    private JButton confirmBookingButton;
    private JPanel mainPanel;
    private JButton openReceiptButton;
    private BookingTableUI bookingTableUI;

    private final BookingFunctions bookingFunctions;
    private final PaymentFunctions paymentFunctions;
    private final CustomerFunctions customerFunctions;
    private DefaultComboBoxModel<String> comboBoxModel;

    public CustomerBookings(Application application, BookingFunctions bookingFunctions, CustomerFunctions customerFunctions, PaymentFunctions paymentFunctions) {
        super(application);
        this.bookingFunctions = bookingFunctions;
        this.customerFunctions = customerFunctions;
        this.paymentFunctions = paymentFunctions;

        this.backButton.addActionListener(this::backToPreviousPage);
        this.openReceiptButton.addActionListener(e -> openReceipt());
        confirmBookingButton.addActionListener(e -> makePayment());
        bookingTable.getSelectionModel().addListSelectionListener(e -> onItemSelected());
        customerComboBox.addActionListener(e -> filterByCustomer());
    }

    /** This function is to open receipt */
    private void openReceipt() {
        Booking selectedBooking = getSelectedBooking();
        if (selectedBooking.getStatus().equals(BookingCondition.UNCONFIRMED)) {
            PromptSwing.promptMessageError("Booking is not confirmed yet!");
            return;
        }

        application.toPaymentDetailPage(selectedBooking);
    }

    /** This function is to confirmed booking
     */
    private void makePayment() {
        Booking selectedBooking = getSelectedBooking();
        if (selectedBooking.getStatus().equals(BookingCondition.CONFIRMED)) {
            PromptSwing.promptMessageError("Booking is already confirmed!");
            return;
        }

        PromptSwing.promptMessageInfo("Booking confirmed!");
        bookingFunctions.paymentDone(selectedBooking.getBookingId());
        paymentFunctions.makePayment(Payment.initialize(selectedBooking.getBookingId(), selectedBooking.getTotalCost()));
        refreshData();
    }

    /** This function is to return table of the booking*/
    private Booking getSelectedBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        Booking selectedBooking = bookingTableUI.getModelAt(selectedRow);
        return selectedBooking;
    }

    /** This function is to open receipt button when no row selected
     */
    private void onItemSelected() {
        int[] selectedRows = bookingTable.getSelectedRows();
        if (selectedRows.length != 1){
            confirmBookingButton.setEnabled(false);
            openReceiptButton.setEnabled(false);
            return;
        }

        confirmBookingButton.setEnabled(true);
        openReceiptButton.setEnabled(true);
    }

    /** This function is to filter customer
     */
    private void filterByCustomer() {
        String selectedCustomer = (String) customerComboBox.getSelectedItem();

        if ("All".equals(selectedCustomer)) {
            refreshTable(bookingFunctions.getAllBookings());
            return;
        }


        List<Booking> bookings = bookingFunctions.getAllBookingsByCustomer(selectedCustomer);
        refreshTable(bookings);
    }

    /** This function is to generate table
     */
    private void createUIComponents() {
        bookingTableUI = new BookingTableUI();
        bookingTable = new JTable(bookingTableUI);

        this.comboBoxModel = new DefaultComboBoxModel<>();
        customerComboBox = new JComboBox<>(comboBoxModel);
    }

    /** refresh data and table
      */
    @Override
    public void refreshData() {
        refreshTable(bookingFunctions.getAllBookings());

        comboBoxModel.removeAllElements();
        comboBoxModel.addElement("All");
        comboBoxModel.addAll(customerFunctions.getAllCustomers().stream().map(Customer::getUsername).collect(Collectors.toList()));
        comboBoxModel.setSelectedItem("All");
        customerComboBox.addNotify();
    }

    private void refreshTable(List<Booking> bookingService) {
        bookingTableUI.refreshData(bookingService);
        bookingTable.addNotify();
        bookingTable.clearSelection();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }
}
