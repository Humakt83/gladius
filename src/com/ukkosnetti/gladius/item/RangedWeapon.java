package com.ukkosnetti.gladius.item;

import java.io.Serializable;
import java.util.Random;

public class RangedWeapon implements WeaponInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7494794713855008746L;
	private int damagemax;
	private int damagemin;
	private String weapontype; // 0 bow, 1 crossbow
	private int price;
	private Random r = new Random();
	private String name;

	public RangedWeapon(int min, int max, String type, String n, int p) {
		damagemin = min;
		damagemax = max;
		weapontype = type;
		name = n;
		price = p;
	}

	public String getWeaponType() {
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
}
