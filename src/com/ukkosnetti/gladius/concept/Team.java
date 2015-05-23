package com.ukkosnetti.gladius.concept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.ukkosnetti.gladius.gladiator.Gladiator;

public class Team implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3363026838342392895L;
	private Vector<Gladiator> gladiators = new Vector<Gladiator>();
	private String name;
	private int squirrels;
	private int members = 0;
	private int league;
	private boolean computer;
	private int matchwinscurrentseason = 0;
	private int matchesalready = 0;
	private String opponent1 = "";
	private String opponent2 = "";
	private String opponent3 = "";
	private int op1 = 0; // 1: win, 2: loss, 3: draw
	private int op2 = 0;
	private int matchwinsalltime = 0;
	private int championwins = 0;

	public Team(String n, int l, int m, boolean computer) {
		name = n;
		this.computer = computer;
		league = l;
		squirrels = m;
	}

	public Gladiator getGladiator(String n) {
		for (Iterator<Gladiator> it = gladiators.iterator(); it.hasNext();) {
			Gladiator gl = it.next();
			if (gl.getName().equals(n))
				return gl;
		}
		return null;
	}

	public void resetGladiatorsHealth() {
		for (Iterator<Gladiator> it = gladiators.iterator(); it.hasNext();) {
			Gladiator gl = it.next();
			gl.setHealth(gl.getMaxhealth());
			gl.setMana(gl.getMaxmana());
		}
	}

	public int payGladiators() {
		int squirrelstart = squirrels;
		for (Iterator<Gladiator> it = gladiators.iterator(); it.hasNext();) {
			this.squirrels = squirrels - it.next().getUpkeep();
		}
		return (squirrelstart - squirrels);
	}

	public Vector<Gladiator> getGladiators() {
		if (gladiators.isEmpty())
			return null;
		return gladiators;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(Vector<Gladiator> v) {
		gladiators = v;
		members = v.size();
	}

	public void addGladiator(Gladiator gl) {
		gladiators.add(gl);
		members++;
	}

	public String getName() {
		return name;
	}

	public void fireGladiator(Gladiator gl) {
		gladiators.remove(gl);
		members--;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSquirrels() {
		return squirrels;
	}

	public void setSquirrels(int squirrels) {
		this.squirrels = squirrels;
	}

	public int getLeague() {
		return league;
	}

	public void setLeague(int league) {
		this.league = league;
	}

	public void resetMatchWins() {
		matchwinscurrentseason = 0;
		matchesalready = 0;
		op1 = 0;
		op2 = 0;
		opponent1 = "";
		opponent2 = "";
		opponent3 = "";
	}

	public void increaseMatchWins() {
		matchwinscurrentseason++;
		matchwinsalltime++;
	}

	public void setResult(int r) {
		if (op1 == 0)
			op1 = r;
		else
			op2 = r;
	}

	public int getResult(int r) {
		if (r == 1)
			return op1;
		else
			return op2;
	}

	public int getMatchWins() {
		return matchwinscurrentseason;
	}

	public void increaseMatchesAlready() {
		matchesalready++;
	}

	public int getMatchesAlready() {
		return matchesalready;
	}

	public void setOpponent1(String s) {
		opponent1 = s;
	}

	public void setOpponent2(String s) {
		opponent2 = s;
	}

	public String getOpponent1() {
		return opponent1;
	}

	public String getOpponent2() {
		return opponent2;
	}

	public void setOpponent3(String s) {
		opponent3 = s;
	}

	public String getOpponent3() {
		return opponent3;
	}

	public boolean getComputer() {
		return computer;
	}

	public void setComputer(boolean c) {
		computer = c;
	}

	public int getChampionWins() {
		return this.championwins;
	}

	public void increaseChampionWins() {
		this.championwins++;
	}

	public int getMatchWinsAllTime() {
		return this.matchwinsalltime;
	}

	// TODO: Temporary method in order to get rid of old dao impl
	public static List<Team> getTeams() {
		List<Team> teams = new ArrayList<Team>();
		for (int i = 0; i < 15; i++) {
			Team team = new Team("Team" + i, 4 - ((int)Math.floor(i / 4)), 500, true);
			team.addGladiator(Gladiator.getGladiators().get(0));
			teams.add(team);
		}
		return teams;
	}
}
