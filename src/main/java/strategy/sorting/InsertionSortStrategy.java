package strategy.sorting;

import model.Car;
import strategy.comparators.ComparatorStrategy;

import java.util.List;

public class InsertionSortStrategy implements SortStrategy{
    @Override
    public void sort(List<Car> cars, ComparatorStrategy comparator) {
        int size = cars.size();

        for (int i = 1; i < size; i++) {
            Car current = cars.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(cars.get(j), current) > 0) {
                cars.set(j + 1, cars.get(j));
                j--;
            }
            cars.set(j + 1, current);
        }
    }
}
