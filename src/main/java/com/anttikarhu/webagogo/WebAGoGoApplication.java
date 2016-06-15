package com.anttikarhu.webagogo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebAGoGoApplication {
	@RequestMapping("/placePiece")
	public Map<String, Object> placePiece() {
		// TODO The id comes as a parameter along with the piece placement info
		// Game id is got when game is started, and it's supposed to be stored in the client until the game is over
		Map<String, Object> model = new HashMap<String, Object>();
		String id = UUID.randomUUID().toString();
		model.put("id", id);
		model.put("content", "Test");

		try {
			logToDb(id);
		} catch (Exception e) {
			System.err.println("Failed to save a log: " + e.getMessage());
		}
		return model;
	}

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private void logToDb(String id) {
		// TODO Log piece placement info and game status
		System.out.println("Sent an id: " + id);

		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		if (!stringRedisTemplate.hasKey(id)) {
			valueOperations.set(id, Long.toString(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(WebAGoGoApplication.class, args);
	}
}
