package util;
import model.Car;

public class RandomUtil {
    private static final String[] MODELS = {"Toyota", "BMW", "Tesla", "Lada", "Ford", "Audi"};

    public static Car fillRandom() {
        int modelIndex = (int) (Math.random() * MODELS.length);
        String model = MODELS[modelIndex];
        int power = 50 + (int) (Math.random() * 451);
        int year = 1900 + (int) (Math.random() * 127);
        return new Car(model, power, year);
    }
}
