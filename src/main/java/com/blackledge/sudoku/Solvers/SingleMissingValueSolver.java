package com.blackledge.sudoku.Solvers;

import com.blackledge.sudoku.Action;
import com.blackledge.sudoku.Cell;
import com.blackledge.sudoku.CellChangeListener;
import com.blackledge.sudoku.GameState;
import com.blackledge.sudoku.GameState.UniqueCellGroup;
import com.blackledge.sudoku.util.Position;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Only solves the most trivial problem:
 * where a single cell is missing a value from a row/column/grid
 * EG:
 * +-----+
 * |5 2 7|
 * |4   3|
 * |9 8 6|
 * +-----+
 *
 * Or
 *
 * +-----+-----+-----+
 * |1 2 3|4 5 6|7 8  |
 *
 */
public class SingleMissingValueSolver extends AbstractSolver {
    private final Set<Action> newActions = new HashSet<>();

    public SingleMissingValueSolver(GameState board) {
        super(board);
        registerListeners(board.getRows());
        registerListeners(board.getColumns());
        registerListeners(board.getGrids());
    }

    private class UniqueCellGroupingSolver implements CellChangeListener {
        private final Set<Position> unsetCells;
        private final Set<Integer> unsetvalues;

        UniqueCellGroupingSolver(UniqueCellGroup cells) {
            this.unsetCells = Arrays.stream(cells.getCells())
                                  .map(Cell::getPosition)
                                  .collect(Collectors.toSet());
            unsetvalues = IntStream.rangeClosed(1, 9)
                .boxed()
                .collect(Collectors.toSet());

            Arrays.stream(cells.getCells()).forEach(cell -> cell.addListener(this));

            for(Cell c : cells.getCells()) {
                Optional<Integer> value = c.getValue();
                if(value.isPresent()) {
                    unsetvalues.remove(value.get());
                    unsetCells.remove(c.getPosition());
                }
            }
            computePossibleAction();
        }

        @Override
        public void assigned(Cell cell, int value) {
            unsetCells.remove(cell.getPosition());
            unsetvalues.remove(value);
            computePossibleAction();
        }

        private void computePossibleAction() {
            if(unsetCells.size() == 1) {
                Position actionCell = unsetCells.iterator().next();
                Integer actionValue = unsetvalues.iterator().next();
                newActions.add(new Action(actionCell, actionValue));
            }
        }
    }

    private void registerListeners(UniqueCellGroup[] cellLists) {
        Arrays.stream(cellLists)
            .forEach(UniqueCellGroupingSolver::new);
    }

    @Override
    public Set<Action> getSuggestedMoves() {
        return newActions;
    }
}
