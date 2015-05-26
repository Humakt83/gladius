package com.ukkosnetti.gladius.item;

public interface WeaponInterface<T> extends Purchasable {

	T getWeaponType();

	int getMinDam();

	int getMaxDam();

	int battleDamage();

}
