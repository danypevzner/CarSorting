import model.Car;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.CarInvariants;
import util.RandomCarFactory;

@Test
public class RandomCarFactoryTest {
   public void testRandomCarFactory(){
       CarInvariants invariants = new CarInvariants();
       RandomCarFactory  factory=  new RandomCarFactory(invariants);
       Car car = factory.create();
       Assert.assertFalse(invariants.validate(car).isPresent(),"Created incorrect car");
   }
}
