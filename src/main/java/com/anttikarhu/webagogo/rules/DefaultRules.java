package com.anttikarhu.webagogo.rules;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import com.anttikarhu.webagogo.GameStatus;
import com.anttikarhu.webagogo.Position;
import com.anttikarhu.webagogo.Move;
import com.anttikarhu.webagogo.MoveType;
import com.anttikarhu.webagogo.Turn;

/**
 * Default rules with 19x19 board.
 * 
 * @author Antti Karhu
 * 
 */
@Component
@ConditionalOnMissingBean(type = "com.anttikarhu.webagogo.rules.TestRules")
public class DefaultRules implements Rules {

	@Override
	public int getBoardSize() {
		return 19;
	}

	@Override
	public GameStatus newGame() {
		GameStatus newGame = new GameStatus();

		newGame.setGameId(createId());
		newGame.setTimestamp(getTimestamp());
		newGame.setPlayersTurn(getStartingPlayer());
		newGame.setBoard(createInitialBoard());
		newGame.setPreviousMove(null);

		return newGame;
	}

	@Override
	public GameStatus move(GameStatus gameStatus, Move move) throws InvalidMoveException {
		if (move.getMoveType() == MoveType.PLACE_STONE) {
			return placeStone(gameStatus, move);
		} else if (move.getMoveType() == MoveType.SKIP) {
			return skip(gameStatus, move);
		} else {
			throw new InvalidMoveException();
		}
	}

	/**
	 * Creates game id.
	 * 
	 * @return UUID as string.
	 */
	protected String createId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Gets a timestamp.
	 * 
	 * @return Timestamp in milliseconds.
	 */
	protected long getTimestamp() {
		return System.currentTimeMillis();
	}

	/**
	 * Gets the starting player.
	 * 
	 * @return Player.
	 */
	protected Turn getStartingPlayer() {
		return Turn.BLACK;
	}

	/**
	 * Creates an initial board. Uses {@link #getBoardSize()} to get the size.
	 */
	protected Position[][] createInitialBoard() {
		Position[][] board = new Position[getBoardSize()][getBoardSize()];
		// Fill each line (y) with FREE positions
		for (int y = 0; y < board.length; y++) {
			Arrays.fill(board[y], Position.FREE);
		}
		return board;
	}

	/**
	 * Handles the stone placement.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Proposed move.
	 * @return Updated game status.
	 * @throws InvalidMoveException
	 *             Thrown if the move was invalid.
	 */
	protected GameStatus placeStone(GameStatus gameStatus, Move move) throws InvalidMoveException {
		// TODO Check situation, skip or place stone, alter status
		// For now we just accept the move as is
		Position position = Position.BLACK;
		if (gameStatus.getPlayersTurn() == Turn.WHITE) {
			position = Position.WHITE;
		}
		gameStatus.getBoard()[move.getY()][move.getX()] = position;

		// Update game status after valid move
		gameStatus.changeTurn();
		gameStatus.updateTimestamp();
		gameStatus.setPreviousMove(move);

		return gameStatus;
	}

	/**
	 * Handles the skip.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Proposed move.
	 * @return Updated game status.
	 * @throws InvalidMoveException
	 *             Thrown if the move was invalid.
	 */
	protected GameStatus skip(GameStatus gameStatus, Move move) throws InvalidMoveException {
		// TODO Check situation and end game if needed
		return gameStatus;
	}
}
