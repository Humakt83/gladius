package com.ukkosnetti.gladius.gladiator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class GladiatorGeneratorTest {

	private GladiatorGenerator generator = GladiatorGenerator.getInstance();

	@Test
	public void generates5RandomGladiators() {
		assertEquals(5, generator.generateRandomGladiators(5).size());
	}

	@Test
	public void generates15RandomGladiators() {
		assertEquals(15, generator.generateRandomGladiators(15).size());
	}

	@Test
	public void generates150RandomGladiators() {
		assertEquals(150, generator.generateRandomGladiators(150).size());
	}

	@Test
	public void gladiatorsHaveNames() {
		for (Gladiator gl : generator.generateRandomGladiators(15)) {
			assertFalse(gl.getName().isEmpty());
		}
	}
}
