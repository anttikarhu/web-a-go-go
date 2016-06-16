package com.anttikarhu.webagogo.rules;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import com.anttikarhu.webagogo.GameStatus;
import com.anttikarhu.webagogo.Position;
import com.anttikarhu.webagogo.StonePlacement;
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
	public GameStatus placeStone(GameStatus gameStatus, StonePlacement stonePlacement) throws InvalidMoveException {
		// TODO Check situation, place stone, alter status
		return gameStatus;
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
		for (int x = 0; x < board.length; x++) {
			Arrays.fill(board[x], Position.FREE);
		}
		return board;
	}
}
