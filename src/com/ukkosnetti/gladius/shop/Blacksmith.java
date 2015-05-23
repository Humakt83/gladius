package com.ukkosnetti.gladius.shop;

import java.io.Serializable;
import java.util.List;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.View;
import com.ukkosnetti.gladius.item.Armor;
import com.ukkosnetti.gladius.item.MeleeWeapon;
import com.ukkosnetti.gladius.item.RangedWeapon;

/*
 * Class for Blacksmith. Contains weapons and armors used to place in ShopPanel.
 */
public class Blacksmith implements ShopInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6727096730008821602L;

	private List<RangedWeapon> rangedWeapons; // Container for ranged weapons.
	private List<Armor> armors; // Container for armors.
	private List<MeleeWeapon> meleeWeapons; // Container for melee weapons.

	public Blacksmith() {
		armors = Armor.getArmors();
		rangedWeapons = RangedWeapon.getRangedWeapons();
		meleeWeapons = MeleeWeapon.getMeleeWeapons();
	}

	public List<RangedWeapon> getRangeds() {
		return rangedWeapons;
	}

	public List<MeleeWeapon> getMelees() {
		return meleeWeapons;
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
		if (g.getRace().equals("Beholder") || g.getRace().equals("Beholder_Hero")) {
			v.addText("Beholders can't wear armor or use weapons.");
			return true;
		}
		int squirrels = t.getSquirrels();
		for (MeleeWeapon meleeWeapon : meleeWeapons) {
			if (meleeWeapon.getName().equals(which)) {
				if (g.getMelee() != null) {
					int discount = (int) (g.getMelee().getPrice() / 2);
					if (squirrels >= meleeWeapon.getPrice() - discount) {
						v.addText("Thy will get " + discount + " squirrels for thine old weapon.");
						t.setSquirrels((squirrels - (meleeWeapon.getPrice() - discount)));
						g.setMelee(meleeWeapon);
						v.addText("Thanks for purchasing o noble customer.");
						return true;
					}
				} else if (squirrels >= meleeWeapon.getPrice()) {
					g.setMelee(meleeWeapon);
					v.addText("Thanks for purchasing o noble customer.");
					t.setSquirrels((squirrels - meleeWeapon.getPrice()));
					return true;
				}
			}
		}

		for (RangedWeapon rangedWeapon : rangedWeapons) {
			if (rangedWeapon.getName().equals(which)) {
				if (g.getRanged() != null) {
					int discount = (int) (g.getRanged().getPrice() / 2);
					if (squirrels >= rangedWeapon.getPrice() - discount) {
						v.addText("Thy will get " + discount + " squirrels for thine old weapon.");
						g.setRanged(rangedWeapon);
						v.addText("Thanks for purchasing o noble customer.");
						t.setSquirrels((squirrels - (rangedWeapon.getPrice() - discount)));
						return true;
					}
				} else if (squirrels >= rangedWeapon.getPrice()) {
					g.setRanged(rangedWeapon);
					t.setSquirrels((squirrels - rangedWeapon.getPrice()));
					v.addText("Thanks for purchasing o noble customer.");
					return true;
				}
			}
		}
		for (Armor armor : armors) {
			if (armor.getName().equals(which)) {
				if (g.getArmor() != null) {
					int discount = (int) (g.getArmor().getPrice() / 2);
					if (squirrels >= armor.getPrice() - discount) {
						v.addText("Thy will get " + discount + " squirrels for thine old armor.");
						g.setArmor(armor);
						t.setSquirrels((squirrels - (armor.getPrice() - discount)));
						v.addText("Thanks for purchasing o noble customer.");
						return true;
					}
				} else if (squirrels >= armor.getPrice()) {
					g.setArmor(armor);
					t.setSquirrels((squirrels - armor.getPrice()));
					v.addText("Thanks for purchasing o noble customer.");
					return true;
				}
			}
		}
		return false;
	}

}
