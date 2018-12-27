package com.blackledge.sudoku.util;

import com.blackledge.sudoku.GameState;
import java.util.Objects;

/**
 * Immutable POJO representing a single square in a sudoku game (eg row 3 column 5).
 * This class is zero indexed
 */
public final class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getGrid() {
        int gridRow = row / GameState.gridDim;
        int gridColumn = column / GameState.gridDim;
        return (gridRow * GameState.gridDim) + gridColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row &&
                   column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Position{" +
                   "row=" + row +
                   ", column=" + column +
                   '}';
    }
}
