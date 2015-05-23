package com.ukkosnetti.gladius.gladiator;

import java.awt.Point;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.ukkosnetti.gladius.item.Armor;
import com.ukkosnetti.gladius.item.Spell;
import com.ukkosnetti.gladius.item.WeaponInterface;

public class Gladiator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8109938923144968581L;
	private WeaponInterface melee;
	private WeaponInterface ranged;
	private Spell spell1;
	private Spell spell2;
	private Armor armor;
	private int damagemin;
	private int damagemax;
	private String race;
	private int naturalarmor;
	private int health;
	private int maxhealth;
	private int strength;
	private int attack;
	private int defense;
	private int agility;
	private int evasion;
	private int resistance;
	private int swordatt;
	private int sworddef;
	private int spearatt;
	private int speardef;
	private int axeatt;
	private int axedef;
	private int hammeratt;
	private int hammerdef;
	private int bowskill;
	private int crossbowskill;
	private int mana;
	private int maxmana;
	private String name;
	private int upkeep;
	private int knockdowns = 0;
	private Point location = new Point(0, 0);

	private ImageIcon picture;

	public Gladiator(int damagemin, int damagemax, String race, int naturalarmor, int maxhealth, int attack, int defense, int agility, int resistance, int swordatt, int sworddef, int spearatt,
			int speardef, int axeatt, int axedef, int hammeratt, int hammerdef, int bowskill, int crossbowskill, int maxmana, String name, int upkeep, ImageIcon p, int strength, int evasion) {
		picture = p;
		this.strength = strength;
		this.damagemin = damagemin;
		this.damagemax = damagemax;
		this.race = race;
		this.naturalarmor = naturalarmor;
		this.health = maxhealth;
		this.maxhealth = maxhealth;
		this.attack = attack;
		this.defense = defense;
		this.agility = agility;
		this.resistance = resistance;
		this.swordatt = swordatt;
		this.sworddef = sworddef;
		this.spearatt = spearatt;
		this.speardef = speardef;
		this.axeatt = axeatt;
		this.axedef = axedef;
		this.hammeratt = hammeratt;
		this.hammerdef = hammerdef;
		this.bowskill = bowskill;
		this.crossbowskill = crossbowskill;
		this.mana = maxmana;
		this.maxmana = maxmana;
		this.name = name;
		this.upkeep = upkeep;
		this.evasion = evasion;
	}

	public WeaponInterface getMelee() {
		return melee;
	}

	public void setMelee(WeaponInterface melee) {
		this.melee = melee;
	}

	public WeaponInterface getRanged() {
		return ranged;
	}

	public void setRanged(WeaponInterface ranged) {
		this.ranged = ranged;
	}

	public Spell getSpell1() {
		return spell1;
	}

	public void setSpell1(Spell spell1) {
		this.spell1 = spell1;
	}

	public Spell getSpell2() {
		return spell2;
	}

	public void setSpell2(Spell spell2) {
		this.spell2 = spell2;
	}

	public Armor getArmor() {
		return armor;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}

	public int getDamageMin() {
		return damagemin;
	}

	public void setDamageMin(int damage) {
		this.damagemin = damage;
	}

	public int getDamageMax() {
		return damagemax;
	}

	public void setDamageMax(int damage) {
		this.damagemax = damage;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public int getEvasion() {
		return evasion;
	}

	public void setEvasion(int e) {
		evasion = e;
	}

	public int getNaturalarmor() {
		return naturalarmor;
	}

	public void setNaturalarmor(int naturalarmor) {
		this.naturalarmor = naturalarmor;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxhealth() {
		return maxhealth;
	}

	public void setMaxhealth(int maxhealth) {
		this.maxhealth = maxhealth;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setStrength(int s) {
		strength = s;
	}

	public int getStrength() {
		return strength;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public int getSwordskillAtt() {
		return swordatt;
	}

	public void setSwordskillAtt(int s) {
		this.swordatt = s;
	}

	public int getSwordskillDef() {
		return sworddef;
	}

	public void setSwordskillDef(int s) {
		this.sworddef = s;
	}

	public int getSpearskillAtt() {
		return spearatt;
	}

	public void setSpearskillAtt(int s) {
		this.spearatt = s;
	}

	public int getSpearskillDef() {
		return speardef;
	}

	public void setSpearskillDef(int s) {
		this.speardef = s;
	}

	public int getAxeskillAtt() {
		return axeatt;
	}

	public void setAxeskillAtt(int s) {
		this.axeatt = s;
	}

	public int getAxeskillDef() {
		return axedef;
	}

	public void setAxeskillDef(int s) {
		this.axedef = s;
	}

	public int getHammerskillAtt() {
		return hammeratt;
	}

	public void setHammerskillAtt(int s) {
		this.hammeratt = s;
	}

	public int getHammerskillDef() {
		return hammerdef;
	}

	public void setHammerskillDef(int s) {
		this.hammerdef = s;
	}

	public int getBowskill() {
		return bowskill;
	}

	public void setBowskill(int bowskill) {
		this.bowskill = bowskill;
	}

	public int getCrossbowskill() {
		return crossbowskill;
	}

	public void setCrossbowskill(int crossbowskill) {
		this.crossbowskill = crossbowskill;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMaxmana() {
		return maxmana;
	}

	public void setMaxmana(int maxmana) {
		this.maxmana = maxmana;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUpkeep() {
		return upkeep;
	}

	public void setUpkeep(int upkeep) {
		this.upkeep = upkeep;
	}

	public int getKnockdowns() {
		return knockdowns;
	}

	public void setKnockdowns(int knockdowns) {
		this.knockdowns += knockdowns;
	}

	public ImageIcon getImage() {
		return picture;
	}

	public void setLocation(Point p) {
		location = p;
	}

	public Point getLocation() {
		return location;
	}

	public boolean increaseMeleeAttackSkill() {
		if (this.melee != null) {
			if (melee.getWeaponType().equals("spear") && spearatt < 99) {
				spearatt++;
				return true;
			}
			if (melee.getWeaponType().equals("sword") && swordatt < 99) {
				swordatt++;
				return true;
			}
			if (melee.getWeaponType().equals("axe") && axeatt < 99) {
				axeatt++;
				return true;
			}
			if (melee.getWeaponType().equals("hammer") && hammeratt < 99) {
				hammeratt++;
				return true;
			}
		}
		return false;
	}

	public boolean increaseRangedSkill() {
		if (this.ranged != null) {
			if (ranged.getWeaponType().equals("bow") && bowskill < 99) {
				bowskill++;
				return true;
			}
			if (ranged.getWeaponType().equals("crossbow") && crossbowskill < 99) {
				crossbowskill++;
				return true;
			}
		}
		return false;
	}

	public boolean increaseMeleeDefendSkill() {
		if (this.melee != null) {
			if (melee.getWeaponType().equals("spear") && speardef < 99) {
				speardef++;
				return true;
			}
			if (melee.getWeaponType().equals("sword") && sworddef < 99) {
				sworddef++;
				return true;
			}
			if (melee.getWeaponType().equals("axe") && axedef < 99) {
				axedef++;
				return true;
			}
			if (melee.getWeaponType().equals("hammer") && hammerdef < 99) {
				hammerdef++;
				return true;
			}
		}
		return false;
	}
}
