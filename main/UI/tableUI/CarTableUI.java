package main.UI.tableUI;

import main.model.car.Car;

public class CarTableUI extends ListTableUI<Car> {
    /** This class is to return table of car */
    private static final String[] COLUMN_NAMES = {"Plate No.", "Brand", "Model", "Seats", "Transmission", "Price"};
    private static final ObjectAdapter<Car> ADAPTER = (obj, idx) -> {
        switch (idx) {
            case 0:
                return obj.getCarPlate();
            case 1:
                return obj.getBrand();
            case 2:
                return obj.getModel();
            case 3:
                return obj.getSeats();
            case 4:
                return obj.getTransmission().name();
            case 5:
                return obj.getPrice().toString();
            default:
                throw new IllegalArgumentException("Exceed column size");
        }
    };

    public CarTableUI() {
        super(COLUMN_NAMES, ADAPTER);
    }
}
