import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import model.Car;
import util.FileUtil;

public class FileUtilTest {

    private Path createTestFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("cars_", ".txt");
        Files.writeString(tempFile, content, StandardCharsets.UTF_8);
        return tempFile;
    }

    @Test
    public void testReadFile_Success() throws IOException {

        String content = "Toyota;100;2000\nBMW;230;1998\n";
        Path file = createTestFile(content);
        List<Car> cars = FileUtil.readFile(file.toString());

        Assert.assertEquals(cars.size(), 2, "Должно быть прочитано 2 машины");
        Assert.assertEquals(cars.get(0).model(), "Toyota");
        Assert.assertEquals(cars.get(0).yearOfProduction(), 2000);
        Assert.assertEquals(cars.get(0).power(), 100);

        Assert.assertEquals(cars.get(1).model(), "BMW");
        Assert.assertEquals(cars.get(1).yearOfProduction(), 1998);
        Assert.assertEquals(cars.get(1).power(), 230);
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFile_InvalidFormat() throws IOException {
        String content = "Tesla;2021\n";
        Path file = createTestFile(content);
        FileUtil.readFile(file.toString());
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFile_ParseError() throws IOException {

        String content = "Ford;122;nightyseven\n";
        Path file = createTestFile(content);
        FileUtil.readFile(file.toString());
    }

    @Test
    public void testWriteFile_Success() throws IOException {
        List<Car> carList = List.of(
                new Car("Audi", 202, 2020),
                new Car("Mercedes", 203, 2020)
        );
        Path tempFile = Files.createTempFile("write_", ".txt");

        FileUtil.writeFile(carList, tempFile.toString());
        List<String> lines = Files.readAllLines(tempFile, StandardCharsets.UTF_8);
        Assert.assertEquals(lines.size(), 2);
        Assert.assertTrue(lines.get(0).contains("Audi;202.0;2020"));
    }

    @Test
    public void testAppendFile_List_Success() throws IOException {
        Path tempFile = Files.createTempFile("append_", ".txt");
        List<Car> initialList = List.of(new Car("Lada", 88, 1990));
        FileUtil.writeFile(initialList, tempFile.toString());

        List<Car> toAppend = List.of(new Car("Kia", 150, 2026));

        FileUtil.appendFile(toAppend, tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile, StandardCharsets.UTF_8);
        Assert.assertEquals(lines.size(), 2);
        Assert.assertTrue(lines.stream().anyMatch(l -> l.contains("Lada;88.0;1990")));
        Assert.assertTrue(lines.stream().anyMatch(l -> l.contains("Kia;150.0;2026")));
    }

    @Test
    public void testAppendFile_Single_Success() throws IOException {

        Path tempFile = Files.createTempFile("single_", ".txt");
        FileUtil.writeFile(List.of(), tempFile.toString());
        Car car = new Car("Hyundai", 154, 2001);

        FileUtil.appendFile(car, tempFile.toString());
        List<String> lines = Files.readAllLines(tempFile, StandardCharsets.UTF_8);
        Assert.assertEquals(lines.size(), 1);
        Assert.assertTrue(lines.get(0).contains("Hyundai;154.0;2001"));
    }

    @Test
    public void testReadEmptyFile() throws IOException {
        Path tempFile = Files.createTempFile("empty_", ".txt");
        Files.writeString(tempFile, "", StandardCharsets.UTF_8);
        List<Car> result = FileUtil.readFile(tempFile.toString());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }
}