package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Car;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ArrayList<Car> readCarList(String filePath){
        return  readObjectFromFile(filePath,new TypeReference<ArrayList<Car>>() {});
    }

    public static void writeCarList(String filePath,ArrayList<Car> carList){
        saveObjectToFile(filePath,carList);
    }

    public static void  addCarToFile(String filePath,Car car){
        ArrayList<Car> carList = readCarList(filePath);
        carList.add(car);
        writeCarList(filePath,carList);
    }

    public static void addCarListToFile(String filePath,ArrayList<Car> carList){
        ArrayList<Car> carListCurrent = readCarList(filePath);
        carListCurrent.addAll(carList);
        writeCarList(filePath,carListCurrent);
    }



    private static <T> T readObjectFromFile(String filepath, TypeReference<T> typeRef) {
        try {
            if (filepath == null || filepath.isBlank()) {
                throw new IllegalArgumentException("Путь к файлу не может быть пустым");
            }
            Path path = Path.of(filepath);
            String jsonString = Files.readString(path);
            return objectMapper.readValue(jsonString, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + filepath, e);
        }
    }

    public static void saveObjectToFile(String filepath,Object object){
        if (filepath == null || filepath.isBlank()) {
            throw new IllegalArgumentException("Путь к файлу не может быть пустым");
        }
        try {
            File file = new File(filepath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            objectMapper.writeValue(file, object);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл: " + filepath, e);
        }
    }

    private FileUtil(){};

}