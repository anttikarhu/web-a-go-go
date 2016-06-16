package com.anttikarhu.webagogo.rules;

import org.springframework.stereotype.Component;

/**
 * Defines a small board for testing.
 * 
 * @author Antti Karhu
 * 
 */
@Component
public class TestRules extends DefaultRules {
	@Override
	public int getBoardSize() {
		return 5;
	}

	@Override
	protected String createId() {
		return "01234567-89ab-cdef-0123456789abcdef0";
	}

	@Override
	protected long getTimestamp() {
		return 123L;
	}
}
