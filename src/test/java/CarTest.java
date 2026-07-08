import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import model.Car;

public class CarTest {
    @Test
    public void testCarCreation() {
        Car car = new Car("Tesla", 500, 2025);
        assertEquals(car.model(), "Tesla");
        assertEquals(car.power(), 500);
        assertEquals(car.yearOfProduction(), 2025);
    }

    @Test
    public void testSortByModel() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("BMW", 200, 2020));
        cars.add(new Car("Audi", 180, 2019));
        cars.add(new Car("Mersedes", 300, 2023));

        cars.sort(Comparator.comparing(Car::model));

        assertEquals(cars.get(0).model(), "Audi");
        assertEquals(cars.get(1).model(), "BMW");
        assertEquals(cars.get(2).model(), "Mersedes");
    }
}

