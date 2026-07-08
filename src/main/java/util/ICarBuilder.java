package util;

import model.Car;

public interface ICarBuilder {
  public ICarBuilder setModel(String model);
  public ICarBuilder setPower(double power);
  public ICarBuilder setYearOfProduction(int year);
  public Result<Car> build();
}
