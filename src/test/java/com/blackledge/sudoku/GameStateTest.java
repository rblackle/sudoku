package com.blackledge.sudoku;

import static org.junit.jupiter.api.Assertions.*;

import com.blackledge.sudoku.GameState.UniqueCellGroup;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class GameStateTest {

    @Test
    public void testLoadingBoard_rows() {
        String board = ""
                           + "+-----+-----+-----+\n"
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

        GameState gameState = GameState.loadFromAscii(board);
        UniqueCellGroup[] rows = gameState.getRows();

        assertValuesEqual(rows[0], new int[] {5, 0, 0,   0, 8, 0,   0, 4, 9});
        assertValuesEqual(rows[1], new int[] {0, 0, 0,   5, 0, 0,   0, 3, 0});
        assertValuesEqual(rows[2], new int[] {0, 6, 7,   3, 0, 0,   0, 0, 1});

        assertValuesEqual(rows[3], new int[] {1, 5, 0,   0, 0, 0,   0, 0, 0});
        assertValuesEqual(rows[4], new int[] {0, 0, 0,   2, 0, 8,   0, 0, 0});
        assertValuesEqual(rows[5], new int[] {0, 0, 0,   0, 0, 0,   0, 1, 8});

        assertValuesEqual(rows[6], new int[] {7, 0, 0,   0, 0, 4,   1, 5, 0});
        assertValuesEqual(rows[7], new int[] {0, 3, 0,   0, 0, 2,   0, 0, 0});
        assertValuesEqual(rows[8], new int[] {4, 9, 0,   0, 5, 0,   0, 0, 3});
    }

    @Test
    public void testLoadingBoard_columns() {
        String board = ""
                           + "+-----+-----+-----+\n"
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

        GameState gameState = GameState.loadFromAscii(board);
        UniqueCellGroup[] rows = gameState.getColumns();

        assertValuesEqual(rows[0], new int[] {5, 0, 0,   1, 0, 0,   7, 0, 4});
        assertValuesEqual(rows[1], new int[] {0, 0, 6,   5, 0, 0,   0, 3, 9});
        assertValuesEqual(rows[2], new int[] {0, 0, 7,   0, 0, 0,   0, 0, 0});

        assertValuesEqual(rows[3], new int[] {0, 5, 3,   0, 2, 0,   0, 0, 0});
        assertValuesEqual(rows[4], new int[] {8, 0, 0,   0, 0, 0,   0, 0, 5});
        assertValuesEqual(rows[5], new int[] {0, 0, 0,   0, 8, 0,   4, 2, 0});

        assertValuesEqual(rows[6], new int[] {0, 0, 0,   0, 0, 0,   1, 0, 0});
        assertValuesEqual(rows[7], new int[] {4, 3, 0,   0, 0, 1,   5, 0, 0});
        assertValuesEqual(rows[8], new int[] {9, 0, 1,   0, 0, 8,   0, 0, 3});
    }

    @Test
    public void testLoadingBoard_grids() {
        String board = ""
                           + "+-----+-----+-----+\n"
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

        GameState gameState = GameState.loadFromAscii(board);
        UniqueCellGroup[] rows = gameState.getGrids();

        assertValuesEqual(rows[0], new int[]{
            5, 0, 0,
            0, 0, 0,
            0, 6, 7
        });
        assertValuesEqual(rows[1], new int[]{
            0, 8, 0,
            5, 0, 0,
            3, 0, 0
        });
        assertValuesEqual(rows[2], new int[]{
            0, 4, 9,
            0, 3, 0,
            0, 0, 1
        });

        assertValuesEqual(rows[3], new int[] {
            1, 5, 0,
            0, 0, 0,
            0, 0, 0
        });
        assertValuesEqual(rows[4], new int[] {
            0, 0, 0,
            2, 0, 8,
            0, 0, 0
        });
        assertValuesEqual(rows[5], new int[] {
            0, 0, 0,
            0, 0, 0,
            0, 1, 8
        });

        assertValuesEqual(rows[6], new int[] {
            7, 0, 0,
            0, 3, 0,
            4, 9, 0
        });
        assertValuesEqual(rows[7], new int[] {
            0, 0, 4,
            0, 0, 2,
            0, 5, 0
        });
        assertValuesEqual(rows[8], new int[] {
            1, 5, 0,
            0, 0, 0,
            0, 0, 3
        });
    }

    private void assertValuesEqual(UniqueCellGroup cells, int[] values) {
        for(int i = 0; i < cells.getCells().length; i++) {
            Optional<Integer> value = Optional.of(values[i]).filter(val -> val != 0);
            assertEquals(value, cells.getCells()[i].getValue());
        }
    }
}