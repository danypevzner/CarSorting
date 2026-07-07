package strategy;

import model.Car;

import java.util.List;

public class SortPower implements SortStrategy {
    @Override
    public void sort(List<Car> cars) {
        cars.sort(new Car.CarPowerComparator());
    }
}
