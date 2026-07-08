package util;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import core.CarField;
import model.Car;

public class CarValidationSchema implements IValidationSchema<CarField, Car> {

  private Optional<String> validatePower(Double value) {
    double min = 5;
    double max = 3000;

    return (value < min || value > max)
      ? Optional.of("Power must be greater than " + min + " and less than " + max)
      : Optional.empty();
  }

  private Optional<String> validateModel(String value) {
    int maxLength = 50;

    if (value.length() > maxLength) {
      return Optional.of("Model must not be longer than " + maxLength);
    }

    return !value.matches("[A-Za-z0-9 ]+") ? Optional.of("Invalid model value") : Optional.empty();
  }

  private Optional<String> validateYear(Integer value) {
    double min = 1900;
    double max = Year.now().getValue();

    return (value < min || value > max)
      ? Optional.of("Year of production must be greater than " + min + " and less than " + max)
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

}
