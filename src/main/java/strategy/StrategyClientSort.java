package strategy;

import model.Car;

import java.util.List;

public class StrategyClientSort {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }
    public void executeStrategy(List<Car> cars) {
        strategy.sort(cars);
    }
}
