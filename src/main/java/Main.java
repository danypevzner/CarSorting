import model.Car;
import util.FileUtil;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileUtil.appendFile(new Car("Mustang",200,1990),"C:\\Users\\Daniil Pevzner\\IdeaProjects\\CarSorting\\src\\main\\files\\CarsCSV");
    }
}
