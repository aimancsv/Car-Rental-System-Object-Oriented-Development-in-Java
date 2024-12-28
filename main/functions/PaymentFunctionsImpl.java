package main.functions;

import main.database.PaymentRepo;
import main.model.booking.Payment;
import main.utils.GeneratePk;

import java.time.LocalDate;
import java.util.List;

public class PaymentFunctionsImpl implements PaymentFunctions {
    private final PaymentRepo paymentRepo;

    public PaymentFunctionsImpl(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public void makePayment(Payment payment) {
        GeneratePk.generateSerialIfNew(payment, paymentRepo);
        paymentRepo.insert(payment);
    }

    @Override
    public List<Payment> getPaymentsByDate(LocalDate selectedDate) {
        return paymentRepo.getPaymentsByDate(selectedDate);
    }

    @Override
    public List<Payment> getAllPaymentsForMonth(LocalDate today) {
        if(today == null) return List.of();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.plusMonths(1).withDayOfMonth(1).minusDays(1);
        return paymentRepo.getPaymentBetween(start, end);
    }
}
