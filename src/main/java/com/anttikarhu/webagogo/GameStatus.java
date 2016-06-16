package com.anttikarhu.webagogo;

/**
 * Current game status with game id, timestamp and the stuff about the game.
 * 
 * @author Antti Karhu
 * 
 */
public class GameStatus {

	private String gameId;

	private Long timestamp;

	private StonePlacement previousMove;

	private Turn playersTurn;

	private Position[][] board;

	/**
	 * Gets the game id.
	 * 
	 * @return Java UUID as a string.
	 */
	public String getGameId() {
		return gameId;
	}

	/**
	 * Sets the game id.
	 * 
	 * @param gameId
	 *            UUID as string.
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	/**
	 * Gets the timestamp when the latest move was done, or when the game was
	 * started if no moves have been done.
	 * 
	 * @return Timestamp in milliseconds.
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp when the latest move was done, or when the game was
	 * started if no moves have been done.
	 * 
	 * @param timestamp
	 *            Timestamp in milliseconds.
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the previous move.
	 * 
	 * @return Previous move.
	 */
	public StonePlacement getPreviousMove() {
		return previousMove;
	}

	/**
	 * Sets the previous move.
	 * 
	 * @param previousMove
	 *            Previous move.
	 */
	public void setPreviousMove(StonePlacement previousMove) {
		this.previousMove = previousMove;
	}

	/**
	 * Gets a value which defines who's turn is next.
	 * 
	 * @return Enum telling who's turn it is.
	 */
	public Turn getPlayersTurn() {
		return playersTurn;
	}

	/**
	 * Sets a value which defines who's turn is next.
	 * 
	 * @param playersTurn
	 *            Enum telling who's turn it is.
	 */
	public void setPlayersTurn(Turn playersTurn) {
		this.playersTurn = playersTurn;
	}

	/**
	 * Gets the board. Board is a grid of stone positions, or intersections.
	 * 
	 * @return Board arrays as [y][x]. All the indices contain a non-null Position value.
	 */
	public Position[][] getBoard() {
		return board;
	}

	/**
	 * Sets the board. Board is a grid of stone positions, or intersections.
	 * 
	 * @return Board arrays as [y][x]. All the indices must contain a non-null Position
	 *         value.
	 */
	public void setBoard(Position[][] board) {
		this.board = board;
	}
}
