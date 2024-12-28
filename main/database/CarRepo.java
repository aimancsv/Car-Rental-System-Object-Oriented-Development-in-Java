package main.database;

import main.model.car.Car;
import main.model.car.Transmission;
import main.utils.Split;

import java.math.BigDecimal;

public class CarRepo extends BaseRepo<String, Car> {
    public CarRepo(String fileName, Database database) {
        super(fileName, database);
    }

    /** This Function is to map object to text*/
    @Override
    public String recordToString(Car record) {
        return Split.defaultDbJoin(record.getCarPlate(), record.getBrand(), record.getModel(),
                String.valueOf(record.getSeats()), record.getPrice().toString(), record.getTransmission().name());
    }

    /** This Function is to convert text to object*/
    @Override
    public Car stringToRecord(String str) {
        String[] strings = Split.defaultDbSplit(str);
        return new Car(strings[0], strings[1], strings[2], Integer.parseInt(strings[3]),
                new BigDecimal(strings[4]), Transmission.valueOf(strings[5]));
    }
}
