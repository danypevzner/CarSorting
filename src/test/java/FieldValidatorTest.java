import org.testng.Assert;
import org.testng.annotations.Test;
import util.FieldValidator;

public class FieldValidatorTest {
    //TODO написать сообщения
    @Test
    public void testModelValidation(){
        //допустимые
        Assert.assertTrue(FieldValidator.validateModel("Camry"));
        Assert.assertTrue(FieldValidator.validateModel("3"));
        Assert.assertTrue(FieldValidator.validateModel("2CV"));
        //недопустимые
        Assert.assertFalse(FieldValidator.validateModel(null));
        Assert.assertFalse(FieldValidator.validateModel(""));
        Assert.assertFalse(FieldValidator.validateModel("@$Zhu"));
        Assert.assertFalse(FieldValidator.validateModel("CarModelWithTOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooLONgNAme"));
    }

    @Test
    public void testYearVerification(){
        Assert.assertTrue(FieldValidator.validateYear("1990"));

        Assert.assertFalse(FieldValidator.validateYear(""));
        Assert.assertFalse(FieldValidator.validateYear("nnn"));
        Assert.assertFalse(FieldValidator.validateYear("2400"));
        Assert.assertFalse(FieldValidator.validateYear("1534"));
        Assert.assertFalse(FieldValidator.validateYear(null));
    }

    @Test
    public void testPowerVerification(){
        Assert.assertTrue(FieldValidator.validatePower("150"));

        Assert.assertFalse(FieldValidator.validatePower(""));
        Assert.assertFalse(FieldValidator.validatePower("nnn"));
        Assert.assertFalse(FieldValidator.validatePower("-12"));
        Assert.assertFalse(FieldValidator.validatePower("50000"));
        Assert.assertFalse(FieldValidator.validatePower(null));
    }
}
