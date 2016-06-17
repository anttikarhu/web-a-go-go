package com.anttikarhu.webagogo;

import java.io.Serializable;

/**
 * Contains information about the move.
 * 
 * @author Antti Karhu
 * 
 */
@SuppressWarnings("serial")
public class Move implements Serializable {
	private MoveType moveType;

	private int x;

	private int y;

	private Turn turn;

	private String gameId;

	/**
	 * Gets the move type.
	 * 
	 * @return Move type.
	 */
	public MoveType getMoveType() {
		return moveType;
	}

	/**
	 * Sets the move type.
	 * 
	 * @param moveType
	 *            Move type.
	 */
	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	/**
	 * Gets the X coordinate of the stone position.
	 * 
	 * @return X coordinate, first position is at index 0.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X coordinate of the stone position.
	 * 
	 * @param x
	 *            X coordinate, first position is at index 0.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the Y coordinate of the stone position.
	 * 
	 * @return Y coordinate, first position is at index 0.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y coordinate of the stone position.
	 * 
	 * @param y
	 *            Y coordinate, first position is at index 0.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the value which indicates who's turn it is. Client does not need to provide this information.
	 * 
	 * @return Who's turn it is.
	 */
	public Turn getTurn() {
		return turn;
	}

	/**
	 * Sets the value which indicates who's turn it is. Client does not need to provide this information.
	 * 
	 * @param turn
	 *            Who's turn it is.
	 */
	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	/**
	 * Gets the game id.
	 * 
	 * @return Game id.
	 */
	public String getGameId() {
		return gameId;
	}

	/**
	 * Sets the game id.
	 * 
	 * @param gameId
	 *            Game id.
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
