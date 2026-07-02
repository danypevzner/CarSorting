import model.Car;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class CarTest {
    @Test
    public void testCarCreation() {
        Car car = new Car("Tesla", 500, 2025);
        assertEquals(car.getModel(), "Tesla");
        assertEquals(car.getPower(), 500);
        assertEquals(car.getYear(), 2025);
    }

    @Test
    public void testSortByModel() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("BMW", 200, 2020));
        cars.add(new Car("Audi", 180, 2019));
        cars.add(new Car("Mersedes", 300, 2023));

        cars.sort(Comparator.comparing(Car::getModel));

        assertEquals(cars.get(0).getModel(), "Audi");
        assertEquals(cars.get(1).getModel(), "BMW");
        assertEquals(cars.get(2).getModel(), "Mersedes");
    }
}

