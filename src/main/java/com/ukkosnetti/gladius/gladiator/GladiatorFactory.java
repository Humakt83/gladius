package com.ukkosnetti.gladius.gladiator;

import java.util.Random;

public class GladiatorFactory {

	public static Gladiator generateGladiator(GladiatorType type, String name) {
		GladiatorBuilder builder = new GladiatorBuilder(type);
		switch (type) {
		case BEHOLDER:
			builder.setAttack(randomNumberBetween(34, 55)).setDefense(randomNumberBetween(30, 40)).setEvasion(randomNumberBetween(5, 20)).setMaxHealth(randomNumberBetween(30, 40))
					.setMaxMana(randomNumberBetween(25, 40)).setResistance(randomNumberBetween(25, 40)).setNaturalArmor(randomNumberBetween(2, 6)).setUpkeep(randomNumberBetween(15, 35));
			break;
		case CYCLOPS:
			builder.setAttack(randomNumberBetween(20, 35)).setDefense(randomNumberBetween(15, 30)).setEvasion(randomNumberBetween(5, 20)).setMaxHealth(randomNumberBetween(25, 35))
					.setMaxMana(randomNumberBetween(1, 10)).setResistance(randomNumberBetween(1, 10)).setNaturalArmor(randomNumberBetween(0, 3))
					.setAxeSkill(randomNumberBetween(10, 30), randomNumberBetween(5, 20)).setHammerSkill(randomNumberBetween(15, 35), randomNumberBetween(5, 30))
					.setUpkeep(randomNumberBetween(15, 35));
			break;
		case GOBLIN_WIZARD:
			builder.setAttack(randomNumberBetween(25, 50)).setDefense(randomNumberBetween(30, 60)).setEvasion(randomNumberBetween(10, 30)).setMaxHealth(randomNumberBetween(10, 20))
					.setMaxMana(randomNumberBetween(30, 50)).setResistance(randomNumberBetween(30, 50)).setAxeSkill(randomNumberBetween(5, 25), randomNumberBetween(5, 25))
					.setHammerSkill(randomNumberBetween(5, 25), randomNumberBetween(5, 25)).setSpearSkill(randomNumberBetween(5, 25), randomNumberBetween(5, 25))
					.setSwordSkill(randomNumberBetween(5, 25), randomNumberBetween(5, 35)).setBowSkill(randomNumberBetween(5, 25)).setCrossBowSkill(randomNumberBetween(5, 25))
					.setUpkeep(randomNumberBetween(7, 17));
			break;
		case HOBGOBLIN:
			builder.setAttack(randomNumberBetween(25, 50)).setDefense(randomNumberBetween(20, 40)).setEvasion(randomNumberBetween(10, 25)).setMaxHealth(randomNumberBetween(15, 25))
					.setMaxMana(randomNumberBetween(10, 20)).setResistance(randomNumberBetween(5, 25)).setAxeSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30))
					.setHammerSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30)).setSpearSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30))
					.setSwordSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30)).setBowSkill(randomNumberBetween(15, 35)).setCrossBowSkill(randomNumberBetween(15, 35))
					.setUpkeep(randomNumberBetween(5, 15));
			break;
		case HOBOTTI:
			builder.setAttack(randomNumberBetween(30, 60)).setDefense(randomNumberBetween(35, 70)).setEvasion(randomNumberBetween(20, 45)).setMaxHealth(randomNumberBetween(8, 19))
					.setMaxMana(randomNumberBetween(15, 30)).setResistance(randomNumberBetween(25, 60)).setAxeSkill(randomNumberBetween(15, 45), randomNumberBetween(15, 45))
					.setHammerSkill(randomNumberBetween(15, 45), randomNumberBetween(15, 45)).setSpearSkill(randomNumberBetween(15, 45), randomNumberBetween(15, 45))
					.setSwordSkill(randomNumberBetween(25, 65), randomNumberBetween(30, 70)).setBowSkill(randomNumberBetween(35, 65)).setCrossBowSkill(randomNumberBetween(35, 65))
					.setUpkeep(randomNumberBetween(7, 16));
			break;
		case KAARNAPEIKKO:
			builder.setAttack(randomNumberBetween(20, 35)).setDefense(randomNumberBetween(15, 30)).setEvasion(randomNumberBetween(5, 20)).setMaxHealth(randomNumberBetween(30, 40))
					.setMaxMana(randomNumberBetween(1, 10)).setResistance(randomNumberBetween(1, 10)).setNaturalArmor(randomNumberBetween(1, 5))
					.setAxeSkill(randomNumberBetween(15, 35), randomNumberBetween(15, 35)).setHammerSkill(randomNumberBetween(15, 35), randomNumberBetween(15, 35))
					.setUpkeep(randomNumberBetween(10, 25));
			break;
		case LONGSTRIDER:
			builder.setAttack(randomNumberBetween(20, 45)).setDefense(randomNumberBetween(15, 50)).setEvasion(randomNumberBetween(25, 60)).setMaxHealth(randomNumberBetween(15, 25))
					.setMaxMana(randomNumberBetween(10, 25)).setResistance(randomNumberBetween(10, 30)).setSwordSkill(randomNumberBetween(25, 50), randomNumberBetween(25, 50))
					.setSpearSkill(randomNumberBetween(30, 65), randomNumberBetween(30, 65)).setBowSkill(randomNumberBetween(25, 55)).setCrossBowSkill(randomNumberBetween(25, 55))
					.setUpkeep(randomNumberBetween(7, 17));
			break;
		case MEDUSA:
			builder.setAttack(randomNumberBetween(25, 50)).setDefense(randomNumberBetween(25, 50)).setEvasion(randomNumberBetween(10, 25)).setMaxHealth(randomNumberBetween(15, 25))
					.setMaxMana(randomNumberBetween(30, 45)).setResistance(randomNumberBetween(25, 50)).setAxeSkill(randomNumberBetween(15, 45), randomNumberBetween(15, 45))
					.setHammerSkill(randomNumberBetween(15, 45), randomNumberBetween(15, 45)).setSpearSkill(randomNumberBetween(15, 45), randomNumberBetween(15, 45))
					.setSwordSkill(randomNumberBetween(15, 45), randomNumberBetween(15, 45)).setBowSkill(randomNumberBetween(25, 55)).setCrossBowSkill(randomNumberBetween(25, 55))
					.setUpkeep(randomNumberBetween(7, 17));
			break;
		case MINOTAUR:
			builder.setAttack(randomNumberBetween(25, 50)).setDefense(randomNumberBetween(5, 30)).setEvasion(randomNumberBetween(5, 20)).setMaxHealth(randomNumberBetween(22, 30))
					.setMaxMana(randomNumberBetween(5, 15)).setResistance(randomNumberBetween(15, 35)).setAxeSkill(randomNumberBetween(15, 65), randomNumberBetween(10, 50))
					.setHammerSkill(randomNumberBetween(15, 50), randomNumberBetween(10, 35)).setSpearSkill(randomNumberBetween(5, 25), randomNumberBetween(5, 25))
					.setSwordSkill(randomNumberBetween(5, 35), randomNumberBetween(5, 35)).setBowSkill(randomNumberBetween(5, 15)).setCrossBowSkill(randomNumberBetween(5, 15))
					.setUpkeep(randomNumberBetween(7, 17));
			break;
		case ORC:
			builder.setAttack(randomNumberBetween(25, 50)).setDefense(randomNumberBetween(20, 40)).setEvasion(randomNumberBetween(10, 25)).setMaxHealth(randomNumberBetween(18, 29))
					.setMaxMana(randomNumberBetween(10, 20)).setResistance(randomNumberBetween(5, 25)).setAxeSkill(randomNumberBetween(15, 55), randomNumberBetween(10, 30))
					.setHammerSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30)).setSpearSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30))
					.setSwordSkill(randomNumberBetween(15, 55), randomNumberBetween(10, 30)).setBowSkill(randomNumberBetween(15, 35)).setCrossBowSkill(randomNumberBetween(15, 35))
					.setUpkeep(randomNumberBetween(5, 15));
			break;
		case SHADOW:
			builder.setAttack(randomNumberBetween(25, 55)).setDefense(randomNumberBetween(25, 50)).setEvasion(randomNumberBetween(45, 75)).setMaxHealth(randomNumberBetween(10, 25))
					.setMaxMana(randomNumberBetween(20, 35)).setResistance(randomNumberBetween(10, 30)).setSwordSkill(randomNumberBetween(25, 50), randomNumberBetween(35, 60))
					.setSpearSkill(randomNumberBetween(30, 65), randomNumberBetween(35, 75)).setBowSkill(randomNumberBetween(25, 65)).setCrossBowSkill(randomNumberBetween(15, 35))
					.setUpkeep(randomNumberBetween(10, 20));
			break;
		case VAMPIRE:
			builder.setAttack(randomNumberBetween(25, 50)).setDefense(randomNumberBetween(20, 40)).setEvasion(randomNumberBetween(20, 35)).setMaxHealth(randomNumberBetween(17, 27))
					.setMaxMana(randomNumberBetween(20, 30)).setResistance(randomNumberBetween(15, 35)).setAxeSkill(randomNumberBetween(15, 35), randomNumberBetween(10, 30))
					.setHammerSkill(randomNumberBetween(15, 25), randomNumberBetween(10, 20)).setSpearSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30))
					.setSwordSkill(randomNumberBetween(25, 65), randomNumberBetween(20, 60)).setBowSkill(randomNumberBetween(15, 35)).setCrossBowSkill(randomNumberBetween(10, 25))
					.setUpkeep(randomNumberBetween(10, 20));
			break;
		case ZOMBIE:
			builder.setAttack(randomNumberBetween(25, 50)).setDefense(randomNumberBetween(5, 15)).setEvasion(randomNumberBetween(1, 10)).setMaxHealth(randomNumberBetween(25, 30))
					.setMaxMana(randomNumberBetween(1, 10)).setResistance(randomNumberBetween(1, 15)).setAxeSkill(randomNumberBetween(15, 55), randomNumberBetween(5, 10))
					.setHammerSkill(randomNumberBetween(15, 45), randomNumberBetween(5, 10)).setSpearSkill(randomNumberBetween(5, 35), randomNumberBetween(5, 10))
					.setSwordSkill(randomNumberBetween(15, 50), randomNumberBetween(5, 10)).setBowSkill(randomNumberBetween(5, 15)).setCrossBowSkill(randomNumberBetween(15, 35))
					.setUpkeep(randomNumberBetween(2, 10));
			break;
		case OTO:
			builder.setAttack(randomNumberBetween(20, 35)).setDefense(randomNumberBetween(15, 30)).setEvasion(randomNumberBetween(1, 10)).setMaxHealth(randomNumberBetween(35, 45))
					.setMaxMana(randomNumberBetween(5, 15)).setResistance(randomNumberBetween(10, 20)).setNaturalArmor(randomNumberBetween(1, 5))
					.setAxeSkill(randomNumberBetween(15, 35), randomNumberBetween(15, 35)).setHammerSkill(randomNumberBetween(15, 35), randomNumberBetween(15, 35))
					.setSpearSkill(randomNumberBetween(15, 45), randomNumberBetween(10, 30)).setSwordSkill(randomNumberBetween(25, 65), randomNumberBetween(20, 60))
					.setUpkeep(randomNumberBetween(10, 25));
			break;
		default:
			break;
		}
		return builder.build(name);
	}

	private static int randomNumberBetween(int min, int max) {
		Random random = new Random(System.currentTimeMillis());
		return Math.max(min, random.nextInt(max));
	}
}
