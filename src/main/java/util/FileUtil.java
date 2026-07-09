package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import collection.CarCollection;
import model.Car;

public class FileUtil {

    public static CarCollection readFile(String filepath) throws IOException {
        CarCollection result = new CarCollection();
        SimpleCarBuilder builder = new SimpleCarBuilder(new CarInvariants());
        Path path = Path.of(filepath);

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] values = line.split(";", 0);

            if (values.length < 3) {
                throw new IOException("Некорректная строка - недостаточно полей:" + line);
            }

            try {
                String model = values[0].trim();
                if (model.isBlank()) throw new IOException("Car argument is blank or null");
                Double power = StringUtils.parseDouble(values[1].trim());
                int year = StringUtils.parseInt(values[2].trim());
                Result<Car> built = builder
                        .setModel(model)
                        .setPower(power)
                        .setYearOfProduction(year)
                        .build();

                switch (built) {
                    case Result.Ok(Car car) -> {
                        result.add(car);
                    }
                    case Result.Failure(List<String> errors) -> {
                        errors.forEach(System.out::println);
                    }
                }
            } catch (Exception e) {
                throw new IOException("Ошибка парсинга строки:"+line);
            }
        }

        return result;
    }

    public static void  writeFile(List<Car> carList,String filepath) throws IOException {
        try {
            Path path = Path.of(filepath);
            Path parentDir = path.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Car car : carList) {
                    String line = car.model() + ";" + car.power() + ";" + car.yearOfProduction();
                    writer.write(line);
                    writer.newLine();
                }
            }
        }catch (Exception e){
            throw new IOException("Problem during writing file"+e.getCause());
        }
    }

    public static void  appendFile(List<Car> carList,String filepath) throws IOException {
        Path path = Path.of(filepath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {

            for (Car car : carList) {
                String line = car.model() + ";" + car.power() + ";" + car.yearOfProduction();
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static void  appendFile(Car car,String filepath) throws IOException {
        Path path = Path.of(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                String line = car.model() + ";" + car.power() + ";" + car.yearOfProduction();
                writer.write(line);
                writer.newLine();
        }
    }
}
