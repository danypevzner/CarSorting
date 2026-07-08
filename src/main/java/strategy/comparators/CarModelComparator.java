package strategy.comparators;

import model.Car;

public class CarModelComparator implements ComparatorStrategy {
    @Override
    public int compare(Car o1, Car o2) {
        return o1.model().compareTo(o2.model());
    }

    @Override
    public boolean isEven(Car car) {
        return car.model().length()%2 == 0;
    }
}