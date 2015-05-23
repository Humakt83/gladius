package game;

import java.io.Serializable;
/*
 * Class for Armor object.
 */
public class Armor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5628068665398244341L;
	private int armor; //Value which is reduced from inflicted melee or ranged damage in combat.
    private int price; //Price of armor in squirrels.
	private String name; //Name of armor.
	public Armor(int a, String n, int price){
		armor = a;
		name = n;
        this.price = price;
	}
	public int getArmor(){
		return armor;
	}
	public String getName(){
		return name;
	}
    public int getPrice(){
        return price;
    }
}
