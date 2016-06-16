package com.anttikarhu.webagogo;

/**
 * Stone position.
 * 
 * @author Antti Karhu
 * 
 */
public enum Position {
	/**
	 * Position is free and can accept a stone.
	 */
	FREE,

	/**
	 * There's a black stone in the position.
	 */
	BLACK,

	/**
	 * There's a white stone in the position.
	 */
	WHITE,

	/**
	 * There is no stone at the position, but it's blocked by black player.
	 */
	BLOCKED_BY_BLACK,

	/**
	 * There is no stone at the position, but it's blocked by white player.
	 */
	BLOCKED_BY_WHITE
}
