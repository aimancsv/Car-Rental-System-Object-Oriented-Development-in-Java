package main.functions;

import main.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingFunctions {
    void makeBooking(Booking booking);
    /** This Function is to return all bookings within the start date & end date
     */
    List<Booking> getBookingsWithinDates(LocalDate startDate, LocalDate endDate);

    /**
     * This Function is to return all booking list
     */
    List<Booking> getAllBookings();

    /**
     * This Function is to return all booking list by filter customers name
     */
    List<Booking> getAllBookingsByCustomer(String customerName);

    void paymentDone(Integer bookingId);

    List<Booking> getAllBookingsForMonth(LocalDate day);

    Booking getById(Integer bookingId);
}
