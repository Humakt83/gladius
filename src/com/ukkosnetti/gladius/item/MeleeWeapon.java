package com.ukkosnetti.gladius.item;

import java.io.Serializable;
import java.util.Random;

public class MeleeWeapon implements WeaponInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3599774284047376093L;
	private int damagemax;
    private int damagemin;
	private String weapontype; //0 spear, 1 sword, 2 axe, 3 hammer
	private Random r = new Random();
	private String name;
    private int price;
	public MeleeWeapon(int min, int max, String type, String n, int price){
		damagemin = min;
        damagemax = max;
		weapontype = type;
		name = n;
        this.price = price;
	}
	public String getWeaponType(){
		return weapontype;
	}
    public int getMinDam(){
        return damagemin;
    }
    public int getMaxDam(){
        return damagemax;
    }
	public int battleDamage(){
		return r.nextInt( damagemax - damagemin + 1 ) + damagemin;
	}
	public String getName(){
		return name;
	}
    public int getPrice(){
        return price;
    }
}
