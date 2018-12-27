package com.blackledge.sudoku;

/**
 * Basic interface for a subscriber for value changes to cells
 */
public interface CellChangeListener {
    /**
     * Callback to implement for when a cell is updated to a specific value.
     * Values are as they are displayed to the user (values 1-9, 1 indexed)
     * @param cell the cell being updated
     * @param value the value the cell is being updated to
     */
    void assigned(Cell cell, int value);
}
