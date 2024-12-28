package main.functions;

import main.model.car.Car;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CarFunctions {
    void addCar(Car car);
    /**
     * This Function is to return all car list
     */
    List<Car> getAllCars();

    /**
     * This Function is to return car list within start date , end date
     */
    List<Car> getCarsWithinDates(LocalDate startDate, LocalDate endDate);

    /**
     * This Function is to return car plate
     */
    Car getByCarPlate(String carPlate);

    /**
     * This Function is to filter car plate with unbooked
     */
    List<Car> getAllCarsExcept(Collection<String> bookedCarPlate);

    void updateCar(Car car);

    Optional<Car> findByCarPlate(String text);

    void removeCar(Car car);
}
