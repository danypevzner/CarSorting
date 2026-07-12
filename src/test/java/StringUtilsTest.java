import org.testng.Assert;
import org.testng.annotations.Test;
import util.StringUtils;

@Test
public class StringUtilsTest {
    public void testStringUtils(){
        Assert.assertEquals(StringUtils.parseDouble("110.4"), 110.4,"Double parsed incorrectly");
        Assert.assertNull(StringUtils.parseDouble("yupeee"),"Error during working with incorrect value(parsing double)");
        Assert.assertEquals(StringUtils.parseInt("110"),110,"Integer parsed incorrectly");
        Assert.assertNull(StringUtils.parseInt("Yupeee"),"Error during working with incorect value(paring Integer)");
    }
}
