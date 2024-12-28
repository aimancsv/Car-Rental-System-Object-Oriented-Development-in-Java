package main;

import main.database.*;
import main.model.booking.Booking;
import main.model.car.Car;
import main.model.account.Admin;
import main.model.account.Customer;
import main.functions.*;
import main.session.SessionContainer;
import main.session.SessionContainerImpl;
import main.UI.*;
import main.UI.adminPanel.*;
import main.UI.customerPanel.*;

import javax.swing.*;
import java.util.Optional;

public class Application {

    private AdminRepo adminRepo;

    private CustomerFunctions customerFunctions;
    private AdminFunctions adminFunctions;
    private CarFunctions carFunctions;
    private BookingFunctions bookingFunctions;
    private PaymentFunctions paymentFunctions;
    private LoginFunctions loginFunctions;
    private JFrame jFrame;
    private LoginForm loginForm;
    private CustomerAccount customerRegisterPage;
    private CustomerAccount customerEditPage;
    private AdminMainPanel adminMainPanel;
    private CustomerPanel customerPanel;
    private CarManagement carManagement;
    private CarDetails addCarDetails;
    private CarDetails editCarDetails;
    private CarBooking bookCarPage;
    private BookingHistory bookingHistory;
    private CustomerBookings customerBookings;
    private BookingReceipt bookingReceipt;
    private DailyReport dailyReport;
    private MonthlyIncome monthlyCarRevenuePage;

    private SessionContainer sessionContainer = new SessionContainerImpl();
    private Optional<BasePage> currentPage = Optional.empty();

    public static void main(String[] args) {
        new Application();
    }


    public Application() {
        initializeDatabase();
        initializeUI();
        seedDataIfNew();
        initJFrame();
        toLogin();
    }


    private void initializeUI() {

        loginForm = new LoginForm(this, loginFunctions);

        customerRegisterPage = new CustomerAccount(this, customerFunctions::registerCustomer, true);

        adminMainPanel = new AdminMainPanel(this);

        carManagement = new CarManagement(this, carFunctions);

        addCarDetails = new CarDetails(this, carFunctions, CarDetails.Mode.ADD);

        editCarDetails = new CarDetails(this, carFunctions, CarDetails.Mode.EDIT);

        customerPanel = new CustomerPanel(this);

        bookCarPage = new CarBooking(this, carFunctions, bookingFunctions);

        bookingHistory = new BookingHistory(this, bookingFunctions);

        customerBookings = new CustomerBookings(this, bookingFunctions, customerFunctions, paymentFunctions);

        bookingReceipt = new BookingReceipt(this, customerFunctions, carFunctions);

        dailyReport = new DailyReport(this, paymentFunctions);

        monthlyCarRevenuePage = new MonthlyIncome(this, bookingFunctions, paymentFunctions);

        customerEditPage = new CustomerAccount(this, customerFunctions::updateCustomer, false);
    }

    // direct to login page
    public void toLogin() {
        toPage(loginForm);
    }

    // direct to car menu
    public void toCarMenu() {
        toPage(carManagement);
    }

    // direct to add car
    public void toAddCar() {
        toPage(addCarDetails);
    }

    // direct to booking history
    public void toCustomerBookingHistory() {
        toPage(bookingHistory);
    }

    // direct to booking page
    public void toAdminBookingPage() {
        toPage(customerBookings);
    }

    public void toPaymentDetailPage(Booking booking) {
        toPage(bookingReceipt);
        bookingReceipt.initializeData(booking);
    }

    // direct to daily sales report page
    public void toDailySalesReportPage() {
        toPage(dailyReport);
    }

    public void toPage(BasePage page) {
        toPage(page, false);
    }

    public void backToPage(BasePage page) {
        toPage(page, true);
    }

    // direct from page to page
    public void toPage(BasePage page, boolean isBack) {
        currentPage.ifPresent(BasePage::close);
        if (!isBack)
            page.setPreviousPage(currentPage.orElse(null));
        currentPage = Optional.of(page);
        jFrame.setContentPane(page.getPanel());
        page.open();
        jFrame.setVisible(true);
    }

    private void initJFrame() {
        jFrame = new JFrame("Car Rental System");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800,600);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
    }

    // check for admin user first
    private void seedDataIfNew() {
        if (adminRepo.getTotalRecord() == 0) {
            Admin admin = new Admin("admin", "adm123");
            adminRepo.insert(admin);
        }
    }

    private void initializeDatabase() {
        Database database = new Database("Project_Database");
        adminRepo = new AdminRepo("Admin.txt", database);
        CustomerRepo customerRepository = new CustomerRepo("Customer_Details.txt", database);
        CarRepo carRepository = new CarRepo("Car_Details.txt", database);
        BookingRepo bookingRepository = new BookingRepo("Booking_details.txt", database);
        PaymentRepo paymentRepository = new PaymentRepo("Payment_Details.txt", database);

        bookingFunctions = new BookingFunctionsImpl(bookingRepository);
        paymentFunctions = new PaymentFunctionsImpl(paymentRepository);
        carFunctions = new CarFunctionsImpl(carRepository);
        customerFunctions = new CustomerFunctionsImpl(customerRepository, adminRepo);
        adminFunctions = new AdminFunctionsImpl(adminRepo);
        loginFunctions = new LoginFunctionsImpl(adminRepo, customerRepository);
    }

    // middle layer validation for users
    public SessionContainer getSessionContainer() {
        return sessionContainer;
    }

    public void toEditCar(Car car) {
        toPage(editCarDetails);
        editCarDetails.initializeCarPage(car);
    }
/*
* functions diverting to different pages
*/
    public void toBookCar() {
        toPage(bookCarPage);
    }

    public void toCarMonthlyRevenueReportPage() {
        toPage(monthlyCarRevenuePage);
    }

    public void toCustomerRegister() {
        toPage(customerRegisterPage);
    }

    public void toAdminMenu() {
        toPage(adminMainPanel);
    }

    public void toCustomerMenu() {
        toPage(customerPanel);
    }

    public void toCustomerEdit(Customer customer) {
        toPage(customerEditPage);
        customerEditPage.initializeData(customer);
    }
}
