package core;

import model.Car;
import util.ICarBuilder;
import util.IValidationSchema;

public interface IApplicationContext {
  public ICarBuilder getBuilder();
  public IValidationSchema<CarField, Car> getValidationSchema();
}
