package com.ukkosnetti.gladius.shop;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.View;
import com.ukkosnetti.gladius.item.Spell;

public class Spellshop implements ShopInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4680531697500241304L;
	private List<Spell> damagespells;
	private List<Spell> healingspells;

	public Spellshop() {
		damagespells = Spell.getDamageSpells();
		healingspells = Spell.getHealingSpells();
	}

	public boolean purchase(Team t, String which, Gladiator g, View v) {
		int squirrels = t.getSquirrels();
		Iterator<Spell> it = damagespells.iterator();
		while (it.hasNext()) {
			Spell apu = it.next();
			if (apu.getName().equals(which)) {
				if (squirrels >= apu.getPrice()) {
					if (g.getSpell1() == null)
						g.setSpell1(apu);
					else
						g.setSpell2(apu);
					t.setSquirrels((squirrels - apu.getPrice()));
					v.addText("Thanks for purchasing o noble customer.");
					return true;
				}
			}
		}
		Iterator<Spell> it2 = healingspells.iterator();
		while (it2.hasNext()) {
			Spell apu = it2.next();
			if (apu.getName().equals(which)) {
				if (squirrels >= apu.getPrice()) {
					if (g.getSpell1() == null)
						g.setSpell1(apu);
					else
						g.setSpell2(apu);
					t.setSquirrels((squirrels - apu.getPrice()));
					v.addText("Thanks for purchasing o noble customer.");
					return true;
				}
			}
		}
		return false;
	}

	public Spell getSpell(String n) {
		Iterator<Spell> it = damagespells.iterator();
		while (it.hasNext()) {
			Spell apu = it.next();
			if (apu.getName().equals(n))
				return apu;
		}
		Iterator<Spell> it2 = healingspells.iterator();
		while (it2.hasNext()) {
			Spell apu = it2.next();
			if (apu.getName().equals(n))
				return apu;
		}
		System.out.println("Why here");
		return null;
	}

	public List<Spell> getDamageSpells() {
		return damagespells;
	}

	public List<Spell> getHealingSpells() {
		return healingspells;
	}
}
