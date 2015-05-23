package com.ukkosnetti.gladius.item;

public interface WeaponInterface<T> {

	T getWeaponType();

	int getMinDam();

	int getMaxDam();

	int battleDamage();

	String getName();

	int getPrice();
}
