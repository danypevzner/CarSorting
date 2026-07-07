package strategy;

import model.Car;

import java.util.List;

public class SortModel implements SortStrategy {
    @Override
    public void sort(List<Car> cars) {
        cars.sort(new Car.CarModelComparator());
    }
}
