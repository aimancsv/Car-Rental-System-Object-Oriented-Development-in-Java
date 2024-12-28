package main.functions;

import main.database.CarRepo;
import main.model.car.Car;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarFunctionsImpl implements CarFunctions {
    private final CarRepo carRepo;

    public CarFunctionsImpl(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public void addCar(Car car) {
        car.setCarPlate(car.getCarPlate().toLowerCase());
        carRepo.insert(car);
    }

    @Override
    public Optional<Car> findByCarPlate(String carPlate) {
        return carRepo.findByPrimaryKey(carPlate.toLowerCase());
    }

    @Override
    public void removeCar(Car car) {
        carRepo.remove(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepo.getLines().collect(Collectors.toList());
    }

    @Override
    public List<Car> getCarsWithinDates(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Car getByCarPlate(String carPlate) {
        return carRepo.getByPrimaryKey(carPlate);
    }

    @Override
    public List<Car> getAllCarsExcept(Collection<String> bookedCarPlate) {
        return carRepo.getLines()
                .filter(car -> !bookedCarPlate.contains(car.getCarPlate()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateCar(Car car) {
        carRepo.update(car);
    }
}
