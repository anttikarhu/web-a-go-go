package com.anttikarhu.webagogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anttikarhu.webagogo.rules.Rules;

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

	/**
	 * Creates a new game.
	 */
	@RequestMapping("/newGame")
	public GameStatus newGame() {
		return rules.newGame();
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
