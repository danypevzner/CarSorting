package strategy.sorting;

import model.Car;
import strategy.comparators.ComparatorStrategy;

import java.util.List;

public interface SortStrategy {
    void sort(List<Car> cars, ComparatorStrategy comparator);
}