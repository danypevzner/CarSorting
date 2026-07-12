package collection;

import model.Car;

import java.util.ArrayDeque;
import java.util.Deque;

public class UndoCarCollection extends CarCollection {
    private final Deque<CarCollection> history = new ArrayDeque<>();
    private static final int MAX_HISTORY = 10;

    @Override
    public boolean add(Car car) {
        saveState();
        return super.add(car);
    }

    @Override
    public Car remove(int index) {
        saveState();
        return super.remove(index);
    }

    @Override
    public boolean addAll(java.util.Collection<? extends Car> cars) {
        saveState();
        return super.addAll(cars);
    }

    @Override
    public void clear() {
        saveState();
        super.clear();
    }

    private void saveState() {
        CarCollection snapshot = new CarCollection();
        snapshot.addAll(this);
        history.push(snapshot);
        if (history.size() > MAX_HISTORY) {
            history.pollLast();
        }
    }

    public boolean undo() {
        if (history.isEmpty()) {
            return false;
        }
        CarCollection previous = history.pop();
        this.clear();
        this.addAll(previous);
        return true;
    }
}
