package com.anttikarhu.webagogo.rules;

import com.anttikarhu.webagogo.Game;
import com.anttikarhu.webagogo.GameStatus;
import com.anttikarhu.webagogo.Move;

/**
 * Interface for Go rules. The rules object will know how to create a new game,
 * and how the stone placement will affect the current game.
 * 
 * @author Antti Karhu
 *
 */
public interface Rules {
	/**
	 * Gets the number of stone positions or intersections, in one axis. Board
	 * is getBoardSize() x getBoardSize() in size.
	 * 
	 * @return Board size.
	 */
	public int getBoardSize();

	/**
	 * Creates a fresh game board and other needed data.
	 * 
	 * @return Game with initial game status.
	 */
	public Game newGame();

	/**
	 * Checks the move against the current game status, and either
	 * returns a new status, or throws.
	 * 
	 * @param gameStatus
	 *            Current game status.
	 * @param move
	 *            Proposed move, like skip or stone placement.
	 * @return New game status if the move was legal.
	 * @throws InvalidMoveException
	 *             Thrown if the move was not legal.
	 */
	public GameStatus move(GameStatus gameStatus, Move move) throws InvalidMoveException;
}
