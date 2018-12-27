package com.blackledge.sudoku.Solvers;

import static org.junit.jupiter.api.Assertions.*;

import com.blackledge.sudoku.Action;
import com.blackledge.sudoku.GameState;
import com.blackledge.sudoku.util.Position;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class SingleMissingValueSolverTest {

    @Test
    void testSuggestedMoves_rowOnLoad() {
        String board = ""
                           + "+-----+-----+-----+\n"
                           + "|1 2 3|4 5 6|7 8  |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+";
        GameState gameState = GameState.loadFromAscii(board);
        SingleMissingValueSolver solver = new SingleMissingValueSolver(gameState);

        Action expected = new Action(new Position(0, 8), 9);
        assertEquals(Collections.singleton(expected), solver.getSuggestedMoves());
    }

    @Test
    void testSuggestedMoves_rowOnValueSet() {
        String board = ""
                           + "+-----+-----+-----+\n"
                           + "|1 2 3|4 5 6|7    |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+";
        GameState gameState = GameState.loadFromAscii(board);
        SingleMissingValueSolver solver = new SingleMissingValueSolver(gameState);

        assertTrue(solver.getSuggestedMoves().isEmpty());

        gameState.getRows()[0].getCells()[7].setValue(8);

        Action expected = new Action(new Position(0, 8), 9);
        assertEquals(Collections.singleton(expected), solver.getSuggestedMoves());
    }

    @Test
    void testSuggestedMoves_columnOnLoad() {
        String board = ""
                           + "+-----+-----+-----+\n"
                           + "|1    |     |     |\n"
                           + "|2    |     |     |\n"
                           + "|3    |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|4    |     |     |\n"
                           + "|5    |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|7    |     |     |\n"
                           + "|8    |     |     |\n"
                           + "|9    |     |     |\n"
                           + "+-----+-----+-----+";
        GameState gameState = GameState.loadFromAscii(board);
        SingleMissingValueSolver solver = new SingleMissingValueSolver(gameState);

        Action expected = new Action(new Position(5, 0), 6);
        assertEquals(Collections.singleton(expected), solver.getSuggestedMoves());
    }

    @Test
    void testSuggestedMoves_gridOnLoad() {
        String board = ""
                           + "+-----+-----+-----+\n"
                           + "|5 2 7|     |     |\n"
                           + "|4 1 3|     |     |\n"
                           + "|9 8  |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+";
        GameState gameState = GameState.loadFromAscii(board);
        SingleMissingValueSolver solver = new SingleMissingValueSolver(gameState);

        Action expected = new Action(new Position(2, 2), 6);
        assertEquals(Collections.singleton(expected), solver.getSuggestedMoves());
    }

    @Test
    void testSuggestedMoves_multipleGridOnLoad() {
        String board = ""
                           + "+-----+-----+-----+\n"
                           + "|5 2  |     |     |\n"
                           + "|4 1 3|2 6 7|9 8  |\n"
                           + "|9 8 6|     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+";
        GameState gameState = GameState.loadFromAscii(board);
        SingleMissingValueSolver solver = new SingleMissingValueSolver(gameState);

        Set<Action> expected = Stream.of(
            new Action(new Position(0, 2), 7),
            new Action(new Position(1, 8), 5)
        ).collect(Collectors.toSet());
        assertEquals(expected, solver.getSuggestedMoves());
    }

    @Test
    void testSuggestedMoves_deDuplicateSuggestionGridOnLoad() {
        String board = ""
                           + "+-----+-----+-----+\n"
                           + "|5 2 7|     |     |\n"
                           + "|4   3|2 6 7|9 8 5|\n"
                           + "|9 8 6|     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+";
        GameState gameState = GameState.loadFromAscii(board);
        SingleMissingValueSolver solver = new SingleMissingValueSolver(gameState);

        Set<Action> expected = Stream.of(
            new Action(new Position(1, 1), 1)
        ).collect(Collectors.toSet());
        assertEquals(expected, solver.getSuggestedMoves());
    }

    @Test
    void testSuggestedMoves_multipleSuggestions() {
        String board = ""
                           + "+-----+-----+-----+\n"
                           + "|5 2 7|     |     |\n"
                           + "|4   3|     |     |\n"
                           + "|9 8 6|     |     |\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |5   7|\n"
                           + "|     |     |4 1 3|\n"
                           + "|     |     |9 8 6|\n"
                           + "+-----+-----+-----+\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "|     |     |     |\n"
                           + "+-----+-----+-----+";
        GameState gameState = GameState.loadFromAscii(board);
        SingleMissingValueSolver solver = new SingleMissingValueSolver(gameState);

        Set<Action> expected = Stream.of(
            new Action(new Position(1, 1), 1),
            new Action(new Position(3, 7), 2)
        ).collect(Collectors.toSet());
        assertEquals(expected, solver.getSuggestedMoves());
    }
}