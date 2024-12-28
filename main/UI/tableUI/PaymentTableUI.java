package main.UI.tableUI;

import main.model.booking.Payment;

public class PaymentTableUI extends ListTableUI<Payment> {
    /** This class is to return table of payment details*/
    private static final String[] COLUMNS = {"Payment Id", "Booking Id", "Payment Date", "Total Price"};
    private static final ObjectAdapter<Payment> ADAPTER = (obj, idx) -> {
        switch (idx) {
            case 0:
                return obj.getPaymentId();
            case 1:
                return obj.getBookingId();
            case 2:
                return obj.getPaymentDate();
            case 3:
                return obj.getPrice();
            default:
                throw new IllegalArgumentException("Exceed column size");
        }
    };

    public PaymentTableUI() {
        super(COLUMNS, ADAPTER);
    }
}
