package com.anttikarhu.webagogo;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * WebAGoGo tests.
 * 
 * @author Antti Karhu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { WebAGoGoApplication.class })
@WebAppConfiguration
public class WebAGoGoApplicationTests {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void newGame() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/newGame")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.gameId", is("01234567-89ab-cdef-0123456789abcdef0")))
				.andExpect(jsonPath("$.timestamp", is(123)))
				.andExpect(jsonPath("$.previousMove").doesNotExist())
				.andExpect(jsonPath("$.playersTurn", is("BLACK")))
				.andExpect(jsonPath("$.board", hasSize(5)))
				.andExpect(jsonPath("$.board[0][0]", is("FREE")))
				.andExpect(jsonPath("$.board[3][2]", is("FREE")))
				.andExpect(jsonPath("$.board[4][4]", is("FREE")));
	}

	@Test
	public void makeMove() throws Exception {
		// Start a new game so the board is clear
		mvc.perform(MockMvcRequestBuilders.get("/newGame"));

		// Mock a short game
		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "PLAY")
				.param("x", "2")
				.param("y", "2")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.gameId", is("01234567-89ab-cdef-0123456789abcdef0")))
				.andExpect(jsonPath("$.timestamp", is(123)))
				.andExpect(jsonPath("$.previousMove").exists())
				.andExpect(jsonPath("$.playersTurn", is("WHITE")))
				.andExpect(jsonPath("$.board", hasSize(5)))
				.andExpect(jsonPath("$.board[2][2]", is("BLACK")));

		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "PLAY")
				.param("x", "3")
				.param("y", "2")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.playersTurn", is("BLACK")))
				.andExpect(jsonPath("$.board[2][3]", is("WHITE")));

		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "PLAY")
				.param("x", "4")
				.param("y", "2")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.board[2][4]", is("BLACK")));

		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "PASS")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.board[2][2]", is("BLACK")))
				.andExpect(jsonPath("$.board[2][3]", is("WHITE")))
				.andExpect(jsonPath("$.board[2][4]", is("BLACK")));

		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "PLAY")
				.param("x", "3")
				.param("y", "1")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.board[1][3]", is("BLACK")));

		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "PASS")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "PLAY")
				.param("x", "3")
				.param("y", "3")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.board[2][2]", is("BLACK")))
				.andExpect(jsonPath("$.board[2][4]", is("BLACK")))
				.andExpect(jsonPath("$.board[1][3]", is("BLACK")))
				.andExpect(jsonPath("$.board[3][3]", is("BLACK")))
				.andExpect(jsonPath("$.board[2][3]", is("FREE")));

		mvc.perform(MockMvcRequestBuilders.get("/makeMove")
				.param("moveType", "REMOVE_STONE")
				.param("x", "3")
				.param("y", "3")
				.param("gameId", "01234567-89ab-cdef-0123456789abcdef0")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.board[2][2]", is("BLACK")))
				.andExpect(jsonPath("$.board[2][4]", is("BLACK")))
				.andExpect(jsonPath("$.board[1][3]", is("BLACK")))
				.andExpect(jsonPath("$.board[3][3]", is("FREE")))
				.andExpect(jsonPath("$.board[2][3]", is("FREE")));
	}
}
