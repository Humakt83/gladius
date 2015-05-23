package com.ukkosnetti.gladius.shop;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import com.ukkosnetti.gladius.DAO;
import com.ukkosnetti.gladius.Game;
import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.View;

public class Tavern implements ShopInterface, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6151312881627631404L;
	private Vector<Gladiator> gladiators;

	public Tavern(DAO dao, Game g) {
		gladiators = dao.searchGladiators("Tavern", g);
	}

	public boolean purchase(Team t, String which, Gladiator g, View v) {
		int squirrels = t.getSquirrels();
		Iterator<Gladiator> it = gladiators.iterator();
		boolean success = false;
		while (it.hasNext()) {
			Gladiator apu = it.next();
			if (apu.getName().equals(which)) {
				if (squirrels >= apu.getUpkeep() * 10) {
					t.setSquirrels((squirrels - (apu.getUpkeep() * 10)));
					success = true;
				}
			}
		}
		return success;
	}

	public void takeGladiators(String n) {
		Gladiator gl = null;
		Iterator<Gladiator> it = gladiators.iterator();
		while (it.hasNext()) {
			Gladiator apu = it.next();
			if (apu.getName().equals(n))
				gl = apu;
		}
		if (gl != null)
			gladiators.remove(gl);
	}

	public Gladiator getGladiator(String n) {
		Gladiator g = null;
		Iterator<Gladiator> it = gladiators.iterator();
		while (it.hasNext()) {
			Gladiator apu = it.next();
			if (apu.getName().equals(n))
				g = apu;
		}
		return g;
	}

	public Vector<Gladiator> getGladiators() {
		return gladiators;
	}
}
