package com.anttikarhu.webagogo;

/**
 * Turns.
 * 
 * @author Antti Karhu
 * 
 */
public enum Turn {
	/**
	 * It's black player's turn.
	 */
	BLACK,

	/**
	 * It's white player's turn.
	 */
	WHITE;

	/**
	 * Helper method for getting a proper stone.
	 * @return Position/stone that matches a turn.
	 */
	public Position getStone() {
		if (this == Turn.BLACK) {
			return Position.BLACK;
		} else {
			return Position.WHITE;
		}
	}

	/**
	 * Returns the other turn.
	 * @return Other turn.
	 */
	public Turn getOtherTurn() {
		if (this == Turn.BLACK) {
			return Turn.WHITE;
		} else {
			return Turn.BLACK;
		}
	}
}
