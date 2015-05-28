package com.ukkosnetti.gladius.gladiator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GladiatorGenerator {

	public static List<Gladiator> generateRandomGladiators(int amount) {
		Random random = new Random(System.currentTimeMillis());
		List<Gladiator> generatedGladiators = new ArrayList<>();
		for (int i = amount; i > 0; i--) {
			GladiatorType[] types = GladiatorType.values();
			GladiatorType type = types[random.nextInt(types.length)];
			generatedGladiators.add(GladiatorFactory.generateGladiator(type));
		}
		return generatedGladiators;
	}

}
