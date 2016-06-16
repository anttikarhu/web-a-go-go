package com.anttikarhu.webagogo.rules;

import com.anttikarhu.webagogo.GameStatus;
import com.anttikarhu.webagogo.StonePlacement;

/**
 * Interface for Go rules. THe rules object will know how to create a new game,
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
	 * @return Initial game status.
	 */
	public GameStatus newGame();

	/**
	 * Checks the stone placement against the current game status, and either
	 * returns a new status, or throws.
	 * 
	 * @param gameStatus
	 *            Current game status.
	 * @param stonePlacement
	 *            Proposed stone placement.
	 * @return New game status if the placement was legal.
	 * @throws InvalidMoveException
	 *             Thrown if the stone placement was not legal.
	 */
	public GameStatus placeStone(GameStatus gameStatus, StonePlacement stonePlacement) throws InvalidMoveException;
}
