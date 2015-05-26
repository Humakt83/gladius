package com.ukkosnetti.gladius.item;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MeleeWeapon implements WeaponInterface<MeleeWeaponType>, Serializable {

	private static final long serialVersionUID = -3599774284047376093L;

	private int damagemax;
	private int damagemin;
	private MeleeWeaponType weapontype;
	private Random r = new Random();
	private String name;
	private int price;

	public MeleeWeapon(int min, int max, MeleeWeaponType type, String n, int price) {
		damagemin = min;
		damagemax = max;
		weapontype = type;
		name = n;
		this.price = price;
	}

	public MeleeWeaponType getWeaponType() {
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

	public static List<MeleeWeapon> getMeleeWeapons() {
		return Arrays.asList(new MeleeWeapon(1, 6, MeleeWeaponType.SPEAR, "Pike", 25), new MeleeWeapon(3, 4, MeleeWeaponType.SWORD, "Shortsword", 45), new MeleeWeapon(2, 5, MeleeWeaponType.AXE,
				"Handaxe", 50), new MeleeWeapon(3, 7, MeleeWeaponType.HAMMER, "Club", 75));
	}
}
