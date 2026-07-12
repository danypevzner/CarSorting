package collection;

import java.util.ArrayList;

import core.CarField;
import core.Sorting;
import model.Car;
import strategy.comparators.CarModelComparator;
import strategy.comparators.CarPowerComparator;
import strategy.comparators.CarYearComparator;
import strategy.comparators.ComparatorStrategy;
import strategy.sorting.BubbleSortStrategy;
import strategy.sorting.EvenSortingDecorator;
import strategy.sorting.InsertionSortStrategy;
import strategy.sorting.SelectionSortStrategy;
import strategy.sorting.SortStrategy;

public class CarCollection extends ArrayList<Car> {

    public void sortBy(CarField field, Sorting sorting) {
        SortStrategy strategy = getSortingStrategy(sorting);
        ComparatorStrategy comparator = getComparator(field);

        strategy.sort(this, comparator);
    }

    public void sortEvenlyBy(CarField field, Sorting sorting) {
        SortStrategy strategy = new EvenSortingDecorator(getSortingStrategy(sorting));
        ComparatorStrategy comparator = getComparator(field);

        strategy.sort(this, comparator);
    }

    public long count(Car car) {
        return this.parallelStream().filter(car::equals).count();
    }

    private SortStrategy getSortingStrategy(Sorting sorting) {
        return switch (sorting) {
            case BUBBLE -> new BubbleSortStrategy();
            case INSERTION -> new InsertionSortStrategy();
            case SELECTION -> new SelectionSortStrategy();
        };
    }

    private ComparatorStrategy getComparator(CarField field) {
        return switch (field) {
            case MODEL -> new CarModelComparator();
            case POWER -> new CarPowerComparator();
            case YEAR -> new CarYearComparator();
        };
    }

    public void removeAllCars() {
        this.clear();
    }

    public boolean removeCar(Car car) {
        return this.remove(car);
    }
}