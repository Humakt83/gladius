package com.ukkosnetti.gladius.gladiator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GladiatorGenerator {

	private static GladiatorGenerator generator = null;

	private final List<String> names;

	private GladiatorGenerator(List<String> names) {
		this.names = names;
	}

	public List<Gladiator> generateRandomGladiators(int amount) {
		Random random = new Random(System.currentTimeMillis());
		return generateRandomGladiators(amount, random);
	}

	public List<Gladiator> generateRandomGladiators(int amount, Random random) {
		List<Gladiator> generatedGladiators = new ArrayList<>();
		for (int i = amount; i > 0; i--) {
			GladiatorType[] types = GladiatorType.values();
			GladiatorType type = types[random.nextInt(types.length)];
			generatedGladiators.add(GladiatorFactory.generateGladiator(type, randomName(random, generatedGladiators)));
		}
		return generatedGladiators;
	}

	private String randomName(Random random, List<Gladiator> gladiators) {
		String name = names.get(random.nextInt(names.size()));
		return nameAlreadyUsed(name, gladiators) ? randomName(random, gladiators) : name;
	}

	private boolean nameAlreadyUsed(String name, List<Gladiator> gladiators) {
		for (Gladiator gl : gladiators) {
			if (gl.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public static GladiatorGenerator getInstance() {
		if (generator == null) {
			generator = new GladiatorGenerator(loadNames());
		}
		return generator;
	}

	private static List<String> loadNames() {
		List<String> names = new ArrayList<String>();
		try {
			List<String> lines = Files.readAllLines(Paths.get("res/names.txt"));
			for (String line : lines) {
				names.addAll(Arrays.asList(line.split(",")));
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not load the names", e);
		}
		return names;
	}

}
