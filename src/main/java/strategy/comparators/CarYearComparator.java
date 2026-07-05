package strategy.comparators;

import model.Car;

public class CarYearComparator implements ComparatorStrategy {
    @Override
    public int compare(Car o1, Car o2) {
        return Integer.compare(o1.getYear(), o2.getYear());
    }

    @Override
    public int getValue(Car car) {
        return car.getYear();
    }
}
