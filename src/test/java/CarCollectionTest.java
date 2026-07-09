import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import collection.CarCollection;
import core.CarField;
import core.Sorting;
import model.Car;

@Test
public class CarCollectionTest {

    @Test
    public void testBubbleSortByModel() {
        var cars = prepareCollection();
        cars.sortBy(CarField.MODEL, Sorting.BUBBLE);
        assertRegularModelSortOrder(cars);
    }

    @Test
    public void testSelectionSortByModel() {
        var cars = prepareCollection();
        cars.sortBy(CarField.MODEL, Sorting.SELECTION);
        assertRegularModelSortOrder(cars);
    }

    @Test
    public void testInsertionSortByModel() {
        var cars = prepareCollection();
        cars.sortBy(CarField.MODEL, Sorting.INSERTION);
        assertRegularModelSortOrder(cars);
    }

    @Test
    public void testBubbleSortByPower() {
        var cars = prepareCollection();
        cars.sortBy(CarField.POWER, Sorting.BUBBLE);
        assertRegularPowerSortOrder(cars);
    }

    @Test
    public void testSelectionSortByPower() {
        var cars = prepareCollection();
        cars.sortBy(CarField.POWER, Sorting.SELECTION);
        assertRegularPowerSortOrder(cars);
    }

    @Test
    public void testInsertionSortByPower() {
        var cars = prepareCollection();
        cars.sortBy(CarField.POWER, Sorting.INSERTION);
        assertRegularPowerSortOrder(cars);
    }

    @Test
    public void testBubbleSortByYearOfProduction() {
        var cars = prepareCollection();
        cars.sortBy(CarField.YEAR, Sorting.BUBBLE);
        assertRegularYearSortOrder(cars);
    }

    @Test
    public void testSelectionSortByYearOfProduction() {
        var cars = prepareCollection();
        cars.sortBy(CarField.YEAR, Sorting.SELECTION);
        assertRegularYearSortOrder(cars);
    }

    @Test
    public void testInsertionSortByYearOfProduction() {
        var cars = prepareCollection();
        cars.sortBy(CarField.YEAR, Sorting.INSERTION);
        assertRegularYearSortOrder(cars);
    }

    @Test
    public void testEvenBubbleSortByModel() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.MODEL, Sorting.BUBBLE);
        assertEvenModelSortOrder(cars);
    }

    @Test
    public void testEvenSelectionSortByModel() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.MODEL, Sorting.SELECTION);
        assertEvenModelSortOrder(cars);
    }

    @Test
    public void testEvenInsertionSortByModel() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.MODEL, Sorting.INSERTION);
        assertEvenModelSortOrder(cars);
    }

    @Test
    public void testEvenBubbleSortByPower() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.POWER, Sorting.BUBBLE);
        assertEvenPowerSortOrder(cars);
    }

    @Test
    public void testEvenSelectionSortByPower() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.POWER, Sorting.SELECTION);
        assertEvenPowerSortOrder(cars);
    }

    @Test
    public void testEvenInsertionSortByPower() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.POWER, Sorting.INSERTION);
        assertEvenPowerSortOrder(cars);
    }

    @Test
    public void testEvenBubbleSortByYearOfProduction() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.YEAR, Sorting.BUBBLE);
        assertEvenYearSortOrder(cars);
    }

    @Test
    public void testEvenSelectionSortByYearOfProduction() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.YEAR, Sorting.SELECTION);
        assertEvenYearSortOrder(cars);
    }

    @Test
    public void testEvenInsertionSortByYearOfProduction() {
        var cars = prepareCollection();
        cars.sortEvenlyBy(CarField.YEAR, Sorting.INSERTION);
        assertEvenYearSortOrder(cars);
    }

    @Test
    public void testCount() {
        var cars = prepareCollection();
        var target = cars.getLast();
        cars.add(target);
        var count = cars.count(target);

        assertEquals(count, 2);
    }

    private void assertRegularModelSortOrder(CarCollection cars) {
        assertEquals(cars.get(0).model(), "Audi");
        assertEquals(cars.get(1).model(), "BMW");
        assertEquals(cars.get(2).model(), "Ford");
        assertEquals(cars.get(3).model(), "Mercedes");
    }

    private void assertRegularPowerSortOrder(CarCollection cars) {
        assertEquals(cars.get(0).power(), 151);
        assertEquals(cars.get(1).power(), 180);
        assertEquals(cars.get(2).power(), 201);
        assertEquals(cars.get(3).power(), 300);
    }

    private void assertRegularYearSortOrder(CarCollection cars) {
        assertEquals(cars.get(0).yearOfProduction(), 2018);
        assertEquals(cars.get(1).yearOfProduction(), 2020);
        assertEquals(cars.get(2).yearOfProduction(), 2021);
        assertEquals(cars.get(3).yearOfProduction(), 2023);
    }

    private void assertEvenModelSortOrder(CarCollection cars) {
        assertEquals(cars.get(0).model(), "BMW");
        assertEquals(cars.get(1).model(), "Audi");
        assertEquals(cars.get(2).model(), "Ford");
        assertEquals(cars.get(3).model(), "Mercedes");
    }

    private void assertEvenPowerSortOrder(CarCollection cars) {
        assertEquals(cars.get(0).power(),201);
        assertEquals(cars.get(1).power(),151);
        assertEquals(cars.get(2).power(),180);
        assertEquals(cars.get(3).power(),300);
    }

    private void assertEvenYearSortOrder(CarCollection cars) {
        assertEquals(cars.get(0).yearOfProduction(), 2018);
        assertEquals(cars.get(1).yearOfProduction(), 2021);
        assertEquals(cars.get(2).yearOfProduction(), 2023);
        assertEquals(cars.get(3).yearOfProduction(), 2020);
    }

    private CarCollection prepareCollection() {
        var cars = new CarCollection();
        cars.add(new Car("BMW", 201, 2020));
        cars.add(new Car("Ford", 151, 2021));
        cars.add(new Car("Mercedes", 300, 2023));
        cars.add(new Car("Audi", 180, 2018));

        return cars;
    }
}
