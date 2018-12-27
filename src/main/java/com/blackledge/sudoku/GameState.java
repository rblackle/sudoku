package com.blackledge.sudoku;

import com.blackledge.sudoku.util.Position;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represents the overall state of a sudoku game board
 * This includes all cells in the game, stored in useful ways like per-row, per-column and per-3x3-grid
 */
public class GameState {
    public static final int gridDim = 3;
    public static final int dim = 9;

    private final Cell[] cells = generateCells();

    private Cell[] generateCells() {
        return IntStream.range(0, dim*dim)
            .mapToObj(i -> {
                int row = i / dim;
                int column = i % dim;
                return new Position(row, column);
            }).map(Cell::new)
            .toArray(Cell[]::new);
    }

    private final UniqueCellGroup[] rows;
    private final UniqueCellGroup[] columns;
    private final UniqueCellGroup[] grids;

    private GameState() {
        this.rows = buildUniqueGroups(cell -> cell.getPosition().getRow());
        this.columns = buildUniqueGroups(cell -> cell.getPosition().getColumn());
        this.grids = buildUniqueGroups(cell -> cell.getPosition().getGrid());
    }

    /**
     * Static factory method that creates a game with all blank cells
     */
    public static GameState createBlankBoard() {
        return new GameState();
    }

    /**
     * Parse a game board in this format
     * "+-----+-----+-----+\n"
     + "|5    |  8  |  4 9|\n"
     + "|     |5    |  3  |\n"
     + "|  6 7|3    |    1|\n"
     + "+-----+-----+-----+\n"
     + "|1 5  |     |     |\n"
     + "|     |2   8|     |\n"
     + "|     |     |  1 8|\n"
     + "+-----+-----+-----+\n"
     + "|7    |    4|1 5  |\n"
     + "|  3  |    2|     |\n"
     + "|4 9  |  5  |    3|\n"
     + "+-----+-----+-----+";
     * @param ascii ascii representation of the board
     */
    public static GameState loadFromAscii(String ascii) {
        String[] lines = ascii.split("\n");

        GameState board = createBlankBoard();

        List<Integer> lineNumbers = IntStream.iterate(0, i -> i + 1)
                                    // the 0th, 4th, 8th, 12th lines are grid spacing lines in our format
                                    .filter(i -> i % (gridDim + 1) != 0)
                                    .limit(dim)
                                    .boxed()
                                    .collect(Collectors.toList());

        for(int i = 0; i < dim; i++) {
            board.loadValuesFromAsciiLine(lines[lineNumbers.get(i)],  i);
        }

        return board;
    }

    // load from line like the following
    // 0123456789012345678
    // |5    |  8  |  4 9|
    private void loadValuesFromAsciiLine(String line, int lineNo) {
        for(int i = 0; i < dim; i++) {
            char c = line.charAt(i * 2 + 1);
            if (' ' == c) {
                rows[lineNo].getCells()[i].setValue(0);
            } else if(Character.isDigit(c) && Character.digit(c, 10) <= 9 && Character.digit(c, 10) >= 1) {
                int value = Character.digit(c, 10);
                rows[lineNo].getCells()[i].setValue(value);
            } else {
                throw new IllegalArgumentException("Illegal input on sudoku row " + lineNo + 1 + " column " + i + 1);
            }
        }
    }

    /**
     * Get a list of rows
     */
    public UniqueCellGroup[] getRows() {
        return rows;
    }

    /**
     * Get a list of columns
     */
    public UniqueCellGroup[] getColumns() {
        return columns;
    }

    /**
     * Get a list of grids
     */
    public UniqueCellGroup[] getGrids() {
        return grids;
    }

    /**
     * Get an array of all cells in the game, in left-to-right, top-to-bottom order
     */
    public Cell[] getCells() {
        return cells;
    }

    /**
     * A class that holds 9 cells all in the same "grouping", such as a row, column or grid
     */
    public static class UniqueCellGroup {
        private final Cell[] cells;

        private UniqueCellGroup(List<Cell> cells) {
            assert(cells.size() == dim);
            this.cells = cells.toArray(new Cell[0]);
        }

        public Cell[] getCells() {
            return cells;
        }
    }

    private UniqueCellGroup[] buildUniqueGroups(Function<Cell, Integer> discriminator) {
        return Arrays.stream(cells)
                   .collect(Collectors.groupingBy(discriminator))
                   .values().stream()
                   .map(UniqueCellGroup::new)
                   .toArray(UniqueCellGroup[]::new);
    }
}
