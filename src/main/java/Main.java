import com.fasterxml.jackson.core.type.TypeReference;
import model.Car;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Car> carList = new ArrayList<Car>();
        carList.add(new Car("Tesla",300,2000));
        carList.add(new Car("Honda",140,2020));
        FileUtil.writeCarList("C:\\Users\\Daniil Pevzner\\IdeaProjects\\CarSorting\\src\\main\\files\\carsJson",carList);
        ArrayList<Car> carListRead;
        carListRead= FileUtil.readCarList("C:\\Users\\Daniil Pevzner\\IdeaProjects\\CarSorting\\src\\main\\files\\carsJson");
        System.out.println(carListRead.get(0).getModel());
        System.out.println(carListRead.get(1).getModel());

    }
}
