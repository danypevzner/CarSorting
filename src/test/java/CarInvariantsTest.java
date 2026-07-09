import com.beust.ah.A;
import core.CarField;
import model.Car;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.CarInvariants;

@Test
public class CarInvariantsTest {
    CarInvariants carInvariants = new CarInvariants();

    public void testValidateFields() {
        Assert.assertFalse(carInvariants.validateField(CarField.MODEL, "tesla").isPresent(), "Допустимая модель не прошла валидацию");
        Assert.assertFalse(carInvariants.validateField(CarField.MODEL, "3").isPresent(), "Допустимая модель не прошла валидацию");
        Assert.assertFalse(carInvariants.validateField(CarField.MODEL, "2CV").isPresent(), "Допустимая модель не прошла валидацию");

        Assert.assertTrue(carInvariants.validateField(CarField.MODEL, "tes#la").isPresent(), "Модель с недопустимым символом прошла валидацию");
        Assert.assertTrue(carInvariants.validateField(CarField.MODEL, "toooooooooooooooooooooooooooooooooooooooooooooooooooooooooooloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooonnnnnnnnnnnnnnnnnnnnnnnngNAme").isPresent(), "Модель с слишком длинным именем прошла валидацию");
    }

    public void testValidateYear() {
        Assert.assertFalse(carInvariants.validateField(CarField.YEAR, 1990).isPresent(), "Модель с допустимым годом не прошла валидацию");
        Assert.assertTrue(carInvariants.validateField(CarField.YEAR, 2400).isPresent(), "Модель с недопустимым годом прошла валидацию");
        Assert.assertTrue(carInvariants.validateField(CarField.YEAR, 1534).isPresent(), "Модель с недопустимым годом прошла валидацию");
    }


    public void testValidatePower(){
        Assert.assertFalse(carInvariants.validateField(CarField.POWER,150).isPresent(),"Модель с допустимой мощностью не прошла валидацию");
        Assert.assertTrue(carInvariants.validateField(CarField.POWER,-12).isPresent(),"Модель с недопустимой мощностью прошла валидацию");
        Assert.assertTrue(carInvariants.validateField(CarField.POWER,500000).isPresent(),"Модель с недопустимой мощностью прошла валидацию");


    }

    public void testValidateCar(){
        Car correct = new Car("Beetle",100,1984);
        Car incorrect = new Car("Fake",1,20);
        Assert.assertFalse(carInvariants.validate(correct).isPresent(),"Корректная машина не прошла проверку");
        Assert.assertTrue(carInvariants.validate(incorrect).isPresent(),"Некорректная машина не прошла проверку");
    }
}
