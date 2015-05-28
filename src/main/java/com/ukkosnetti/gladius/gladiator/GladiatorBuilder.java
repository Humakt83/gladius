package com.ukkosnetti.gladius.gladiator;

import javax.swing.ImageIcon;

public class GladiatorBuilder {

	private final Gladiator gladiator;

	public GladiatorBuilder(GladiatorType type) {
		gladiator = new Gladiator("", 0, 10, 15, 15, 15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, "", 10, new ImageIcon(type.imgName), 10, 10);
	}

	public GladiatorBuilder setNaturalArmor(int armor) {
		gladiator.setNaturalarmor(armor);
		return this;
	}

	public GladiatorBuilder setMaxHealth(int maxHealth) {
		gladiator.setMaxhealth(maxHealth);
		return this;
	}

	public GladiatorBuilder setAttack(int attack) {
		gladiator.setAttack(attack);
		return this;
	}
	
	public GladiatorBuilder setDefense(int defense) {
		gladiator.setDefense(defense);
		return this;
	}
	
	public GladiatorBuilder setResistance(int resistance) {
		gladiator.setResistance(resistance);
		return this;
	}

	public GladiatorBuilder setSwordSkill(int attack, int defense) {
		gladiator.setSwordskillAtt(attack);
		gladiator.setSwordskillDef(defense);
		return this;
	}

	public GladiatorBuilder setSpearSkill(int attack, int defense) {
		gladiator.setSpearskillAtt(attack);
		gladiator.setSpearskillDef(defense);
		return this;
	}

	public GladiatorBuilder setAxeSkill(int attack, int defense) {
		gladiator.setAxeskillAtt(attack);
		gladiator.setAxeskillDef(defense);
		return this;
	}

	public GladiatorBuilder setHammerSkill(int attack, int defense) {
		gladiator.setHammerskillAtt(attack);
		gladiator.setHammerskillDef(defense);
		return this;
	}

	public GladiatorBuilder setBowSkill(int skill) {
		gladiator.setBowskill(skill);
		return this;
	}

	public GladiatorBuilder setCrossBowSkill(int skill) {
		gladiator.setCrossbowskill(skill);
		return this;
	}

	public GladiatorBuilder setMaxMana(int mana) {
		gladiator.setMaxmana(mana);
		return this;
	}

	public GladiatorBuilder setUpkeep(int upkeep) {
		gladiator.setUpkeep(upkeep);
		return this;
	}

	public GladiatorBuilder setStrength(int strength) {
		gladiator.setStrength(strength);
		return this;
	}

	public GladiatorBuilder setEvasion(int evasion) {
		gladiator.setEvasion(evasion);
		return this;
	}


	public Gladiator build(String name) {
		gladiator.setName(name);
		return gladiator;
	}
}
