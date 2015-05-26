package com.ukkosnetti.gladius.item;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RangedWeapon implements WeaponInterface<RangedWeaponType>, Serializable {

	private static final long serialVersionUID = -7494794713855008746L;
	private int damagemax;
	private int damagemin;
	private RangedWeaponType weapontype;
	private int price;
	private Random r = new Random();
	private String name;

	public RangedWeapon(int min, int max, RangedWeaponType type, String n, int p) {
		damagemin = min;
		damagemax = max;
		weapontype = type;
		name = n;
		price = p;
	}

	public RangedWeaponType getWeaponType() {
		return weapontype;
	}

	public int getMinDam() {
		return damagemin;
	}

	public int getMaxDam() {
		return damagemax;
	}

	public int battleDamage() {
		return r.nextInt(damagemax - damagemin + 1) + damagemin;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public static List<RangedWeapon> getRangedWeapons() {
		return Arrays.asList(new RangedWeapon(1, 3, RangedWeaponType.BOW, "Sling", 25), new RangedWeapon(2, 5, RangedWeaponType.BOW, "Bow", 50), new RangedWeapon(4, 7, RangedWeaponType.CROSSBOW,
				"Crossbow", 75));
	}
}
