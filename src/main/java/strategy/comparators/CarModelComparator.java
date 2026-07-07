package strategy.comparators;

import model.Car;

public class CarModelComparator implements ComparatorStrategy {
    @Override
    public int compare(Car o1, Car o2) {
        return o1.getModel().compareTo(o2.getModel());
    }

    @Override
    public boolean isEven(Car car) {
        return car.getModel().length()%2 == 0;
    }
}