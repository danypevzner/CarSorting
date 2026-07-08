package util;

import model.Car;

public class RandomCarFactory implements ICarFactory {
  private ICarFieldGenerator provider;

  public RandomCarFactory(ICarFieldGenerator provider) {
    this.provider = provider;
  }

  @Override
  public Car create() {
    return new Car(
      provider.generateModelValue(),
      provider.generatePowerValue(),
      provider.generateYearOfProductionValue()
    );
  }
}
