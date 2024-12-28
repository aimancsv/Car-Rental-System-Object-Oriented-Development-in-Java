package main.UI.tableUI;

import main.model.booking.Booking;

public class BookingTableUI extends ListTableUI<Booking> {
    /** This class is to return table of booking details*/
    private static final String[] COLUMNS = {"Booking Id", "Customer Name", "Car", "Booking Date", "Start Date", "End Date", "Total Price", "Status"};
    private static final ObjectAdapter<Booking> ADAPTER = (obj, idx) -> {
        switch (idx) {
            case 0:
                return obj.getBookingId();
            case 1:
                return obj.getCustomer();
            case 2:
                return obj.getCarPlate();
            case 3:
                return obj.getTodayDate();
            case 4:
                return obj.getStartDate();
            case 5:
                return obj.getEndDate();
            case 6:
                return obj.getTotalCost();
            case 7:
                return obj.getStatus().name();
            default:
                throw new IllegalArgumentException("Exceed column size");
        }
    };

    public BookingTableUI() {
        super(COLUMNS, ADAPTER);
    }
}
