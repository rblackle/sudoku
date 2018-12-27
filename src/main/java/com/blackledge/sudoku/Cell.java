package com.blackledge.sudoku;

import com.blackledge.sudoku.util.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A single cell in a sudoku game (eg where a single value can be entered)
 * This class is mutable and can also be subscribed to by solvers which may update when a value is entered.
 * Values are stored as they are displayed to the user (values 1-9, 1 indexed)
 */
public class Cell {
    private int value;
    private final List<CellChangeListener> changeListeners;
    private final Position position;

    public Cell(Position position) {
        this.value = 0;
        this.position = position;
        this.changeListeners = new ArrayList<>();
    }
    
    public Optional<Integer> getValue() {
        if(isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    private boolean isEmpty() {
        return value == 0;
    }

    public void setValue(int newValue) {
        this.value = newValue;
        for(CellChangeListener changeListener : changeListeners) {
            changeListener.assigned(this, newValue);
        }
    }
    
    public void addListener(CellChangeListener listener) {
        changeListeners.add(listener);
    }

    public Position getPosition() {
        return position;
    }

}
