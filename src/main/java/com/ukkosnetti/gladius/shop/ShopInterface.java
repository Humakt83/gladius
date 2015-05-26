package com.ukkosnetti.gladius.shop;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.View;

public interface ShopInterface {
	boolean purchase(Team t, String which, Gladiator g, View v);
}
