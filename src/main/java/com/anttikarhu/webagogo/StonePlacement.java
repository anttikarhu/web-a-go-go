package com.anttikarhu.webagogo;

/**
 * Contains information about the stone placement.
 * 
 * @author Antti Karhu
 * 
 */
public class StonePlacement {
	private int x;

	private int y;

	private String gameId;

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
