package main.functions;

import main.model.booking.Payment;

import java.time.LocalDate;
import java.util.List;

public interface PaymentFunctions {
    void makePayment(Payment initialize);

    /**
     * This Function is to return list of payments
     */
    List<Payment> getPaymentsByDate(LocalDate selectedDate);

    List<Payment> getAllPaymentsForMonth(LocalDate today);
}
