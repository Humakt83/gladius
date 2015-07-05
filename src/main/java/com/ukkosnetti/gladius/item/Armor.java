package com.ukkosnetti.gladius.item;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/*
 * Class for Armor object.
 */
public class Armor implements Serializable, Purchasable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5628068665398244341L;
	private int armor; // Value which is reduced from inflicted melee or ranged
						// damage in combat.
	private int price; // Price of armor in squirrels.
	private String name; // Name of armor.

	public Armor(int a, String n, int price) {
		armor = a;
		name = n;
		this.price = price;
	}

	public int getArmor() {
		return armor;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public static List<Armor> getArmors() {
		return Arrays.asList(new Armor(1, "Rags", 25), new Armor(2, "Leather", 80), new Armor(3, "Studded Leather", 170), new Armor(4, "Chainmail", 270), new Armor(5, "Splintmail", 485), new Armor(6,
				"Plate", 850), new Armor(8, "Full plate", 1500), new Armor(10, "Titan's Cuirass", 3000));

	}
}
