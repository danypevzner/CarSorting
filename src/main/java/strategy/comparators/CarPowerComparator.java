package strategy.comparators;

import model.Car;

public class CarPowerComparator implements ComparatorStrategy {
    @Override
    public int compare(Car o1, Car o2) {
        return Double.compare(o1.power(), o2.power());
    }

    @Override
    public boolean isEven(Car car) {
        return car.power()%2 == 0;
    }
}
