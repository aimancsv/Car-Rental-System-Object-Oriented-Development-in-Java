package main.UI.customerPanel;

import main.Application;
import main.model.booking.Booking;
import main.model.car.Car;
import main.functions.BookingFunctions;
import main.functions.CarFunctions;
import main.exception.ValidationException;
import main.UI.BasePage;
import main.UI.tableUI.CarTableUI;
import main.utils.DatePicker;
import main.utils.PromptSwing;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class CarBooking extends BasePage {
    private JPanel mainPanel;
    private JButton backButton;
    private JButton makePaymentButton;
    private JTable carTable;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    private JButton searchButton;
    private JLabel totalPriceField;

    private CarTableUI carTableUI;

    private BookingState bookingState;

    private CarFunctions carFunctions;
    private BookingFunctions bookingFunctions;

    public CarBooking(Application application, CarFunctions carFunctions, BookingFunctions bookingRepository) {
        super(application);
        this.carFunctions = carFunctions;
        this.bookingFunctions = bookingRepository;

        backButton.addActionListener(this::backToPreviousPage);
        searchButton.addActionListener(this::searchValidCarList);
        makePaymentButton.addActionListener(this::bookCar);

        carTable.getSelectionModel().addListSelectionListener(e -> onItemSelected());
    }

    private void bookCar(ActionEvent event) {
        List<Car> selectedCars = getSelectedCars();

        LocalDate startDate = DatePicker.getDateFromDatePicker(startDatePicker);
        LocalDate endDate = DatePicker.getDateFromDatePicker(endDatePicker);

        selectedCars.stream()
                .map(car -> Booking.initializeBooking(
                        application.getSessionContainer().getSession().getCurrentUser().getUsername(),
                        car.getCarPlate(), startDate, endDate, getTotalPrice()))
                .forEach(bookingFunctions::makeBooking);

        PromptSwing.promptMessageInfo("Payment has been made, Wait for the booking confirmation!");
        refreshData();
    }

    private void onItemSelected() {
        List<Car> selectedCars = getSelectedCars();

        if (bookingState.equals(BookingState.DISALLOWED))
            return;

        LocalDate startDate = DatePicker.getDateFromDatePicker(startDatePicker);
        LocalDate endDate = DatePicker.getDateFromDatePicker(endDatePicker);
        /* getting the days including the rental end date
        */
        long days = DAYS.between(startDate, endDate) + 1;

        BigDecimal unitPrice = selectedCars.stream().map(Car::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_UP);
        setTotalPrice(totalPrice);
    }

    private List<Car> getSelectedCars() {
        int[] selectedRows = carTable.getSelectedRows();
        List<Car> selectedCars = carTableUI.getModelAt(selectedRows);
        return selectedCars;
    }

    private void setBookingState(BookingState state) {
        bookingState = state;
        state.onStatusChange(this);
    }

    private void searchValidCarList(ActionEvent event) {
        LocalDate startDate = DatePicker.getDateFromDatePicker(startDatePicker);
        LocalDate endDate = DatePicker.getDateFromDatePicker(endDatePicker);

        try {
            if (startDate == null || endDate == null)
                throw new ValidationException("Please enter Start date and end date!");

            if (startDate.compareTo(LocalDate.now()) < 0)
                throw new ValidationException("Date of start must be earlier than today's date!");

            if (startDate.compareTo(endDate) > 0) {
                throw new ValidationException("The Start date must be earlier than end date!");
            }


        } catch (ValidationException e) {
            PromptSwing.promptMessageError(e.getMessage());
            return;
        }

        filterBookedCar(bookingFunctions.getBookingsWithinDates(startDate, endDate));
        setBookingState(BookingState.ALLOWED);
        carTable.clearSelection();
    }

    private void filterBookedCar(List<Booking> bookingBetween) {
        Set<String> bookedCarPlate = bookingBetween.stream().map(Booking::getCarPlate).collect(Collectors.toSet());

        List<Car> availableCarList = carFunctions.getAllCarsExcept(bookedCarPlate);

        carTableUI.refreshData(availableCarList);
        carTable.addNotify();
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        startDatePicker = DatePicker.createNewDatePicker();
        endDatePicker = DatePicker.createNewDatePicker();
        carTableUI = new CarTableUI();
        carTable = new JTable(carTableUI);
    }

    @Override
    public void refreshData() {
        setBookingState(BookingState.DISALLOWED);
        DatePicker.refreshDatePicker(startDatePicker);
        DatePicker.refreshDatePicker(endDatePicker);
        setTotalPrice(BigDecimal.ZERO);
        carTable.clearSelection();
        carTableUI.refreshData(carFunctions.getAllCars());
        carTable.addNotify();
    }

    private void setTotalPrice(BigDecimal price) {
        totalPriceField.setText(String.format("Total: RM %s", price.setScale(2, RoundingMode.HALF_UP)));
    }

    private BigDecimal getTotalPrice() {
        return new BigDecimal(totalPriceField.getText().substring(10));
    }

    private enum BookingState {
        DISALLOWED(false),
        ALLOWED(true);

        private final boolean bookingAllowed;

        BookingState(boolean bookingAllowed) {
            this.bookingAllowed = bookingAllowed;
        }

        public void onStatusChange(CarBooking page) {
            page.makePaymentButton.setEnabled(bookingAllowed);
        }
    }
}
