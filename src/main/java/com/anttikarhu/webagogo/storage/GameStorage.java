package com.anttikarhu.webagogo.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.anttikarhu.webagogo.Game;
import com.anttikarhu.webagogo.GameStatus;

/**
 * Game data storage.
 * 
 * @author Antti Karhu
 *
 */
@Component
public class GameStorage {
	/**
	 * Redis key for the games hash.
	 */
	private static final String GAMES_KEY = "webagogogames";

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private RedisTemplate<String, Game> redisTemplate;

	/**
	 * Creates a Redis template which can serialize our game objects
	 * 
	 * @return Template.
	 */
	@Bean
	public RedisTemplate<String, Game> gameRedisTemplate() {
		final RedisTemplate<String, Game> template = new RedisTemplate<String, Game>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Game.class));
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Game.class));
		return template;
	}

	/**
	 * Adds a new game to the store.
	 * 
	 * @param game
	 *            Game to be added.
	 */
	public void addNewGame(Game game) {
		redisTemplate.opsForHash().put(GAMES_KEY, game.getId(), game);
	}

	/**
	 * Adds a new game status to a game in the store.
	 * 
	 * @param gameStatus
	 *            Game status to be added.
	 */
	public void addStatus(GameStatus gameStatus) {
		Game game = (Game) redisTemplate.opsForHash().get(GAMES_KEY, gameStatus.getGameId());
		game.getGameStatuses().add(gameStatus);
		redisTemplate.opsForHash().put(GAMES_KEY, gameStatus.getGameId(), game);
	}

	/**
	 * Gets the game by id.
	 * 
	 * @param gameId
	 *            Game id.
	 * @return Game id.
	 */
	public Game getGame(String gameId) {
		Game game = (Game) redisTemplate.opsForHash().get(GAMES_KEY, gameId);
		return game;
	}
}
