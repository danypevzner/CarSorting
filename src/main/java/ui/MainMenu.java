package ui;

import model.Car;
import strategy.comparators.ComparatorStrategy;
import strategy.sorting.SortStrategy;
import strategy.sorting.BubbleSortStrategy;
import strategy.sorting.SelectionSortStrategy;
import strategy.sorting.InsertionSortStrategy;
import strategy.sorting.EvenSortingDecorator;
import strategy.comparators.CarModelComparator;
import strategy.comparators.CarPowerComparator;
import strategy.comparators.CarYearComparator;
import util.FieldValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static List<Car> cars = new ArrayList<>();
    private static final String AUTO_SAVE_FILE = "autosave.txt";

    private static void loadFromAutoSave() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(AUTO_SAVE_FILE));
            if (lines.isEmpty()) return;
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String model = parts[0].trim();
                    int power = Integer.parseInt(parts[1].trim());
                    int year = Integer.parseInt(parts[2].trim());
                    cars.add(new Car(model, power, year));
                }
            }
            System.out.println("Автозагрузка: загружено " + cars.size() + " машин.");
        } catch (IOException e) {
            System.out.println("Файл еще не создан");
        }
    }

    private static void autoSave() {
        try {
            List<String> lines = new ArrayList<>();
            for (Car car : cars) {
                lines.add(car.getModel() + ", " + car.getPower() + ", " + car.getYear());
            }
            Files.write(Paths.get(AUTO_SAVE_FILE), lines);
            System.out.println("Автосохранение: сохранено " + cars.size() + " машин.");
        } catch (IOException e) {
            System.out.println("Ошибка автосохранения: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadFromAutoSave();
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
            System.out.println("9. Расширенная сортировка");
            System.out.println("10. Выход");
            System.out.print("Выберите пункт: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите число.");
                choice = -1;
            }

            switch (choice) {
                case 1 -> fillManual(scanner);
                case 2 -> fillFromFile(scanner);
                case 3 -> fillRandom();
                case 4 -> sortByModel();
                case 5 -> sortByPower();
                case 6 -> sortByYear();
                case 7 -> printCar();
                case 8 -> saveToFile(scanner);
                case 9 -> sorting();
                case 10 -> {
                    autoSave();
                    System.out.println("Выход.");
                }
                default -> System.out.println("Неверный выбор");
            }
        } while (choice != 10);
    }

    private static void sorting() {

        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Выбор алгоритма
        System.out.println("\n=== Выбор алгоритма сортировки ===");
        System.out.println("1. Пузырьковая сортировка");
        System.out.println("2. Сортировка выбором");
        System.out.println("3. Сортировка вставками");
        System.out.print("Выберите пункт: ");

        int algorithmChoice = scanner.nextInt();

        SortStrategy sortStrategy;

        switch (algorithmChoice) {
            case 1:
                sortStrategy = new BubbleSortStrategy();
                break;
            case 2:
                sortStrategy = new SelectionSortStrategy();
                break;
            case 3:
                sortStrategy = new InsertionSortStrategy();
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        // Выбор режима
        System.out.println("\n=== Режим сортировки ===");
        System.out.println("1. Обычная");
        System.out.println("2. Только объекты с четными значениями");
        System.out.print("Выберите пункт: ");

        int modeChoice = scanner.nextInt();

        if (modeChoice == 2) {
            sortStrategy = new EvenSortingDecorator(sortStrategy);
        } else if (modeChoice != 1) {
            System.out.println("Неверный выбор.");
            return;
        }

        // Выбор поля
        System.out.println("\n=== Поле сортировки ===");
        System.out.println("1. Модель");
        System.out.println("2. Мощность");
        System.out.println("3. Год выпуска");
        System.out.print("Выберите пункт: ");

        int comparatorChoice = scanner.nextInt();

        ComparatorStrategy comparator;

        switch (comparatorChoice) {
            case 1:
                comparator = new CarModelComparator();
                break;
            case 2:
                comparator = new CarPowerComparator();
                break;
            case 3:
                comparator = new CarYearComparator();
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        sortStrategy.sort(cars, comparator);

        System.out.println("Сортировка успешно выполнена.");
    }

    private static void fillManual(Scanner scanner) {
        System.out.println("--- Ручной ввод ---");

        String model;
        while (true) {
            System.out.print("Введите модель: ");
            model = scanner.nextLine();
            if (FieldValidator.validateModel(model)) {
                break;
            }
            System.out.println("Модель не может быть пустой или содержать недопустимые символы, попробуйте снова");
        }

        int power;
        while (true) {
            System.out.print("Ведите мощность: ");
            try {
                power = scanner.nextInt();
                scanner.nextLine();
                if (FieldValidator.validatePower(String.valueOf(power))) {
                    break;
                }
                System.out.println("Ошибка: мощность должна быть от 50 до 500 л.с. Попробуйте снова.");
            } catch (java.util.InputMismatchException e) {
                System.out.println("Ошибка: введите целое число.");
                scanner.nextLine();
            }
        }

        int year;
        while (true) {
            System.out.print("Введите год: ");
            try {
                year = scanner.nextInt();
                scanner.nextLine();
                if (FieldValidator.validateYear(String.valueOf(year))) {
                    break;
                }
                System.out.println("Ошибка: год должен быть от 1900 до 2026. Попробуйте снова.");
            } catch (java.util.InputMismatchException e) {
                System.out.println("Ошибка: введите целое число.");
                scanner.nextLine();
            }
        }

        Car car = new Car(model, power, year);
        cars.add(car);
        System.out.println("Машина добавлена. Всего машин: " + cars.size() + "\n");
    }


    private static void printCar() {
        System.out.println("\n--- Список машин ---");
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }

        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println("Всего машин: " + cars.size() + "\n");
    }

    private static void sortByModel() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        SortStrategy strategy = new BubbleSortStrategy(); // можно использовать любую стратегию
        ComparatorStrategy comparator = new CarModelComparator();
        strategy.sort(cars, comparator);
        System.out.println("Сортировка по модели выполнена.");
    }

    private static void sortByPower() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        SortStrategy strategy = new BubbleSortStrategy();
        ComparatorStrategy comparator = new CarPowerComparator();
        strategy.sort(cars, comparator);
        System.out.println("Сортировка по мощности выполнена.");
    }

    private static void sortByYear() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        SortStrategy strategy = new BubbleSortStrategy();
        ComparatorStrategy comparator = new CarYearComparator();
        strategy.sort(cars, comparator);
        System.out.println("Сортировка по году выполнена.");
    }

    private static void fillRandom() {
        String[] models = {"Toyota", "BMW", "Tesla", "Lada", "Ford", "Audi"};
        int modelIndex = (int) (Math.random() * models.length);
        int power = 50 + (int) (Math.random() * 451);
        int year = 1900 + (int) (Math.random() * 127);

        String model = models[modelIndex];
        Car car = new Car(model, power, year);
        cars.add(car);

        System.out.println("Добавлена новая рандомная машина: " + car + "\n");
    }

    private static void fillFromFile(Scanner scanner) {
        System.out.print("Введите имя Файла: ");
        String fileName = scanner.nextLine();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            if (lines.isEmpty()) {
                System.out.println("Файл пустой");
                return;
            }
            int count = 0;
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String model = parts[0].trim();
                    int power = Integer.parseInt(parts[1].trim());
                    int year = Integer.parseInt(parts[2].trim());
                    Car car = new Car(model, power, year);
                    cars.add(car);
                    count++;
                } else {
                    System.out.println("Пропущена строка (не 3 поля): " + line);
                }
            }
            System.out.println("Добавлено машин: " + count);
        } catch (IOException e) {
            System.out.print("Ошибка чтения файла (не найден): " + e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат числа в файле. " + e.getMessage());
        }
    }

    private static void saveToFile(Scanner scanner) {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        System.out.println("Введите имя файла для сохранения: ");
        String fileName = scanner.nextLine();

        try {
            List<String> lines = new ArrayList<>();
            for (Car car : cars) {
                String line = car.getModel() + ", " + car.getPower() + ", " + car.getYear();
                lines.add(line);
            }
            Files.write(Paths.get(fileName), lines);
            System.out.println("Машины записаны в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
