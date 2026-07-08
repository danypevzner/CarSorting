package strategy.comparators;

import model.Car;

public class CarYearComparator implements ComparatorStrategy {
    @Override
    public int compare(Car o1, Car o2) {
        return Integer.compare(o1.yearOfProduction(), o2.yearOfProduction());
    }

    @Override
    public boolean isEven(Car car) {
        return car.yearOfProduction() % 2 == 0;
    }
}
