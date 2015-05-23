/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ukkosnetti.gladius;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.item.Armor;
import com.ukkosnetti.gladius.item.MeleeWeapon;
import com.ukkosnetti.gladius.item.RangedWeapon;
import com.ukkosnetti.gladius.item.Spell;
import com.ukkosnetti.gladius.item.WeaponInterface;

public class DAO {
	// Classes and variables
	private ResultSet myRs;
	private Statement myStatement;
	private Connection myCon;
	private final String filename = "res/gladius.mdb";
	private String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	private Vector<Gladiator> gladiators;
	private static DAO instance = null;

	public static synchronized DAO getInstance() {

		if (instance == null) {
			instance = new DAO();
		}
		return instance;

	}

	public DAO() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			database += filename.trim() + ";";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vector<Spell> searchSpells(boolean damage) {
		Vector<Spell> spells = new Vector<Spell>();
		int mindamage, maxdamage;
		int price, mana;
		boolean damagespell;
		String name;
		try {
			String sqlSelect;
			if (damage)
				sqlSelect = "SELECT * FROM Spell WHERE damagespell";
			else
				sqlSelect = "SELECT * FROM Spell WHERE NOT damagespell";
			myCon = DriverManager.getConnection(database, "", "");
			myStatement = myCon.createStatement();
			myRs = myStatement.executeQuery(sqlSelect);
			while (myRs.next()) {
				name = myRs.getString("name");
				mindamage = myRs.getInt("damagemin");
				maxdamage = myRs.getInt("damagemax");
				price = myRs.getInt("price");
				mana = myRs.getInt("mana");
				damagespell = myRs.getBoolean("damagespell");
				spells.add(new Spell(mindamage, maxdamage, damagespell, name, price, mana));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (myRs != null)
					myRs.close();
				if (myStatement != null)
					myStatement.close();
				if (myCon != null)
					myCon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return spells;
	}

	public Vector<Armor> searchArmors() {
		Vector<Armor> armors = new Vector<Armor>();
		int armor;
		int price;
		String name;
		try {
			String sqlSelect = "SELECT * FROM Armor";
			myCon = DriverManager.getConnection(database, "", "");
			myStatement = myCon.createStatement();
			myRs = myStatement.executeQuery(sqlSelect);
			while (myRs.next()) {
				name = myRs.getString("name");
				armor = myRs.getInt("armor");
				price = myRs.getInt("price");
				armors.add(new Armor(armor, name, price));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (myRs != null)
					myRs.close();
				if (myStatement != null)
					myStatement.close();
				if (myCon != null)
					myCon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return armors;
	}

	public Vector<RangedWeapon> searchRangeds() {
		Vector<RangedWeapon> rangeds = new Vector<RangedWeapon>();
		int mindamage;
		int maxdamage;
		int price;
		String type;
		String name;
		try {
			String sqlSelect = "SELECT * FROM Weapon WHERE weapontype='bow' OR weapontype='crossbow'";
			myCon = DriverManager.getConnection(database, "", "");
			myStatement = myCon.createStatement();
			myRs = myStatement.executeQuery(sqlSelect);
			while (myRs.next()) {
				name = myRs.getString("name");
				type = myRs.getString("weapontype");
				price = myRs.getInt("price");
				mindamage = myRs.getInt("damagemin");
				maxdamage = myRs.getInt("damagemax");
				rangeds.add(new RangedWeapon(mindamage, maxdamage, type, name, price));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (myRs != null)
					myRs.close();
				if (myStatement != null)
					myStatement.close();
				if (myCon != null)
					myCon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rangeds;
	}

	public Vector<MeleeWeapon> searchMelees() {
		Vector<MeleeWeapon> melees = new Vector<MeleeWeapon>();
		int damagemin;
		int damagemax;
		int price;
		String type;
		String name;
		try {
			String sqlSelect = "SELECT * FROM Weapon WHERE NOT weapontype='bow' AND NOT weapontype='crossbow'";
			myCon = DriverManager.getConnection(database, "", "");
			myStatement = myCon.createStatement();
			myRs = myStatement.executeQuery(sqlSelect);
			while (myRs.next()) {
				name = myRs.getString("name");
				type = myRs.getString("weapontype");
				price = myRs.getInt("price");
				damagemin = myRs.getInt("damagemin");
				damagemax = myRs.getInt("damagemax");
				melees.add(new MeleeWeapon(damagemin, damagemax, type, name, price));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (myRs != null)
					myRs.close();
				if (myStatement != null)
					myStatement.close();
				if (myCon != null)
					myCon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return melees;
	}

	public Vector<Gladiator> searchGladiators(String Team, Game g) {
		gladiators = new Vector<Gladiator>();
		WeaponInterface melee = null;
		WeaponInterface ranged = null;
		Spell spell1 = null;
		Spell spell2 = null;
		Armor armor = null;
		int damagemin;
		int damagemax;
		String race;
		int naturalarmor;
		int maxhealth;
		int strength;
		int attack;
		int defense;
		int agility;
		int evasion;
		int resistance;
		int swordatt;
		int sworddef;
		int spearatt;
		int speardef;
		int axeatt;
		int axedef;
		int hammeratt;
		int hammerdef;
		int bowskill;
		int crossbowskill;
		int maxmana;
		String name;
		int upkeep;
		ImageIcon picture;
		int i = 0;
		try {
			String sqlSelect = "SELECT * FROM Gladiators, Attributes WHERE team='" + Team + "' AND Gladiators.race = Attributes.race";
			myCon = DriverManager.getConnection(database, "", "");
			myStatement = myCon.createStatement();
			myRs = myStatement.executeQuery(sqlSelect);
			while (myRs.next()) {
				name = myRs.getString("name");
				race = myRs.getString("race");
				picture = new ImageIcon("res/" + race + ".gif");
				String meleestr = myRs.getString("melee");
				if (meleestr == null || meleestr.isEmpty())
					melee = null;
				else
					melee = g.getWeaponBlacksmith(meleestr);
				String rangedstr = myRs.getString("ranged");
				if (rangedstr == null || rangedstr.isEmpty())
					ranged = null;
				else
					ranged = g.getWeaponBlacksmith(rangedstr);
				String spell1str = myRs.getString("spell1");
				if (spell1str == null || spell1str.isEmpty())
					spell1 = null;
				else
					spell1 = g.getOneSpell(spell1str);
				String spell2str = myRs.getString("spell2");
				if (spell2str == null || spell2str.isEmpty())
					spell2 = null;
				else
					spell2 = g.getOneSpell(spell2str);
				String armorstr = myRs.getString("armor");
				if (armorstr == null || armorstr.isEmpty())
					armor = null;
				else
					armor = g.getOneArmor(armorstr);
				agility = myRs.getInt("agility");
				damagemin = myRs.getInt("damageMin");
				damagemax = myRs.getInt("damageMax");
				swordatt = myRs.getInt("swordAtt") + (int) (agility / 3);
				sworddef = myRs.getInt("swordDef") + (int) (agility / 3);
				spearatt = myRs.getInt("spearAtt") + (int) (agility / 3);
				speardef = myRs.getInt("spearDef") + (int) (agility / 3);
				axeatt = myRs.getInt("axeAtt") + (int) (agility / 3);
				axedef = myRs.getInt("axeDef") + (int) (agility / 3);
				hammeratt = myRs.getInt("hammerAtt") + (int) (agility / 3);
				hammerdef = myRs.getInt("hammerDef") + (int) (agility / 3);
				naturalarmor = myRs.getInt("naturalArmor");
				maxhealth = myRs.getInt("maxHealth");
				maxmana = myRs.getInt("maxMana");
				attack = myRs.getInt("attack");
				defense = myRs.getInt("defence");
				resistance = myRs.getInt("resistance");
				upkeep = myRs.getInt("upkeep");
				bowskill = myRs.getInt("bowAtt");
				crossbowskill = myRs.getInt("crossbowAtt");
				evasion = (int) (agility * 1.5);
				strength = damagemax - (int) (damagemin / 2);
				gladiators.add(new Gladiator(damagemin, damagemax, race, naturalarmor, maxhealth, attack, defense, agility, resistance, swordatt, sworddef, spearatt, speardef, axeatt, axedef,
						hammeratt, hammerdef, bowskill, crossbowskill, maxmana, name, upkeep, picture, strength, evasion));
				gladiators.elementAt(i).setMelee(melee);
				gladiators.elementAt(i).setRanged(ranged);
				gladiators.elementAt(i).setSpell2(spell2);
				gladiators.elementAt(i).setSpell1(spell1);
				gladiators.elementAt(i).setArmor(armor);

				i++;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (myRs != null)
					myRs.close();
				if (myStatement != null)
					myStatement.close();
				if (myCon != null)
					myCon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return gladiators;
	}

	public Vector<Team> searchTeams(Game g) {
		Vector<Team> team = new Vector<Team>();
		String name;
		int money;
		boolean computer;
		int league;

		try {
			String sqlSelect = "SELECT * FROM Teams WHERE NOT name='Tavern'";
			myCon = DriverManager.getConnection(database, "", "");
			myStatement = myCon.createStatement();
			myRs = myStatement.executeQuery(sqlSelect);
			while (myRs.next()) {
				name = myRs.getString("name");
				money = myRs.getInt("money");
				computer = myRs.getBoolean("computer");
				league = myRs.getInt("league");
				team.add(new Team(name, league, money, computer));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				if (myRs != null)
					myRs.close();
				if (myStatement != null)
					myStatement.close();
				if (myCon != null)
					myCon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int i = 0;
		while (i < team.size()) {
			team.elementAt(i).setMembers(this.searchGladiators(team.elementAt(i).getName(), g));
			i++;
		}
		return team;
	}
}
