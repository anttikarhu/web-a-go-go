package com.anttikarhu.webagogo.rules;

import java.util.ArrayList;
import java.util.List;

import com.anttikarhu.webagogo.GameStatus;
import com.anttikarhu.webagogo.Move;
import com.anttikarhu.webagogo.Position;

/**
 * Go rules calculation helper class.
 * 
 * @author Antti Karhu
 *
 */
public class Go {
	/**
	 * Check if the move would cause repetition.
	 * 
	 * @param gameStatus
	 *            Game status.
	 * @param move
	 *            Proposed move.
	 * @return True if move would cause repetition, false if not.
	 */
	public static boolean causesRepetition(GameStatus gameStatus, Move move) {
		// Only need to check previous moves that captured one stone
		if (gameStatus.getPreviousMove() != null && gameStatus.getPreviousMove().getStonesCaptured() != null
				&& gameStatus.getPreviousMove().getStonesCaptured() == 1) {
			// Only need to test for adjacent intersections
			if (isAdjacent(gameStatus.getPreviousMove().getX(), gameStatus.getPreviousMove().getY(), move.getX(),
					move.getY())) {
				// Place stone
				Position[][] board = gameStatus.getBoard();
				board[move.getY()][move.getX()] = move.getTurn().getStone();

				// And test for liberty
				if (!hasLiberties(board, gameStatus.getPreviousMove().getX(), gameStatus.getPreviousMove().getY())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Not implemented.
	 * 
	 * @param gameStatus
	 * @param move
	 * @return
	 */
	public static boolean causesSuicide(GameStatus gameStatus, Move move) {
		// TODO Optional rule
		return false;
	}

	/**
	 * Checks if the position 1 is directly adjacent to position 2.
	 * 
	 * @param x1
	 *            Position 1 x.
	 * @param y1
	 *            Position 1 y.
	 * @param x2
	 *            Position 2 x.
	 * @param y2
	 *            Position 2 y.
	 * @return true if adjacent, false if no or invalid coordinates.
	 */
	public static boolean isAdjacent(int x1, int y1, int x2, int y2) {
		return (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 0) || (Math.abs(x1 - x2) == 0 && Math.abs(y1 - y2) == 1);
	}

	/**
	 * Checks if a position on a board has liberties.
	 * 
	 * @param board
	 *            Board.
	 * @param x
	 *            Position x coordinate.
	 * @param y
	 *            Position y coordinate.
	 * @return True if the position is a stone and it has liberties.
	 */
	public static boolean hasLiberties(Position[][] board, int x, int y) {
		Position position = board[y][x];
		boolean[][] checked = new boolean[board.length][board.length];

		// No need to check for empty positions
		if (position == Position.FREE) {
			return false;
		}

		// Check if adjacent or connected stones have liberty (free position
		// next to them)
		PositionInfo[] adjacent = getAdjacentPositions(board, x, y);

		for (PositionInfo p : adjacent) {
			if (checkLiberty(board, checked, position, x, y, p)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the adjacent positions for the coordinate.
	 * 
	 * @param board
	 *            Board.
	 * @param x
	 *            Position x coordinate.
	 * @param y
	 *            Position y coordinate.
	 * @return Adjacent positions in an array.
	 */
	public static PositionInfo[] getAdjacentPositions(Position[][] board, int x, int y) {
		List<PositionInfo> adjacent = new ArrayList<>();

		// Left
		if (x - 1 >= 0) {
			adjacent.add(new PositionInfo(board[y][x - 1], x - 1, y));
		}

		// Right
		if (x + 1 < board.length) {
			adjacent.add(new PositionInfo(board[y][x + 1], x + 1, y));
		}

		// Up
		if (y - 1 >= 0) {
			adjacent.add(new PositionInfo(board[y - 1][x], x, y - 1));
		}

		// Down
		if (y + 1 < board.length) {
			adjacent.add(new PositionInfo(board[y + 1][x], x, y + 1));
		}

		return adjacent.toArray(new PositionInfo[adjacent.size()]);
	}

	/**
	 * Check for liberties recursively.
	 * 
	 * @param board
	 *            Board.
	 * @param checked
	 *            boolean array of arrays of the same size as the board, used to
	 *            prevent back tracing,
	 * @param ownPosition
	 *            Own position/stone.
	 * @param ownX
	 *            Own x coordinate.
	 * @param ownY
	 *            Own y coordinate.
	 * @param adjacentPosition
	 *            The adjacent position.
	 * @return True if the own position has liberties.
	 */
	private static boolean checkLiberty(Position[][] board, boolean[][] checked, Position ownPosition, int ownX,
			int ownY, PositionInfo adjacentPosition) {
		if (adjacentPosition.getPosition() == Position.FREE) {
			return true;
		} else if (adjacentPosition.getPosition() == ownPosition) {
			PositionInfo[] adjacent = getAdjacentPositions(board, adjacentPosition.getX(), adjacentPosition.getY());
			if (!checked[adjacentPosition.getY()][adjacentPosition.getX()]) {
				for (PositionInfo p : adjacent) {
					checked[adjacentPosition.getY()][adjacentPosition.getX()] = true;
					if (checkLiberty(board, checked, adjacentPosition.getPosition(), adjacentPosition.getX(),
							adjacentPosition.getY(), p)) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
