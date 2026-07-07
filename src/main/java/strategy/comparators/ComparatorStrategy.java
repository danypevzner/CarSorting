package strategy.comparators;

import model.Car;

import java.util.Comparator;

public interface ComparatorStrategy extends Comparator<Car> {
    boolean isEven(Car car);
}