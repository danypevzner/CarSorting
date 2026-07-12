package model;

import java.util.Objects;

public record Car(
        String model,
        double power,
        int yearOfProduction
) {
    private void assertModel(String value) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Car model cannot be blank");
        }
    }

    private void assertPower(double value) {
        if (value <= 0) {
            throw new RuntimeException("Car power cannot be less or equal zero");
        }
    }

    private void assertYear(int value) {
        if (value < 0) {
            throw new RuntimeException("Car manufacturing year cannot be less than zero");
        }
    }

    public Car {
        assertModel(model);
        assertPower(power);
        assertYear(yearOfProduction);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(power, car.power) == 0 && yearOfProduction == car.yearOfProduction && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, power, yearOfProduction);
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("[")
                .append(model())
                .append(" | ")
                .append(power())
                .append(" л.с. | ")
                .append(yearOfProduction())
                .append("г.]")
                .toString();
    }
}