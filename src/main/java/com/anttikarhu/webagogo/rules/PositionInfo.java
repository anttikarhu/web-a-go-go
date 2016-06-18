package com.anttikarhu.webagogo.rules;

import com.anttikarhu.webagogo.Position;

/**
 * Wrapper class for position and it's coordinates. Used in calculcations.
 * 
 * @author Antti Karhu
 *
 */
public class PositionInfo {
	private Position position;

	private int x;

	private int y;

	/**
	 * Constructor.
	 * 
	 * @param position
	 *            Position.
	 * @param x
	 *            X coordinate.
	 * @param y
	 *            Y coordinate.
	 */
	public PositionInfo(Position position, int x, int y) {
		this.position = position;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the position.
	 * 
	 * @return Position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the x coordinate.
	 * 
	 * @return X coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate.
	 * 
	 * @return Y coordinate.
	 */
	public int getY() {
		return y;
	}
}
