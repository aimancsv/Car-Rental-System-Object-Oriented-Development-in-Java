package main.database;

import main.model.booking.Payment;
import main.utils.Split;
import main.utils.DateFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentRepo extends BaseRepo<Integer, Payment> {
    public PaymentRepo(String fileName, Database database) {
        super(fileName, database);
    }

    @Override
    public String recordToString(Payment record) {
        return Split.defaultDbJoin(record.getPaymentId().toString(), record.getBookingId().toString(),
                DateFormat.dateToString(record.getPaymentDate()), record.getPrice().toString());
    }

    @Override
    public Payment stringToRecord(String str) {
        String[] strings = Split.defaultDbSplit(str);
        return Payment.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]),
                DateFormat.stringToDate(strings[2]), new BigDecimal(strings[3]));
    }

    /** This Function is to filter payment by date*/
    public List<Payment> getPaymentsByDate(LocalDate selectedDate) {
        return getLines()
                .filter(payment -> payment.getPaymentDate().equals(selectedDate))
                .collect(Collectors.toList());
    }

    /** This Function is to filter payment between start date and end date*/
    public List<Payment> getPaymentBetween(LocalDate startDate, LocalDate endDate) {
        return getLines()
                .filter(payment -> DateFormat.isBetween(payment.getPaymentDate(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
