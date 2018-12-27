package com.blackledge.sudoku.Solvers;

import com.blackledge.sudoku.Action;
import com.blackledge.sudoku.GameState;
import java.util.Set;

/**
 * Base class of all solving strategies.
 * Any solver is expected to be initialized with a board, which it will use to subscribe to state updates.
 */
public abstract class AbstractSolver {
    protected final GameState board;

    protected AbstractSolver(GameState board) {
        this.board = board;
    }

    /**
     * Get the suggested moves according to the initial board state
     * @return
     */
    public abstract Set<Action> getSuggestedMoves();
}
