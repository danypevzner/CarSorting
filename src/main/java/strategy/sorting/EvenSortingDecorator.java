package strategy.sorting;

import model.Car;
import strategy.comparators.ComparatorStrategy;

import java.util.ArrayList;
import java.util.List;

public class EvenSortingDecorator implements SortStrategy{
    private final SortStrategy strategy;

    public EvenSortingDecorator(SortStrategy strategy){
        this.strategy = strategy;
    }

    @Override
    public void sort(List<Car> cars, ComparatorStrategy comparator) {
        List<Car> evenCars = new ArrayList<>();

        // Собираем только четные
        for (Car car : cars) {
            if (comparator.isEven(car)) {
                evenCars.add(car);
            }
        }

        strategy.sort(evenCars, comparator);

        // Возвращаем обратно
        int evenIndex = 0;

        for (int i = 0; i < cars.size(); i++) {
            if (comparator.isEven(cars.get(i))) {
                cars.set(i, evenCars.get(evenIndex++));
            }

        }
    }
}
