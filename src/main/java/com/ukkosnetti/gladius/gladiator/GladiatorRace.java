package com.ukkosnetti.gladius.gladiator;

public enum GladiatorRace {
	
	BEHOLDER("Beholder"), CYCLOPS("Cyclops"), GREMLIN("Gremlin"), IMP("Imp"), HOBOTTI("Höbötti"), LIERO("LongStrider"), MEDUSA("Medusa"), MINOTAUR("Minotaur"), ORC("Orc"), SHADOW("Shadow"), VAMPIRE(
			"Vampire"), ZOMBIE("Zombie");
	
	private final static String IMG_PREFIX = "res/", IMG_POSTFIX = ".gif";
	
	public final String imgName;
	
	private GladiatorRace(String name) {
		imgName = IMG_PREFIX + name + IMG_POSTFIX;
	}

	@Override
	public String toString() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}

}
