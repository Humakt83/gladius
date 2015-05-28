package com.ukkosnetti.gladius.gladiator;

public enum GladiatorType {
	
	BEHOLDER("Beholder", null), CYCLOPS("Cyclops", null), GOBLIN_WIZARD("GoblinWizard", "goblin"), HOBGOBLIN("Hobgoblin", null), HOBOTTI("Höbötti", null), KAARNAPEIKKO("Kaarnapeikko", null), LONGSTRIDER(
			"LongStrider", null), MEDUSA("Medusa", null), MINOTAUR("Minotaur", null), ORC("Orc", null), SHADOW("Shadow", null), VAMPIRE("Vampire", null), ZOMBIE("Zombie", null), OTO("Ötö", null);
	
	private final static String IMG_PREFIX = "res/", IMG_POSTFIX = ".gif";
	
	public final String imgName, race;
	
	private GladiatorType(String name, String race) {
		imgName = IMG_PREFIX + name + IMG_POSTFIX;
		this.race = race != null ? race : name.toLowerCase();
	}
}
