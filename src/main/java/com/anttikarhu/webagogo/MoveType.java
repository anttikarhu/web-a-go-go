package com.anttikarhu.webagogo;

/**
 * Types of moves player can perform.
 * 
 * @author Antti Karhu
 *
 */
public enum MoveType {
	/**
	 * Player places a stone.
	 */
	PLAY,

	/**
	 * Player skips a turn.
	 */
	PASS,

	/**
	 * Mode for removing stones, used during development
	 */
	REMOVE_STONE
}
