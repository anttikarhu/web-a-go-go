package com.anttikarhu.webagogo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anttikarhu.webagogo.rules.InvalidMoveException;
import com.anttikarhu.webagogo.rules.Rules;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	// Temporary storage for game statuses, will be removed later
	private Map<String, GameStatus> gameStatuses = new HashMap<>();

	/**
	 * Creates a new game.
	 */
	@RequestMapping("/newGame")
	public GameStatus newGame() {
		GameStatus newGame = rules.newGame();
		// TODO Store the initial game status
		gameStatuses.put(newGame.getGameId(), newGame);
		return newGame;
	}

	@RequestMapping("/makeMove")
	public GameStatus makeMove(Move move) {
		// TODO Get game status from storage by id
		GameStatus gameStatus = gameStatuses.get(move.getGameId());

		if (gameStatus != null) {
			try {
				gameStatus = rules.move(gameStatus, move);
			} catch (InvalidMoveException e) {
				// TODO Add error message to game status
			}

			// TODO Store the updated game status
		}

		return gameStatus;
	}

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings("unused")
	private void logToDb(String id) {
		// TODO Just for reference, remove later
		System.out.println("Sent an id: " + id);

		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		if (!stringRedisTemplate.hasKey(id)) {
			valueOperations.set(id, Long.toString(System.currentTimeMillis()));
		}
	}

	/**
	 * Application entry point.
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebAGoGoApplication.class, args);
	}
}
