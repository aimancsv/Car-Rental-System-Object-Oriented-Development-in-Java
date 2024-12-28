package main.UI.tableUI;

import main.model.booking.Booking;

public class CustomerBookingTableUI extends ListTableUI<Booking> {
    /** This class is to return table of customers booking*/
    private static final String[] COLUMNS = {"Booking Id", "Car", "Booking Date", "Start Date", "End Date", "Total Price", "Status"};
    private static final ObjectAdapter<Booking> ADAPTER = (obj, idx) -> {
        switch (idx) {
            case 0:
                return obj.getBookingId();
            case 1:
                return obj.getCarPlate();
            case 2:
                return obj.getTodayDate();
            case 3:
                return obj.getStartDate();
            case 4:
                return obj.getEndDate();
            case 5:
                return obj.getTotalCost();
            case 6:
                return obj.getStatus().name();
            default:
                throw new IllegalArgumentException("Exceed column size");
        }
    };

    public CustomerBookingTableUI() {
        super(COLUMNS, ADAPTER);
    }
}
