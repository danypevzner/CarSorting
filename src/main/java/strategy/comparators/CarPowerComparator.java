package strategy.comparators;

import model.Car;

public class CarPowerComparator implements ComparatorStrategy {
    @Override
    public int compare(Car o1, Car o2) {
        return Double.compare(o1.getPower(), o2.getPower());
    }

    @Override
    public int getValue(Car car) {
        return (int) car.getPower();
    }
}
