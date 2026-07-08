package util;

import java.util.List;

import core.CarField;
import model.Car;

public class SimpleCarBuilder implements ICarBuilder {
    private String model;
    private double power;
    private int year;
    private final IValidationSchema<CarField, Car> validationSchema;

    public SimpleCarBuilder(IValidationSchema<CarField, Car> validationSchema) {
        this.validationSchema = validationSchema;
    }

    @Override
    public ICarBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    @Override
    public ICarBuilder setPower(double power) {
        this.power = power;
        return this;
    }

    @Override
    public ICarBuilder setYearOfProduction(int year) {
        this.year = year;
        return this;
    }

    @Override
    public Result<Car> build() {

        try {
            Car car = new Car(model, power, year);
            var errors = validationSchema.validate(car);

            return errors.isEmpty() ? Result.ok(new Car(model, power, year)) : Result.failure(errors.get());
        } catch (Exception e) {
            return Result.failure(List.of(e.getMessage()));
        }
    }

}
