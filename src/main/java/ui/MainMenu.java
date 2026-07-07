package ui;

import model.Car;
import strategy.comparators.*;
import strategy.sorting.*;
import util.FileUtil;

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
            System.out.println("Выберите пункт: ");
            choice = scanner.nextInt();
            scanner.nextLine();

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
                case 3 -> fillRandom();
                case 4 -> sortByModel();
                case 5 -> sortByPower();
                case 6 -> sortByYear();
                case 7 -> printCar();
                case 8 -> {
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
        System.out.print("Всего машин: " + cars.size());
    }

    @Deprecated
    private static void sortByModel() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        SortStrategy strategy = new BubbleSortStrategy();
        ComparatorStrategy comparator = new CarModelComparator();
        strategy.sort(cars, comparator);
        System.out.println("Сортировка по модели выполнена.");
    }

    @Deprecated
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

    @Deprecated
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

        System.out.println("Добавлена новая рандомная машина: " + car);
    }

    private static void loadFromAutoSave() {
        try {
            List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(AUTO_SAVE_FILE));
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
            System.out.println("Файл автосохранения не найден");
        }
    }

    private static void autoSave() {
        try {
            List<String> lines = new ArrayList<>();
            for (Car car : cars) {
                lines.add(car.getModel() + ", " + car.getPower() + ", " + car.getYear());
            }
            java.nio.file.Files.write(java.nio.file.Paths.get(AUTO_SAVE_FILE), lines);
            System.out.println("Автосохранение: сохранено " + cars.size() + " машин.");
        } catch (IOException e) {
            System.out.println("Ошибка автосохранения: " + e.getMessage());
        }
    }
}
