package com.ukkosnetti.gladius.shop;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.View;
import com.ukkosnetti.gladius.item.Armor;
import com.ukkosnetti.gladius.item.MeleeWeapon;
import com.ukkosnetti.gladius.item.RangedWeapon;
import com.ukkosnetti.gladius.item.WeaponInterface;

/*
 * Class for Blacksmith. Contains weapons and armors used to place in ShopPanel.
 */
public class Blacksmith implements ShopInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6727096730008821602L;
	private List<RangedWeapon> rangedweapons; // Container for ranged weapons.
	private List<Armor> armors; // Container for armors.
	private List<MeleeWeapon> meleeweapons; // Container for melee weapons.

	public Blacksmith() {
		armors = Armor.getArmors();
		rangedweapons = RangedWeapon.getRangedWeapons();
		meleeweapons = MeleeWeapon.getMeleeWeapons();
	}

	public List<RangedWeapon> getRangeds() {
		return rangedweapons;
	}

	public List<MeleeWeapon> getMelees() {
		return meleeweapons;
	}

	public List<Armor> getArmors() {
		return armors;
	}

	/*
	 * Function for purchasing item. Checks vectors through iterator until valid
	 * item is found. If team has enough money, item is purchased. If gladiator
	 * had previous equipment in same slot, its price is halved and subtracted
	 * from new item's price.
	 */
	public boolean purchase(Team t, String which, Gladiator g, View v) {
		int squirrels = t.getSquirrels();
		Iterator<MeleeWeapon> it = meleeweapons.iterator();
		while (it.hasNext()) {
			WeaponInterface apu = it.next();
			if (apu.getName().equals(which)) {
				if (g.getRace().equals("Beholder") || g.getRace().equals("Beholder_Hero")) {
					v.addText("Beholders can't wear armor or use weapons.");
					return true;
				} else if (g.getMelee() != null) {
					int discount = (int) (g.getMelee().getPrice() / 2);
					if (squirrels >= apu.getPrice() - discount) {
						v.addText("Thy will get " + discount + " squirrels for thine old weapon.");
						t.setSquirrels((squirrels - (apu.getPrice() - discount)));
						g.setMelee(apu);
						v.addText("Thanks for purchasing o noble customer.");
						return true;
					}
				} else if (squirrels >= apu.getPrice()) {
					g.setMelee(apu);
					v.addText("Thanks for purchasing o noble customer.");
					t.setSquirrels((squirrels - apu.getPrice()));
					return true;
				}
			}
		}
		Iterator<RangedWeapon> it2 = rangedweapons.iterator();
		while (it2.hasNext()) {
			WeaponInterface apu = it2.next();
			if (apu.getName().equals(which)) {
				if (g.getRace().equals("Beholder") || g.getRace().equals("Beholder_Hero")) {
					v.addText("Beholders can't wear armor or use weapons.");
					return true;
				} else if (g.getRanged() != null) {
					int discount = (int) (g.getRanged().getPrice() / 2);
					if (squirrels >= apu.getPrice() - discount) {
						v.addText("Thy will get " + discount + " squirrels for thine old weapon.");
						g.setRanged(apu);
						v.addText("Thanks for purchasing o noble customer.");
						t.setSquirrels((squirrels - (apu.getPrice() - discount)));
						return true;
					}
				} else if (squirrels >= apu.getPrice()) {
					g.setRanged(apu);
					t.setSquirrels((squirrels - apu.getPrice()));
					v.addText("Thanks for purchasing o noble customer.");
					return true;
				}
			}
		}
		Iterator<Armor> it3 = armors.iterator();
		while (it3.hasNext()) {
			Armor apu = it3.next();
			if (apu.getName().equals(which)) {
				if (g.getRace().equals("Beholder") || g.getRace().equals("Beholder_Hero")) {
					v.addText("Beholders can't wear armor or use weapons.");
					return true;
				} else if (g.getArmor() != null) {
					int discount = (int) (g.getArmor().getPrice() / 2);
					if (squirrels >= apu.getPrice() - discount) {
						v.addText("Thy will get " + discount + " squirrels for thine old armor.");
						g.setArmor(apu);
						t.setSquirrels((squirrels - (apu.getPrice() - discount)));
						v.addText("Thanks for purchasing o noble customer.");
						return true;
					}
				} else if (squirrels >= apu.getPrice()) {
					g.setArmor(apu);
					t.setSquirrels((squirrels - apu.getPrice()));
					v.addText("Thanks for purchasing o noble customer.");
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Getter for returning weapon or armor from vector.
	 */
	public WeaponInterface getWeapon(String n) {
		Iterator<MeleeWeapon> it = meleeweapons.iterator();
		while (it.hasNext()) {
			WeaponInterface apu = it.next();
			if (apu.getName().equals(n))
				return apu;
		}
		Iterator<RangedWeapon> it2 = rangedweapons.iterator();
		while (it2.hasNext()) {
			WeaponInterface apu = it2.next();
			if (apu.getName().equals(n))
				return apu;
		}
		return null;
	}

	public Armor getArmor(String n) {
		Iterator<Armor> it = armors.iterator();
		while (it.hasNext()) {
			Armor apu = it.next();
			if (apu.getName().equals(n))
				return apu;
		}
		return null;
	}
}
