package com.anttikarhu.webagogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anttikarhu.webagogo.rules.InvalidMoveException;
import com.anttikarhu.webagogo.rules.Rules;
import com.anttikarhu.webagogo.storage.GameStorage;

/**
 * Go game application. Provides web UI with a fresh game board, rule checking
 * and board update, and logs the events to database for later use.
 * 
 * @author Antti Karhu
 * 
 */
@SpringBootApplication
@RestController
public class WebAGoGoApplication {

	@Autowired
	private Rules rules;

	@Autowired
	private GameStorage gameStorage;

	/**
	 * Creates a new game and returns initial game status.
	 * 
	 * @return Initial game status.
	 */
	@RequestMapping("/newGame")
	public GameStatus newGame() {
		Game newGame = rules.newGame();
		// Store the initial game status
		gameStorage.addNewGame(newGame);
		return newGame.getGameStatuses().get(0);
	}

	/**
	 * Makes a move.
	 * 
	 * @param move
	 *            Proposed move.
	 * @return Updated game status.
	 */
	@RequestMapping("/makeMove")
	public GameStatus makeMove(Move move) {
		// Get game status from storage by id
		Game game = gameStorage.getGame(move.getGameId());
		GameStatus currentStatus = game.getGameStatuses().get(game.getGameStatuses().size() - 1);

		if (currentStatus != null) {
			try {
				move.setTurn(currentStatus.getPlayersTurn());
				GameStatus newStatus = rules.move(game, currentStatus, move);

				// Store the updated game status
				gameStorage.addStatus(newStatus);
				return newStatus;
			} catch (InvalidMoveException e) {
				System.err.println("Invalid move, game status does not change");
			}
		}

		return currentStatus;
	}

	/**
	 * Application entry point.
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebAGoGoApplication.class, args);
	}
}
