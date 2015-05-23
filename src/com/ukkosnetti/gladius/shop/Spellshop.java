package com.ukkosnetti.gladius.shop;

import java.io.Serializable;
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
	private List<Spell> damageSpells;
	private List<Spell> healingSpells;

	public Spellshop() {
		damageSpells = Spell.getDamageSpells();
		healingSpells = Spell.getHealingSpells();
	}

	public boolean purchase(Team t, String which, Gladiator g, View v) {
		int squirrels = t.getSquirrels();
		return purchaseSpell(t, which, g, v, squirrels, damageSpells) || purchaseSpell(t, which, g, v, squirrels, healingSpells);
	}

	private boolean purchaseSpell(Team t, String which, Gladiator g, View v, int squirrels, List<Spell> spells) {
		for (Spell spell : spells) {
			if (spell.getName().equals(which)) {
				if (squirrels >= spell.getPrice()) {
					if (g.getSpell1() == null)
						g.setSpell1(spell);
					else
						g.setSpell2(spell);
					t.setSquirrels((squirrels - spell.getPrice()));
					v.addText("Thanks for purchasing o noble customer.");
					return true;
				}
			}
		}
		return false;
	}

	public List<Spell> getDamageSpells() {
		return damageSpells;
	}

	public List<Spell> getHealingSpells() {
		return healingSpells;
	}
}
