package model;

public class Car {
    private String model;
    private int power;
    private int year;

    public Car(String model, int power, int year) {
        this.model = model;
        this.power = power;
        this.year = year;
    }

    public String getModel() {
        return model;
    }
    public int getPower() {
        return power;
    }
    public int getYear() {
        return year;
    }

    public String toString() {
        return getModel() + " " + getPower() + " " + getYear();
    }
}
