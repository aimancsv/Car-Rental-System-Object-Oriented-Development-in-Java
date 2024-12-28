package main.UI.adminPanel;

import main.Application;
import main.functions.BookingFunctions;
import main.functions.PaymentFunctions;
import main.UI.BasePage;
import main.utils.DatePicker;
import main.utils.RingChart;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class MonthlyIncome extends BasePage {
    private ChartPanel chartPanel;
    private JDatePickerImpl monthPicker;
    private JButton BackButton;
    private JLabel totalField;
    private JPanel mainPanel;
    private DefaultPieDataset dataset;

    private final BookingFunctions bookingFunctions;
    private final PaymentFunctions paymentFunctions;

    public MonthlyIncome(Application application, BookingFunctions bookingFunctions, PaymentFunctions paymentFunctions) {
        super(application);
        this.bookingFunctions = bookingFunctions;
        this.paymentFunctions = paymentFunctions;

        BackButton.addActionListener(this::backToPreviousPage);
        monthPicker.addActionListener(e -> onDateChange());
    }

    /** To convert date change*/
    private void onDateChange() {
        Map<String, BigDecimal> carPlateRevenueMap = getCarPlateRevenueMap();
        populatePieChart(carPlateRevenueMap);
        BigDecimal total = carPlateRevenueMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        changeTotalRevenue(total);
    }

    /** This function is for mapping car plate*/
    private Map<String, BigDecimal> getCarPlateRevenueMap() {
        LocalDate date = DatePicker.getDateFromDatePicker(monthPicker);
        HashMap<String, BigDecimal> mapData = paymentFunctions.getAllPaymentsForMonth(date).stream()
                .map(payment -> bookingFunctions.getById(payment.getBookingId()))
                .collect(HashMap::new, (map, booking) -> {
                    if (map.containsKey(booking.getCarPlate()))
                        map.replace(booking.getCarPlate(), map.get(booking.getCarPlate()).add(booking.getTotalCost()));
                    map.putIfAbsent(booking.getCarPlate(), booking.getTotalCost());
                }, HashMap::putAll);
        return mapData;
    }

    /** add Ring Chart*/
    private void populatePieChart(Map<String, BigDecimal> mapData ) {
        dataset.clear();
        mapData.forEach(dataset::setValue);
        chartPanel.addNotify();
    }

    /** set total Income*/
    private void changeTotalRevenue(BigDecimal total) {
        totalField.setText(String.format("Total Income: RM%s", total.setScale(2, RoundingMode.HALF_UP)));
    }

    private void createUIComponents() {
        monthPicker = DatePicker.createNewDatePicker();
        dataset = new DefaultPieDataset();
        chartPanel = new ChartPanel(RingChart.generateRingChart(dataset));
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        LocalDate today = LocalDate.now();
        DatePicker.setDatePickerSelectedDate(monthPicker, today);
        onDateChange();
    }
}
