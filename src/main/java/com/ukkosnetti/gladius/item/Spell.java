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
		return Arrays.asList(new Spell(2, 5, true, "Firebolt", 60, 5));
	}

	public static List<Spell> getHealingSpells() {
		return Arrays.asList(new Spell(1, 4, true, "Bandage", 50, 6));
	}
}