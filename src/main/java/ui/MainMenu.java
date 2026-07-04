package ui;
import model.Car;
import strategy.comparators.*;
import strategy.sorting.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            System.out.println("4. Сортировка");
            System.out.println("5. Показать все машины");
            System.out.println("6. Записать в файл");
            System.out.println("0. Выход");
            System.out.println("Выберите пункт: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> fillManual(scanner);
                case 2 -> fillFromFile(scanner);
                case 3 -> fillRandom();
                case 4 -> sorting();
                //case 5 -> carSorting(new InsertionSortStrategy(), new CarPowerComparator());
                //case 6 -> carSorting(new SelectionSortStrategy(), new CarYearComparator());
                case 5 -> printCar();
                case 6 -> saveToFile(scanner);
                case 0 -> System.out.println("Выход.");
                default -> System.out.println("Неверный выбор");
            }
        } while (choice != 0);
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
        cars.sort(Comparator.comparing(Car::getModel));
        System.out.println("Сортировка по модели выполнена.");
    }

    @Deprecated
    private static void sortByPower() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        cars.sort(Comparator.comparing(Car::getPower));
        System.out.println("Сортировка по мощности выполнена.");
    }

    @Deprecated
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
            System.out.print("Ошибка чтения файла: " + e.getMessage());
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
