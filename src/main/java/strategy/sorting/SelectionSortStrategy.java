package strategy.sorting;

import model.Car;
import strategy.comparators.ComparatorStrategy;

import java.util.List;

public class SelectionSortStrategy implements SortStrategy{
    @Override
    public void sort(List<Car> cars, ComparatorStrategy comparator) {
        int size = cars.size();

        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < size; j++) {
                if (comparator.compare(cars.get(j), cars.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Car temp = cars.get(i);
                cars.set(i, cars.get(minIndex));
                cars.set(minIndex, temp);
            }
        }
    }
}
