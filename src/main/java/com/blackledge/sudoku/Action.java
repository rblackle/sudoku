package com.blackledge.sudoku;

import com.blackledge.sudoku.util.Position;
import java.util.Objects;

/**
 * Immutable pojo for entering in a number for a specific board position.
 * All values are as they are displayed to the user (values 1-9, 1 indexed)
 */
public class Action {
    private final Position position;
    private final int valueToAssign;

    public Action(Position position, int valueToAssign) {
        this.position = position;
        this.valueToAssign = valueToAssign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        return valueToAssign == action.valueToAssign &&
                   Objects.equals(position, action.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, valueToAssign);
    }

    @Override
    public String toString() {
        return "Action{" +
                   "position=" + position +
                   ", valueToAssign=" + valueToAssign +
                   '}';
    }
}
