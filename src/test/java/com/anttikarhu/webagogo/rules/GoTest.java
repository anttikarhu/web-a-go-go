package com.anttikarhu.webagogo.rules;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.anttikarhu.webagogo.Game;
import com.anttikarhu.webagogo.GameStatus;
import com.anttikarhu.webagogo.Move;
import com.anttikarhu.webagogo.Position;
import com.anttikarhu.webagogo.Turn;

/**
 * Go calculation tests.
 * 
 * @author Antti Karhu
 *
 */
public class GoTest {

	@Test
	public void testAdjacent() throws Exception {
		assertFalse(Go.isAdjacent(0, 0, 0, 0));
		assertFalse(Go.isAdjacent(0, 0, 4, 4));
		assertFalse(Go.isAdjacent(1, 2, 4, 4));
		assertFalse(Go.isAdjacent(2, 2, 3, 3));
		assertFalse(Go.isAdjacent(2, 2, 1, 1));

		assertTrue(Go.isAdjacent(2, 2, 1, 2));
		assertTrue(Go.isAdjacent(1, 2, 1, 1));
		assertTrue(Go.isAdjacent(0, 0, 0, 1));
	}

	@Test
	public void testGetAdjacent() throws Exception {
		Position[][] board = { { Position.BLACK, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.BLACK, Position.WHITE, Position.BLACK, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.BLACK, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.FREE, Position.FREE, Position.FREE } };
		assertThat(Go.getAdjacentPositions(board, 0, 0).length, is(2));
		assertThat(Go.getAdjacentPositions(board, 2, 3).length, is(4));
		assertThat(Go.getAdjacentPositions(board, 2, 4).length, is(3));
		assertThat(Go.getAdjacentPositions(board, 4, 4).length, is(2));
	}

	@Test
	public void testLiberty() throws Exception {
		Position[][] board = { { Position.BLACK, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.BLACK, Position.WHITE, Position.BLACK, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.BLACK, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.FREE, Position.FREE, Position.FREE } };

		// Liberty table for the board (free positions are marked to have no
		// liberty)
		// 1 1 1 0 0
		// 1 0 0 1 0
		// 0 1 1 0 0
		// 0 0 1 1 0
		// 0 0 0 0 0
		assertTrue(Go.hasLiberties(board, 0, 0));
		assertTrue(Go.hasLiberties(board, 1, 0));
		assertTrue(Go.hasLiberties(board, 2, 0));
		assertFalse(Go.hasLiberties(board, 3, 0));
		assertFalse(Go.hasLiberties(board, 4, 0));

		assertTrue(Go.hasLiberties(board, 0, 1));
		assertFalse(Go.hasLiberties(board, 1, 1));
		assertFalse(Go.hasLiberties(board, 2, 1));
		assertTrue(Go.hasLiberties(board, 3, 1));
		assertFalse(Go.hasLiberties(board, 4, 1));

		assertFalse(Go.hasLiberties(board, 0, 2));
		assertTrue(Go.hasLiberties(board, 1, 2));
		assertTrue(Go.hasLiberties(board, 2, 2));
		assertFalse(Go.hasLiberties(board, 3, 2));
		assertFalse(Go.hasLiberties(board, 4, 2));

		assertFalse(Go.hasLiberties(board, 0, 3));
		assertFalse(Go.hasLiberties(board, 1, 3));
		assertTrue(Go.hasLiberties(board, 2, 3));
		assertTrue(Go.hasLiberties(board, 3, 3));
		assertFalse(Go.hasLiberties(board, 4, 3));

		assertFalse(Go.hasLiberties(board, 0, 4));
		assertFalse(Go.hasLiberties(board, 1, 4));
		assertFalse(Go.hasLiberties(board, 2, 4));
		assertFalse(Go.hasLiberties(board, 3, 4));
		assertFalse(Go.hasLiberties(board, 4, 4));
	}

	@Test
	public void testCausesRepetitionPositive() throws Exception {
		// 'One may not capture just one stone, if that stone was played on the
		// previous move, and that move also
		// captured just one stone.'

		Rules rules = new TestRules();
		Game game = rules.newGame();

		// Add previous status and move
		GameStatus previousStatus = new GameStatus();
		previousStatus.setPlayersTurn(Turn.BLACK);
		Position[][] board = { { Position.FREE, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.BLACK, Position.FREE, Position.BLACK, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.BLACK, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.FREE, Position.FREE, Position.FREE } };
		previousStatus.setBoard(board);

		Move previousMove = new Move();
		previousMove.setStonesCaptured(1);
		previousMove.setX(2);
		previousMove.setY(1);
		previousMove.setTurn(Turn.BLACK);
		previousStatus.setPreviousMove(previousMove);
		game.getGameStatuses().add(previousStatus);

		// New move
		Move newMove = new Move();
		newMove.setTurn(Turn.WHITE);
		newMove.setX(1);
		newMove.setY(1);

		assertTrue(Go.causesRepetition(previousStatus, newMove));
	}

	@Test
	public void testCausesRepetitionNegative() throws Exception {
		Rules rules = new TestRules();
		Game game = rules.newGame();

		// Add previous status and move
		GameStatus previousStatus = new GameStatus();
		previousStatus.setPlayersTurn(Turn.BLACK);
		Position[][] board = { { Position.FREE, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.BLACK, Position.WHITE, Position.FREE, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.BLACK, Position.WHITE, Position.FREE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.BLACK, Position.WHITE, Position.FREE },
				{ Position.FREE, Position.FREE, Position.FREE, Position.FREE, Position.FREE } };
		previousStatus.setBoard(board);

		Move previousMove = new Move();
		previousMove.setStonesCaptured(0);
		previousMove.setX(3);
		previousMove.setY(3);
		previousMove.setTurn(Turn.WHITE);
		previousStatus.setPreviousMove(previousMove);
		game.getGameStatuses().add(previousStatus);

		// New move
		Move newMove = new Move();
		newMove.setTurn(Turn.BLACK);
		newMove.setX(2);
		newMove.setY(1);

		assertFalse(Go.causesRepetition(previousStatus, newMove));
	}

	@Test
	public void testCausesSuicidePositive() throws Exception {
		// TODO Optional rule
	}

	@Test
	public void testCausesSuicideNegative() throws Exception {
		// TODO Optional rule
	}
}
