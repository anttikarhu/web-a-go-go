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
	 * @param move Proposed move.
	 * @return Updated game status.
	 */
	@RequestMapping("/makeMove")
	public GameStatus makeMove(Move move) {
		// Get game status from storage by id
		Game game = gameStorage.getGame(move.getGameId());
		GameStatus gameStatus = game.getGameStatuses().get(game.getGameStatuses().size() - 1);

		if (gameStatus != null) {
			try {
				gameStatus = rules.move(game, gameStatus, move);
			} catch (InvalidMoveException e) {
				// TODO Add error message to game status
			}

			// Store the updated game status
			gameStorage.addStatus(gameStatus);
		}

		return gameStatus;
	}

	/**
	 * Application entry point.
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebAGoGoApplication.class, args);
	}
}
