package strategy.sorting;

import model.Car;
import strategy.comparators.ComparatorStrategy;

import java.util.List;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Car> cars, ComparatorStrategy comparator) {
        int size = cars.size();

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (comparator.compare(cars.get(j), cars.get(j + 1)) > 0) {
                    Car temp = cars.get(j);
                    cars.set(j, cars.get(j + 1));
                    cars.set(j + 1, temp);
                }
            }
        }
    }
}