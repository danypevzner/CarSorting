package model;

import java.util.Comparator;
import java.util.Objects;

public class Car implements Comparable<Car> {

    private final String model;
    private final double power;
    private final int yearProduction;

    public Car(String model, double power, int yearProduction) {

        this.model = model;
        this.power = power;
        this.yearProduction = yearProduction;
    }

    public String getModel() {
        return model;
    }

    public double getPower() {
        return power;
    }

    public int getYearProduction() {
        return yearProduction;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Double.compare(power, car.power) == 0 && yearProduction == car.yearProduction && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, power, yearProduction);
    }

    @Override
    public String toString() {
        return  model + " " + power + " л.с., " + yearProduction + " г.";
    }

    @Override
    public int compareTo(Car car) {

        return this.yearProduction - car.getYearProduction();
    }

    public static class CarModelComparator implements Comparator<Car> {

        @Override
        public int compare(Car o1, Car o2) {

            return o1.getModel().compareTo(o2.getModel());
        }
    }

    public static class CarPowerComparator implements Comparator<Car> {

        @Override
        public int compare(Car o1, Car o2) {
            return Double.compare(o1.power,o2.power);
        }
    }

    public static class CarYearComparator implements Comparator<Car> {

        @Override
        public int compare(Car o1, Car o2) {

            return o1.getYearProduction() - o2.getYearProduction();
        }
    }

    //Паттерн Билдер
    public static class Builder {

        private String model;
        private double power;
        private int yearProduction;


        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setPower(double power) {
            this.power = power;
            return this;
        }

        public Builder setYear(int year) {
            this.yearProduction = year;
            return this;
        }

        public Car builder() {

            return new Car( model, power, yearProduction);
        }

    }

}
