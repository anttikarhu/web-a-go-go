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
				.andExpect(jsonPath("$.board", hasSize(5)));
	}

	// TODO Tests for makeMove
}
