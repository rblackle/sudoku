package com.blackledge.sudoku.Solvers;

import com.blackledge.sudoku.Action;
import com.blackledge.sudoku.Cell;
import com.blackledge.sudoku.CellChangeListener;
import com.blackledge.sudoku.GameState;
import com.blackledge.sudoku.GameState.UniqueCellGroup;
import com.blackledge.sudoku.util.Position;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * When a specific cell can only contain a single number based on the other cells in the row, column and grid. eg:
 * +-----+-----+-----+
 * |5   7|     |     |
 * |4[1]3|2 6 7|9    |
 * |9    |     |     |
 * +-----+-----+-----+
 * |  6  |     |     |
 * |     |     |     |
 * |     |     |     |
 * +-----+-----+-----+
 * |     |     |     |
 * |  8  |     |     |
 * |     |     |     |
 * +-----+-----+-----+
 */
public class SingleOptionSolver extends AbstractSolver {
    // True for every number in the range
    private final BitSet[] rows;
    private final BitSet[] columns;
    private final BitSet[] grids;

    private final Set<Action> newActions = new HashSet<>();

    protected SingleOptionSolver(GameState board) {
        super(board);
        this.rows = createBitsets(board.getRows());
        this.columns = createBitsets(board.getColumns());
        this.grids = createBitsets(board.getGrids());

        Arrays.stream(board.getCells())
            .filter(cell -> !cell.getValue().isPresent())
            .forEach(SingleOptionCellSolver::new);
    }

    private BitSet[] createBitsets(UniqueCellGroup[] group) {
        return Arrays.stream(group)
                   .map(this::createBitset)
                   .toArray(BitSet[]::new);
    }

    private BitSet createBitset(UniqueCellGroup group) {
        BitSet bitSet = new BitSet(9);
        Arrays.stream(group.getCells())
            .map(Cell::getValue)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(i -> i-1)
            .forEach(bitSet::set);
        return bitSet;
    }

    private class SingleOptionCellSolver implements CellChangeListener {

        private SingleOptionCellSolver(Cell cell) {
            cell.addListener(this);
            computePossibleAction(cell.getPosition());
        }

        @Override
        public void assigned(Cell cell, int value) {
            Position position = cell.getPosition();
            rows[position.getRow()].set(value-1);
            columns[position.getColumn()].set(value-1);
            grids[position.getGrid()].set(value-1);

            // Iterate through all other cells in this row, column, and grid
            Stream.of(
                board.getRows()[position.getRow()],
                board.getColumns()[position.getColumn()],
                board.getGrids()[position.getGrid()]
            )
                .map(UniqueCellGroup::getCells)
                .flatMap(Arrays::stream)
                // Only look for new actions on cells without values already
                .filter(c -> !c.getValue().isPresent())
                .map(Cell::getPosition)
                .distinct()
                .forEach(this::computePossibleAction);
        }

        /**
         * Determine if an action of this type can be performed for the given position
         * @param p position to find an action for
         */
        private void computePossibleAction(Position p) {
            BitSet result = new BitSet();
            result.or(rows[p.getRow()]);
            result.or(columns[p.getColumn()]);
            result.or(grids[p.getGrid()]);
            if(result.cardinality() == 8) {
                newActions.add(new Action(p, result.nextClearBit(0) + 1));
            }
        }
    }

    @Override
    public Set<Action> getSuggestedMoves() {
        return newActions;
    }
}
