package ui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import collection.CarCollection;
import core.CarField;
import core.IApplicationContext;
import core.IUiClient;
import core.Sorting;
import model.Car;
import util.FileUtil;
import util.Result;

public class CliClient implements IUiClient {
	private CarCollection cars = new CarCollection();
    private final IApplicationContext context;
    private final Scanner scanner = new Scanner(System.in);
    private static final String AUTO_SAVE_FILE = "autosave.txt";

    public CliClient(IApplicationContext context) {
        this.context = context;
    }

    private void sorting() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }

        // Выбор алгоритма
        System.out.println("\n=== Выбор алгоритма сортировки ===");
        System.out.println("1. Пузырьковая сортировка");
        System.out.println("2. Сортировка выбором");
        System.out.println("3. Сортировка вставками");
        System.out.print("Выберите пункт: ");

        int algorithmChoice = scanner.nextInt();

        Sorting sorting;

        switch (algorithmChoice) {
            case 1 -> sorting = Sorting.BUBBLE;
            case 2 -> sorting = Sorting.SELECTION;
            case 3 -> sorting = Sorting.INSERTION;
            default -> {
                System.out.println("Неверный выбор.");
                return;
            }
        }

        // Выбор поля
        System.out.println("\n=== Поле сортировки ===");
        System.out.println("1. Модель");
        System.out.println("2. Мощность");
        System.out.println("3. Год выпуска");
        System.out.print("Выберите пункт: ");

        int comparatorChoice = scanner.nextInt();

        CarField field;

        switch (comparatorChoice) {
            case 1 -> field = CarField.MODEL;
            case 2 -> field = CarField.POWER;
            case 3 -> field = CarField.YEAR;
            default -> {
                System.out.println("Неверный выбор.");
                return;
            }
        }

        // Выбор режима
        System.out.println("\n=== Режим сортировки ===");
        System.out.println("1. Обычная");
        System.out.println("2. Только объекты с четными значениями");
        System.out.print("Выберите пункт: ");

        int modeChoice = scanner.nextInt();

        switch (modeChoice) {
            case 1 -> {
                cars.sortBy(field, sorting);
            }
            case 2 -> {
                cars.sortEvenlyBy(field, sorting);
            }
            default -> {
                System.out.println("Неверный выбор.");
                return;
            }
        }

        System.out.println("Сортировка успешно выполнена.");
    }

    private String readAsNonEmptyString(String prompt) {
        String result = null;

        while (result == null || result.isBlank()) {
            System.out.println(prompt);
            result = scanner.nextLine();
        }

        return result;
    }

    private Double readAsDouble(String prompt) {
        Double result = null;

        while (result == null) {
            System.out.println(prompt);

            try {
                result = Double.valueOf(scanner.nextLine());
            } catch (NumberFormatException e){}
        }

        return result;
    }

    private Integer readAsInt(String prompt) {
        Integer result = null;

        while (result == null) {
            System.out.println(prompt);

            try {
                result = Integer.valueOf(scanner.nextLine());
            } catch (NumberFormatException e){}
        }

        return result;
    }

    private void fillManual() {
        System.out.println("--- Ручной ввод ---");
        var validationSchema = context.getValidationSchema();

        String model;
        while (true) {
            model = readAsNonEmptyString("Введите модель: ");
            if (validationSchema.validateField(CarField.MODEL, model).isEmpty()) {
                break;
            }
            System.out.println("Модель не может быть пустой или содержать недопустимые символы, попробуйте снова");
        }

        double power;
        while (true) {
            power = readAsDouble("Введите мощность: ");
            Optional<String> result = validationSchema.validateField(CarField.POWER, power);
            if (result.isEmpty()) {
                break;
            }
            System.out.println(result.get());
        }

        int year;
        while (true) {
            year = readAsInt("Введите год: ");
            Optional<String> result = validationSchema.validateField(CarField.YEAR, year);
            if (result.isEmpty()) {
                break;
            }
            System.out.println(result.get());
        }

        var builder = context.getBuilder();
        Result<Car> result = builder
            .setModel(model)
            .setPower(power)
            .setYearOfProduction(year)
            .build();

        switch (result) {
            case Result.Ok(Car car) -> {
                cars.add(car);
                System.out.print("машина добавлена. Всего машин: " + cars.size());
            }
            case Result.Failure(List<String> errors) -> {
                System.out.println("При создании возникли ошибки:");
                errors.stream().forEach(System.out::println);
            }
        }
    }

    private void printCar() {
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

    private void sortByModel() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        cars.sortBy(CarField.MODEL, Sorting.BUBBLE);
        System.out.println("Сортировка по модели выполнена.");
    }

    private void sortByPower() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        cars.sortBy(CarField.POWER, Sorting.BUBBLE);
        System.out.println("Сортировка по мощности выполнена.");
    }

    private void sortByYear() {
        if (cars.isEmpty()) {
            System.out.println("Список машин пуст.");
            return;
        }
        cars.sortBy(CarField.YEAR, Sorting.BUBBLE);
        System.out.println("Сортировка по году выполнена.");
    }

    private void loadFromAutoSave() {
        try {
            cars = FileUtil.readFile(AUTO_SAVE_FILE);
            System.out.println("Автозагрузка: загружено " + cars.size() + " машин.\n");
        } catch (IOException e) {
            System.out.println("Файл автосохранения не найден\n");
        }
    }

    private void autoSave() {
        try {
            FileUtil.writeFile(cars, AUTO_SAVE_FILE);
            System.out.println("Автосохранение: сохранено " + cars.size() + " машин.");
        } catch (IOException e) {
            System.out.println("Ошибка автосохранения: " + e.getMessage());
        }
    }

    private void fillRandom() {
        int amount;
        while (true) {
            amount = readAsInt("Введите количество машин для генерации: ");
            if (amount > 0) {
                break;
            }
            System.out.println("Количество не  может быть 0 или отрицательным");
        }

        var carFactory = context.getFactory();
        List<Car> generatedCars = IntStream.generate(() -> 1).limit(amount)
            .mapToObj(i -> carFactory.create())
            .collect(Collectors.toList());
        cars.addAll(generatedCars);
        System.out.println("Добавлено " + generatedCars.size() + " машин");
    }

    private void countOccurrences() {
        String search = readAsNonEmptyString("Найти машину: ");

        List<Car> results = cars.stream()
            .filter(car -> car.model().toLowerCase().contains(search)
                || Double.toString(car.power()).contains(search)
                || Integer.toString(car.yearOfProduction()).contains(search)
            )
            .toList();

        if (results.isEmpty()) {
            System.out.println("Нет подходящих элементов");
        } else {
            System.out.println("Найдено " + results.size() + " элементов:");
            IntStream.range(0, results.size()).forEach(i -> System.out.println("[" + i + "]: " + results.get(i)));

            int targetIndex;
            while (true) {
                targetIndex = readAsInt("Введите индекс элемента для поиска числа вхождений: ");
                if (targetIndex >= 0) {
                    break;
                }
                System.out.println("Индекс не  может быть отрицательным");
            }

            Car targetCar = results.get(targetIndex);
            var amount = cars.parallelStream().filter(targetCar::equals).count();

            System.out.println("Машина " + targetCar + " встречается " + amount + " раз");
        }
    }

    @Override
    public void start() {
        loadFromAutoSave();
        int choice;
        do {
            System.out.println("=== Меню ===");
            System.out.println("1. Добавить машину вручную");
            System.out.println("2. Загрузить машины из файла");
            System.out.println("3. Сгенерировать случайные машины");
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
                case 1 -> fillManual();
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
                case 10 -> countOccurrences();
                case 11 -> {
                    autoSave();
                    System.out.println("Выход.");
                }
                default -> System.out.println("Неверный выбор");
            }
        } while (choice != 11);
    }

	@Override
	public void exit() {
		scanner.close();
        System.exit(0);
	}
}
