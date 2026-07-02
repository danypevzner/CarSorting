package ui;
import model.Car;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static List<Car> cars = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("=== Меню ===");
            System.out.println("1. Ввод вручную");
            System.out.println("2. Заполнить из файла");
            System.out.println("3. Заполнить рандомно");
            System.out.println("4. Сортировка по модели");
            System.out.println("5. Сортировка по мощности");
            System.out.println("6. Сортировка по году");
            System.out.println("7. Показать все машины");
            System.out.println("8. Записать в файл");
            System.out.println("9. Выход");
            System.out.println("Выберите пункт: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> fillManual(scanner);
                case 2 -> System.out.println("Заглушка: чтение из файла");
                case 3 -> fillRandom();
                case 4 -> sortByModel();
                case 5 -> sortByPower();
                case 6 -> sortByYear();
                case 7 -> printCar();
                case 8 -> System.out.println("Заглушка: запись в файл");
                case 9 -> System.out.println("Выход.");
                default -> System.out.println("Неверный выбор");
            }
        } while (choice != 9);
    }

    private static void fillManual(Scanner scanner) {
        System.out.println("--- Ручной ввод ---");
        System.out.println("Введите модель: ");
        String model = scanner.nextLine();
        System.out.println("Ведите мощность: ");
        int power = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите год: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        Car car = new Car(model, power, year);
        cars.add(car);
        System.out.println("машина добавлена. Всего машин: " + cars.size());
    }

    private static void printCar() {
        System.out.println("--- Список машин ---");
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }

        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println("Всего машин: " + cars.size());
    }

    private static void sortByModel() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        cars.sort(Comparator.comparing(Car::getModel));
        System.out.println("Сортировка по модели выполнена.");
    }

    private static void sortByPower() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        cars.sort(Comparator.comparing(Car::getPower));
        System.out.println("Сортировка по мощности выполнена.");
    }

    private static void sortByYear() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        cars.sort(Comparator.comparing(Car::getYear));
        System.out.println("Сортировка по году выполнена.");
    }

    private static void fillRandom() {
        String[] models = {"Toyota", "BMW", "Tesla", "Lada", "Ford", "Audi"};
        int modelIndex = (int) (Math.random() * models.length);
        int power = 50 + (int) (Math.random() * 451);
        int year = 1990 + (int) (Math.random() * 37);

        String model = models[modelIndex];
        Car car = new Car(model, power, year);
        cars.add(car);

        System.out.println("Добавлена новая рандомная машина: " + car);
    }
}
