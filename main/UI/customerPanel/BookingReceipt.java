package main.UI.customerPanel;

import main.Application;
import main.model.booking.Booking;
import main.model.car.Car;
import main.model.account.Customer;
import main.functions.CarFunctions;
import main.functions.CustomerFunctions;
import main.utils.DateFormat;
import main.UI.BasePage;

import javax.swing.*;

public class BookingReceipt extends BasePage {
    private JPanel mainPanel;
    private JTextField emailField;
    private JTextField carPlateField;
    private JTextField carBrandField;
    private JTextField carModelField;
    private JTextField carSeatsField;
    private JTextField carTransmissionField;
    private JButton BackButton;
    private JTextField bookingIdField;
    private JTextField bookingDateField;
    private JTextField bookingStartDateField;
    private JTextField bookingEndDateField;
    private JTextField totalPriceField;

    private final CustomerFunctions customerFunctions;
    private final CarFunctions carFunctions;

    public BookingReceipt(Application application, CustomerFunctions customerFunctions, CarFunctions carFunctions) {
        super(application);
        this.customerFunctions = customerFunctions;
        this.carFunctions = carFunctions;

        BackButton.addActionListener(this::backToPreviousPage);
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    /** display in Booking Receipt*/
    public void initializeData(Booking booking) {
        Customer customer = customerFunctions.getByName(booking.getCustomer());
        Car car = carFunctions.getByCarPlate(booking.getCarPlate());

        emailField.setText(customer.getEmail());

        carPlateField.setText(car.getCarPlate());
        carBrandField.setText(car.getBrand());
        carModelField.setText(car.getModel());
        carSeatsField.setText(car.getSeats().toString());
        carTransmissionField.setText(car.getTransmission().name());


        bookingIdField.setText(booking.getBookingId().toString());
        bookingDateField.setText(DateFormat.dateToString(booking.getTodayDate()));
        bookingStartDateField.setText(DateFormat.dateToString(booking.getStartDate()));
        bookingEndDateField.setText(DateFormat.dateToString(booking.getEndDate()));
        totalPriceField.setText(booking.getTotalCost().toString());
    }
}
