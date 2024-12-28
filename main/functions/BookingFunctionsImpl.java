package main.functions;

import main.database.BookingRepo;
import main.model.booking.Booking;
import main.model.booking.BookingCondition;
import main.utils.GeneratePk;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookingFunctionsImpl implements BookingFunctions {
    private final BookingRepo bookingRepo;

    public BookingFunctionsImpl(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public void makeBooking(Booking booking) {
        GeneratePk.generateSerialIfNew(booking, bookingRepo);
        bookingRepo.insert(booking);
    }

    @Override
    public List<Booking> getBookingsWithinDates(LocalDate startDate, LocalDate endDate) {
        return bookingRepo.getBookingBetween(startDate, endDate);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.getLines()
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getAllBookingsByCustomer(String customerName) {
        return bookingRepo.getLines()
                .filter(booking -> booking.getCustomer().equals(customerName))
                .collect(Collectors.toList());
    }

    @Override
    public void paymentDone(Integer bookingId) {
        Booking booking = bookingRepo.getByPrimaryKey(bookingId);
        booking.setStatus(BookingCondition.CONFIRMED);
        bookingRepo.update(booking);
    }

    @Override
    public List<Booking> getAllBookingsForMonth(LocalDate date) {
        return getBookingsWithinDates(date.withDayOfMonth(0), date.withDayOfMonth(date.getDayOfMonth()));
    }

    @Override
    public Booking getById(Integer bookingId) {
        return bookingRepo.getByPrimaryKey(bookingId);
    }
}
