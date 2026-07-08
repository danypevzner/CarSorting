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
                Double power = Double.parseDouble(values[1].trim());
                int year = Integer.parseInt(values[2].trim());

                if (!FieldValidator.validateYear(String.valueOf(year))){
                    throw new IOException("Incorrect year value:"+year);
                }

                if (!FieldValidator.validatePower(String.valueOf(power))){
                    throw new IOException("Incorrect power value:"+power);
                }

                if (!FieldValidator.validateModel(String.valueOf(model))){
                    throw new IOException("Incorrect model value:"+model);
                }

                result.add(new Car(model, power,year));
            } catch (NumberFormatException e) {
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
