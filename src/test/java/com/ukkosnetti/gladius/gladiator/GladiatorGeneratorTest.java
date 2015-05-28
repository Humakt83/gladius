package com.ukkosnetti.gladius.gladiator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GladiatorGeneratorTest {

	@Test
	public void generates5RandomGladiators() {
		assertEquals(5, GladiatorGenerator.generateRandomGladiators(5).size());
	}

	@Test
	public void generates15RandomGladiators() {
		assertEquals(15, GladiatorGenerator.generateRandomGladiators(15).size());
	}
}
