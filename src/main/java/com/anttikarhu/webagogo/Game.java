package com.anttikarhu.webagogo;

import java.io.Serializable;
import java.util.List;

/**
 * Game object.
 * 
 * @author Antti Karhu
 *
 */
@SuppressWarnings("serial")
public class Game implements Serializable {
	private String id;

	private List<GameStatus> gameStatuses;

	/**
	 * Gets the game id.
	 * 
	 * @return Game id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the game id.
	 * 
	 * @param id
	 *            The game id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the game statuses list.
	 * 
	 * @return Game statuses.
	 */
	public List<GameStatus> getGameStatuses() {
		return gameStatuses;
	}

	/**
	 * Sets the game statuses list.
	 * 
	 * @param gameStatuses
	 *            Game statuses.
	 */
	public void setGameStatuses(List<GameStatus> gameStatuses) {
		this.gameStatuses = gameStatuses;
	}
}
