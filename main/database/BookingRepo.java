package main.database;

import main.model.booking.Booking;
import main.model.booking.BookingCondition;
import main.utils.Split;
import main.utils.DateFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookingRepo extends BaseRepo<Integer, Booking> {
    public BookingRepo(String fileName, Database database) {
        super(fileName, database);
    }

    /** This Function is to map from object to text*/
    @Override
    public String recordToString(Booking record) {
        return Split.defaultDbJoin(record.getPrimaryKey().toString(), record.getCustomer(), record.getCarPlate(),
                record.getStatus().name(), DateFormat.dateToString(record.getTodayDate()),
                DateFormat.dateToString(record.getStartDate()), DateFormat.dateToString(record.getEndDate()), record.getTotalCost().toString());
    }

    /** This Function is to convert text to object*/
    @Override
    public Booking stringToRecord(String str) {
        String[] strings = Split.defaultDbSplit(str);
        return Booking.of(Integer.parseInt(strings[0]), strings[1], strings[2], BookingCondition.valueOf(strings[3]),
                DateFormat.stringToDate(strings[4]), DateFormat.stringToDate(strings[5]),
                DateFormat.stringToDate(strings[6]), new BigDecimal(strings[7]));
    }


    /** get booking by filtering with start date and end date
     */
    public List<Booking> getBookingBetween(LocalDate startDate, LocalDate endDate) {
        return getLines()
                .filter(booking -> DateFormat.isOverlapped(booking.getStartDate(), booking.getEndDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
