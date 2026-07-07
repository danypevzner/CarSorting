package ui;

import model.Car;
import strategy.comparators.*;
import strategy.sorting.*;
import util.FieldValidator;
import util.FileUtil;
import util.RandomUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private static List<Car> cars = new ArrayList<>();
    private static final String AUTO_SAVE_FILE = "autosave.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadFromAutoSave();
        int choice;
        do {
            System.out.println("=== Меню ===");
            System.out.println("1. Добавить машину вручную");
            System.out.println("2. Загрузить машины из файла");
            System.out.println("3. Сгенерировать случайную машину");
            System.out.println("4. Показать все машины");
            System.out.println("5. Сохранить список машин в файл");
            System.out.println("6. Сортировка по модели");
            System.out.println("7. Сортировка по мощности");
            System.out.println("8. Сортировка по году");
            System.out.println("9. Расширенная сортировка");
            System.out.println("10. Поиск машин по модели (многопоточный)");
            System.out.println("11. Выход");
            System.out.println("Выберите пункт: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите число.");
                choice = -1;
            }

            switch (choice) {
                case 1 -> fillManual(scanner);
                case 2 -> {
                    System.out.print("Введите имя файла: ");
                    String fileName = scanner.nextLine();
                    try {
                        List<Car> loaded = FileUtil.readFile(fileName);
                        cars.addAll(loaded);
                        System.out.println("Загружено машин: " + loaded.size());
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения файла: " + e.getMessage());
                    }
                }
                case 3 -> {
                    Car car = RandomUtil.fillRandom();
                    cars.add(car);
                    System.out.println("Добавлена новая рандомная машина: "
                            + car.getModel() + " " + String.format("%.1f", car.getPower()) + " л.с., " + car.getYear() + " г.\n");
                }
                case 4 -> printCar();
                case 5 -> {
                    if (cars.isEmpty()) {
                        System.out.println("Список машин пуст.");
                        return;
                    }
                    System.out.print("Введите имя файла для сохранения: ");
                    String fileName = scanner.nextLine();
                    try {
                        FileUtil.writeFile(cars, fileName);
                        System.out.println("Машины записаны в файл: " + fileName);
                    } catch (IOException e) {
                        System.out.println("Ошибка записи: " + e.getMessage());
                    }
                }
                case 6 -> sortByModel();
                case 7 -> sortByPower();
                case 8 -> sortByYear();
                case 9 -> sorting();
                case 10 -> System.out.println("Заглушка: многопоточный поиск");
                case 11 -> {
                    autoSave();
                    System.out.println("Выход.");
                }
                default -> System.out.println("Неверный выбор");
            }
        } while (choice != 11);
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
            System.out.print("Введите мощность: ");
            try {
                power = scanner.nextInt();
                scanner.nextLine();
                if (FieldValidator.validatePower(String.valueOf(power))) {
                    break;
                }
                System.out.println("Ошибка: мощность должна быть от 5 до 500 л.с. Попробуйте снова.");
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
            System.out.println(car.getModel() + " " + String.format("%.1f", car.getPower()) + " л.с., " + car.getYear() + " г.");
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

    private static void loadFromAutoSave() {
        try {
            cars = FileUtil.readFile(AUTO_SAVE_FILE);
            System.out.println("Автозагрузка: загружено " + cars.size() + " машин.\n");
        } catch (IOException e) {
            System.out.println("Файл автосохранения не найден\n");
        }
    }

    private static void autoSave() {
        try {
            FileUtil.writeFile(cars, AUTO_SAVE_FILE);
            System.out.println("Автосохранение: сохранено " + cars.size() + " машин.");
        } catch (IOException e) {
            System.out.println("Ошибка автосохранения: " + e.getMessage());
        }
    }
}
