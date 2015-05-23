package com.ukkosnetti.gladius.item;

public interface WeaponInterface {

	String getWeaponType();

	int getMinDam();

	int getMaxDam();

	int battleDamage();

	String getName();

	int getPrice();
}
