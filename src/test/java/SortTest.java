import model.Car;
import org.testng.annotations.Test;
import strategy.comparators.CarModelComparator;
import strategy.comparators.CarPowerComparator;
import strategy.comparators.CarYearComparator;
import strategy.sorting.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SortTest {

    private List<Car> createCars() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("BMW", 201, 2020));
        cars.add(new Car("Audi", 180, 2019));
        cars.add(new Car("Mercedes", 300, 2023));

        return cars;
    }

    //================== Bubble ==================

    @Test
    public void testBubbleSortByModel() {
        List<Car> cars = createCars();

        SortStrategy strategy = new BubbleSortStrategy();
        strategy.sort(cars, new CarModelComparator());

        assertEquals(cars.get(0).getModel(), "Audi");
        assertEquals(cars.get(1).getModel(), "BMW");
        assertEquals(cars.get(2).getModel(), "Mercedes");
    }

    @Test
    public void testBubbleSortByPower() {
        List<Car> cars = createCars();

        SortStrategy strategy = new BubbleSortStrategy();
        strategy.sort(cars, new CarPowerComparator());

        assertEquals(cars.get(0).getPower(), 180);
        assertEquals(cars.get(1).getPower(), 201);
        assertEquals(cars.get(2).getPower(), 300);
    }

    @Test
    public void testBubbleSortByYear() {
        List<Car> cars = createCars();

        SortStrategy strategy = new BubbleSortStrategy();
        strategy.sort(cars, new CarYearComparator());

        assertEquals(cars.get(0).getYear(), 2019);
        assertEquals(cars.get(1).getYear(), 2020);
        assertEquals(cars.get(2).getYear(), 2023);
    }

    //================== Selection ==================

    @Test
    public void testSelectionSortByModel() {
        List<Car> cars = createCars();

        SortStrategy strategy = new SelectionSortStrategy();
        strategy.sort(cars, new CarModelComparator());

        assertEquals(cars.get(0).getModel(), "Audi");
        assertEquals(cars.get(1).getModel(), "BMW");
        assertEquals(cars.get(2).getModel(), "Mercedes");
    }

    @Test
    public void testSelectionSortByPower() {
        List<Car> cars = createCars();

        SortStrategy strategy = new SelectionSortStrategy();
        strategy.sort(cars, new CarPowerComparator());

        assertEquals(cars.get(0).getPower(), 180);
        assertEquals(cars.get(1).getPower(), 201);
        assertEquals(cars.get(2).getPower(), 300);
    }

    @Test
    public void testSelectionSortByYear() {
        List<Car> cars = createCars();

        SortStrategy strategy = new SelectionSortStrategy();
        strategy.sort(cars, new CarYearComparator());

        assertEquals(cars.get(0).getYear(), 2019);
        assertEquals(cars.get(1).getYear(), 2020);
        assertEquals(cars.get(2).getYear(), 2023);
    }

    //================== Insertion ==================

    @Test
    public void testInsertionSortByModel() {
        List<Car> cars = createCars();

        SortStrategy strategy = new InsertionSortStrategy();
        strategy.sort(cars, new CarModelComparator());

        assertEquals(cars.get(0).getModel(), "Audi");
        assertEquals(cars.get(1).getModel(), "BMW");
        assertEquals(cars.get(2).getModel(), "Mercedes");
    }

    @Test
    public void testInsertionSortByPower() {
        List<Car> cars = createCars();

        SortStrategy strategy = new InsertionSortStrategy();
        strategy.sort(cars, new CarPowerComparator());

        assertEquals(cars.get(0).getPower(), 180);
        assertEquals(cars.get(1).getPower(), 201);
        assertEquals(cars.get(2).getPower(), 300);
    }

    @Test
    public void testInsertionSortByYear() {
        List<Car> cars = createCars();

        SortStrategy strategy = new InsertionSortStrategy();
        strategy.sort(cars, new CarYearComparator());

        assertEquals(cars.get(0).getYear(), 2019);
        assertEquals(cars.get(1).getYear(), 2020);
        assertEquals(cars.get(2).getYear(), 2023);
    }

    //================== Дополнительное задание ==================

    @Test
    public void testEvenBubbleSortByPower() {

        List<Car> cars = new ArrayList<>();

        cars.add(new Car("BMW", 201, 2020));      // нечетная
        cars.add(new Car("Audi", 180, 2019));     // четная
        cars.add(new Car("Ford", 151, 2021));     // нечетная
        cars.add(new Car("Mercedes", 300, 2023)); // четная

        SortStrategy strategy =
                new EvenSortingDecorator(new BubbleSortStrategy());

        strategy.sort(cars, new CarPowerComparator());

        // Нечетные остаются на местах
        assertEquals(cars.get(0).getModel(), "BMW");
        assertEquals(cars.get(2).getModel(), "Ford");

        // Четные отсортированы между собой
        assertEquals(cars.get(1).getPower(), 180);
        assertEquals(cars.get(3).getPower(), 300);
    }

    @Test
    public void testEvenSelectionSortByYear() {

        List<Car> cars = new ArrayList<>();

        cars.add(new Car("BMW", 150, 2021));      // нечетный год
        cars.add(new Car("Audi", 180, 2020));     // четный
        cars.add(new Car("Ford", 160, 2019));     // нечетный
        cars.add(new Car("Mercedes", 300, 2018)); // четный

        SortStrategy strategy =
                new EvenSortingDecorator(new SelectionSortStrategy());

        strategy.sort(cars, new CarYearComparator());

        assertEquals(cars.get(0).getYear(), 2021);
        assertEquals(cars.get(2).getYear(), 2019);

        assertEquals(cars.get(1).getYear(), 2018);
        assertEquals(cars.get(3).getYear(), 2020);
    }

    @Test
    public void testEvenInsertionSortByModelLength() {

        List<Car> cars = new ArrayList<>();

        cars.add(new Car("BMW", 200, 2020));      // длина 3 (нечетная)
        cars.add(new Car("Audi", 180, 2019));     // длина 4 (четная)
        cars.add(new Car("Kia", 150, 2021));      // длина 3 (нечетная)
        cars.add(new Car("Toyota", 220, 2022));   // длина 7 (нечетная)
        cars.add(new Car("Ford", 190, 2018));     // длина 4 (четная)

        SortStrategy strategy =
                new EvenSortingDecorator(new InsertionSortStrategy());

        strategy.sort(cars, new CarModelComparator());

        System.out.println("После сортировки:");

        assertEquals(cars.get(0).getModel(), "BMW");
        assertEquals(cars.get(1).getModel(), "Audi");
        assertEquals(cars.get(2).getModel(), "Kia");
        assertEquals(cars.get(3).getModel(), "Ford");
        assertEquals(cars.get(4).getModel(), "Toyota");
    }

}