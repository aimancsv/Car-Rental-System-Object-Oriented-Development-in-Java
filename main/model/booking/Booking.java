package main.model.booking;

import main.database.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Booking implements Model<Integer> {
    private Integer bookingId;
    private String customer;
    private String carPlate;
    private LocalDate todayDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingCondition status;
    private BigDecimal totalCost;

    private Booking(Integer bookingId, String customer, String carPlate,
                     LocalDate todayDate, LocalDate startDate, LocalDate endDate, BookingCondition status, BigDecimal totalCost) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.carPlate = carPlate;
        this.todayDate = todayDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.totalCost = totalCost;
    }

    @Override
    public Integer getPrimaryKey() {
        return bookingId;
    }

    @Override
    public void setPrimaryKey(Integer pk) {
        bookingId = pk;
    }

    public static Booking initializeBooking(String customer, String carPlate, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice) {
        return new Booking(null, customer, carPlate,  LocalDate.now(), startDate, endDate, BookingCondition.UNCONFIRMED, totalPrice);
    }

    public static Booking of(int bookingId, String customer, String carPlate,
                             BookingCondition bookingCondition, LocalDate created, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice) {
        return new Booking(bookingId, customer, carPlate,  created, startDate, endDate, bookingCondition, totalPrice);
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCarPlate() {
        return carPlate;
    }
    public LocalDate getTodayDate() {
        return todayDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public BookingCondition getStatus() {
        return status;
    }
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public void setTodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setStatus(BookingCondition status) {
        this.status = status;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
