package util;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import core.CarField;
import model.Car;

public class CarInvariants implements IValidationSchema<CarField, Car>, ICarFieldGenerator {

  private final double minPowerValue = 5;
  private final double maxPowerValue = 3000;
  private final int maxModelLength = 50;
  private final int minYearOfProductionValue = 1900;
  private final int maxYearOfProductionValue = Year.now().getValue();

  private Optional<String> validatePower(Double value) {
    return (value < minPowerValue || value > maxPowerValue)
      ? Optional.of("Power must be greater than " + minPowerValue + " and less than " + maxPowerValue)
      : Optional.empty();
  }

  private Optional<String> validateModel(String value) {
    if (value.length() > maxModelLength) {
      return Optional.of("Model must not be longer than " + maxModelLength);
    }

    return !value.matches("[A-Za-z0-9 ]+") ? Optional.of("Invalid model value") : Optional.empty();
  }

  private Optional<String> validateYear(Integer value) {
    return (value < minYearOfProductionValue || value > maxYearOfProductionValue)
      ? Optional.of("Year of production must be greater than " + minYearOfProductionValue + " and less than " + maxYearOfProductionValue)
      : Optional.empty();
  }

  @Override
  public Optional<String> validateField(CarField field, Object value) {
    String stringVal = value.toString();

    switch (field) {
        case CarField.MODEL -> {
            return validateModel(stringVal);
          }
        case CarField.POWER -> {
            return validatePower(Double.valueOf(stringVal));
          }
        case CarField.YEAR -> {
            return validateYear(Integer.valueOf(stringVal));
          }
    }

    return Optional.empty();
  }

  @Override
  public Optional<List<String>> validate(Car car) {
    var modelError = validateModel(car.model());
    var powerError = validatePower(car.power());
    var yearError = validateYear(car.yearOfProduction());

    var errors = List.of(modelError, powerError, yearError)
      .stream()
      .flatMap(Optional::stream)
      .collect(Collectors.toList());

    return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
  }

  @Override
  public String generateModelValue() {
    String[] modelDictionary = {"Toyota", "BMW", "Tesla", "Lada", "Ford", "Audi"};
    int modelIndex = (int) (Math.random() * modelDictionary.length);

    return modelDictionary[modelIndex];
  }

  @Override
  public double generatePowerValue() {
    double value = minPowerValue + Math.random() * (maxPowerValue - minPowerValue);

    return Math.round(value * 100d) / 100d;
  }

  @Override
  public int generateYearOfProductionValue() {
    return minYearOfProductionValue + (int)(Math.random() * (maxYearOfProductionValue - minYearOfProductionValue));
  }

}
