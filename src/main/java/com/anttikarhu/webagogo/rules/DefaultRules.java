package com.anttikarhu.webagogo.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import com.anttikarhu.webagogo.Game;
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
	public Game newGame() {
		Game newGame = new Game();
		newGame.setId(createId());

		GameStatus initialStatus = new GameStatus();
		initialStatus.setGameId(newGame.getId());
		initialStatus.setTimestamp(getTimestamp());
		initialStatus.setPlayersTurn(getStartingPlayer());
		initialStatus.setBoard(createInitialBoard());
		initialStatus.setPreviousMove(null);

		newGame.setGameStatuses(new ArrayList<>(Arrays.asList(initialStatus)));

		return newGame;
	}

	@Override
	public GameStatus move(Game game, GameStatus gameStatus, Move move) throws InvalidMoveException {
		if (move.getMoveType() == MoveType.PLAY) {
			return play(gameStatus, move);
		} else if (move.getMoveType() == MoveType.PASS) {
			return pass(gameStatus, move);
		} else if (move.getMoveType() == MoveType.REMOVE_STONE) {
			return removeStone(gameStatus, move);
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
	 * Handles the play.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Proposed move.
	 * @return Updated game status.
	 * @throws InvalidMoveException
	 *             Thrown if the move was invalid.
	 */
	protected GameStatus play(GameStatus gameStatus, Move move) throws InvalidMoveException {
		// Check for suicide
		if (Go.causesSuicide(gameStatus, move)) {
			throw new InvalidMoveException();
		}

		// Check for repetition
		if (Go.causesRepetition(gameStatus, move)) {
			throw new InvalidMoveException();
		}

		// Put stone
		Position position = Position.BLACK;
		if (gameStatus.getPlayersTurn() == Turn.WHITE) {
			position = Position.WHITE;
		}
		gameStatus.getBoard()[move.getY()][move.getX()] = position;

		// Remove opponent's stones with no liberty
		int removeCount = Go.removeStonesWithNoLiberty(gameStatus.getBoard(), move.getTurn().getOtherTurn());

		// Remove own stones with no liberty
		removeCount += Go.removeStonesWithNoLiberty(gameStatus.getBoard(), move.getTurn());

		// Update game status after valid move
		updateStatus(gameStatus, move);
		gameStatus.getPreviousMove().setStonesCaptured(removeCount);

		return gameStatus;
	}

	/**
	 * Handles the pass.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Proposed move.
	 * @return Updated game status.
	 * @throws InvalidMoveException
	 *             Thrown if the move was invalid.
	 */
	protected GameStatus pass(GameStatus gameStatus, Move move) throws InvalidMoveException {
		// Check situation and end game if needed
		if (gameStatus.getPreviousMove() != null && gameStatus.getPreviousMove().getMoveType() == MoveType.PASS) {
			// TODO Game ended, end it properly
			return gameStatus;
		} else {
			updateStatus(gameStatus, move);
		}

		return gameStatus;
	}

	/**
	 * Removes a stone from board.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Proposed move.
	 * @return Updated game status.
	 * @throws InvalidMoveException
	 *             Thrown if the move was invalid.
	 */
	protected GameStatus removeStone(GameStatus gameStatus, Move move) throws InvalidMoveException {
		Position currentPosition = gameStatus.getBoard()[move.getY()][move.getX()];
		if (currentPosition == Position.BLACK || currentPosition == Position.WHITE) {
			gameStatus.getBoard()[move.getY()][move.getX()] = Position.FREE;
		}

		// Update game status, but don't change turn
		updateStatus(gameStatus, move, false);

		return gameStatus;
	}

	/**
	 * Updates the game status.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Move.
	 */
	private void updateStatus(GameStatus gameStatus, Move move) {
		updateStatus(gameStatus, move, true);
	}

	/**
	 * Updates the game status.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Move.
	 * @param changeTurn
	 *            Defines if the turn should change.
	 */
	private void updateStatus(GameStatus gameStatus, Move move, boolean changeTurn) {
		move.setTurn(gameStatus.getPlayersTurn());
		gameStatus.setPreviousMove(move);
		if (changeTurn) {
			gameStatus.changeTurn();
		}
		gameStatus.setTimestamp(getTimestamp());
	}
}
