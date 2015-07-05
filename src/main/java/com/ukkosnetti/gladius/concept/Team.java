package com.ukkosnetti.gladius.concept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ukkosnetti.gladius.ai.AIEquipper;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gladiator.GladiatorGenerator;

public class Team implements Serializable {
	private static final long serialVersionUID = 3363026838342392895L;

	private List<Gladiator> gladiators = new ArrayList<Gladiator>();
	private String name;
	private int squirrels;
	private int league;
	private boolean computer;
	private Integer matchwinscurrentseason = 0;
	private int matchesalready = 0;
	private String opponent1 = "";
	private String opponent2 = "";
	private String opponent3 = "";
	private int op1 = 0; // 1: win, 2: loss, 3: draw
	private int op2 = 0;
	private int matchwinsalltime = 0;
	private int championwins = 0;
	
	private static final String[] TEAM_NAMES = { "Lahnat", "Tontut", "Noobs", "Bottom feeders", "Hellions", "Shell", "Vegetables", "Cemetary shift", "Brutes", "Chutes", "Abs", "Oxmen", "Champions",
			"Black Death", "Blood Fist", "Iron Hammer" };

	public Team(String n, int l, int m, boolean computer) {
		name = n;
		this.computer = computer;
		league = l;
		squirrels = m;
	}

	public Gladiator getGladiator(String n) {
		for (Gladiator gl : gladiators) {
			if (gl.getName().equals(n))
				return gl;
		}
		return null;
	}

	public void resetGladiatorsHealth() {
		for (Gladiator gl : gladiators) {
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

	public List<Gladiator> getGladiators() {
		return gladiators;
	}

	public void setMembers(List<Gladiator> v) {
		gladiators = v;
	}

	public void addGladiator(Gladiator gl) {
		gladiators.add(gl);
	}

	public String getName() {
		return name;
	}

	public void fireGladiator(Gladiator gl) {
		gladiators.remove(gl);
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSquirrels() {
		return squirrels;
	}

	public void addSquirrels(int amount) {
		this.squirrels += amount;
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

	public Integer getMatchWins() {
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

	public boolean isComputer() {
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

	public Integer amountOfGladiatorsBattleWorthy() {
		return Collections2.filter(gladiators, new Predicate<Gladiator>() {

			@Override
			public boolean apply(Gladiator input) {
				return !input.isKnockedOut();
			}

		}).size();
	}

	public static List<Team> getTeams() {
		List<Team> teams = new ArrayList<Team>();
		for (int i = 0; i < 16; i++) {
			Team team = new Team(TEAM_NAMES[i], 4 - ((int)Math.floor(i / 4)), 500, true);
			for (Gladiator gl : GladiatorGenerator.getInstance().generateRandomGladiators(4, new Random(System.currentTimeMillis() + i + 1))) {
				team.addGladiator(gl);
			}
			teams.add(team);
		}
		AIEquipper equipper = new AIEquipper();
		teams.forEach(equipper::equipTeam);
		return teams;
	}

	@Override
	public String toString() {
		return name;
	}
}
