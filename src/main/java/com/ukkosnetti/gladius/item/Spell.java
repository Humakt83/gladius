package com.ukkosnetti.gladius.item;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Spell implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3869845681668646676L;
	private int mindamage;
	private int maxdamage;
	private String name;
	private boolean damagespell;
	private int price;
	private int mana;
	private Random r = new Random();

	public Spell(int mindamage, int maxdamage, boolean d, String n, int price, int mana) {
		this.mindamage = mindamage;
		this.maxdamage = maxdamage;
		damagespell = d;
		name = n;
		this.price = price;
		this.mana = mana;
	}

	public boolean getDamageSpell() {
		return damagespell;
	}

	public int getMinDamage() {
		return mindamage;
	}

	public int getMaxDamage() {
		return maxdamage;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int battleDamage() {
		return r.nextInt(maxdamage - mindamage + 1) + mindamage;
	}

	public int getManaCost() {
		return mana;
	}

	public static List<Spell> getDamageSpells() {
		return Arrays.asList(new Spell(1, 4, true, "Flare", 60, 5), new Spell(2, 5, true, "Firebolt", 120, 5), new Spell(3, 10, true, "Lightning Bolt", 450, 7), new Spell(5, 12, true, "Fireball",
				750, 10), new Spell(15, 25, true, "Meteor Strike", 1500, 20), new Spell(20, 25, true, "Divine Strike", 3000, 10));
	}

	public static List<Spell> getHealingSpells() {
		return Arrays.asList(new Spell(1, 3, false, "Bandage", 50, 4), new Spell(2, 4, false, "First Aid", 100, 5), new Spell(2, 9, false, "Heal", 500, 6), new Spell(4, 11, false, "Divine Touch",
				1500, 8));
	}
}
