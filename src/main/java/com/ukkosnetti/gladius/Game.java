package com.ukkosnetti.gladius;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.ukkosnetti.gladius.concept.Season;
import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.BattlePanel;
import com.ukkosnetti.gladius.gui.View;
import com.ukkosnetti.gladius.item.Armor;
import com.ukkosnetti.gladius.item.MeleeWeapon;
import com.ukkosnetti.gladius.item.RangedWeapon;
import com.ukkosnetti.gladius.item.Spell;
import com.ukkosnetti.gladius.shop.Blacksmith;
import com.ukkosnetti.gladius.shop.Spellshop;
import com.ukkosnetti.gladius.shop.Tavern;

public class Game implements Serializable {

	private static final int STARTING_SQUIRRELS = 1000;

	private static final int MAXIMUM_TEAM_SIZE = 6;

	private static final long serialVersionUID = -4919572168403078010L;
	private Tavern tavern;
	private Blacksmith blacksmith;
	private Spellshop spellshop;
	private List<Team> teams;
	private Team activeTeam;
	private Gladiator currentGladiator = null;
	private Season season;
	private Battle battle;
	private int currentseason;
	private int humanplayers = 0;

	public Game() {
		tavern = new Tavern();
		blacksmith = new Blacksmith();
		spellshop = new Spellshop();
		teams = Team.getTeams();
		currentseason = 500;
	}

	public void setTeams(List<String> teamNames) {
		humanplayers += teamNames.size();
		List<Team> aiTeamsToRemove = new ArrayList<>();
		for (Team team : teams) {
			if (team.getLeague() == 4 && team.isComputer() && aiTeamsToRemove.size() < humanplayers)
				aiTeamsToRemove.add(team);
		}
		teams.removeAll(aiTeamsToRemove);
		for (String teamName : teamNames) {
			teams.add(new Team(teamName, 4, STARTING_SQUIRRELS, false));
		}
		activeTeam = Iterables.find(teams, new Predicate<Team>() {

			@Override
			public boolean apply(Team input) {
				return !input.isComputer();
			}

		});
		season = new Season(teams, this);
	}

	public int getHumanPlayers() {
		return humanplayers;
	}

	public boolean newBattle(BattlePanel bp, View v) {
		int loc = 0;
		for (int i = teams.size() - 1; i >= 0; i--) {
			if (activeTeam.getName().equals(teams.get(i).getName())) {
				loc = i;
			}
		}
		for (int i = loc - 1; i >= 0; i--) {
			if (!(teams.get(i).isComputer())) {
				activeTeam = teams.get(i);
				return false;
			}
		}
		battle = new Battle(season.getTeamAForCurrentBattle(), season.getTeamBForCurrentBattle(), season, bp, v);
		return true;
	}

	public void roundOverStartFromFirstTeam() {
		for (int i = teams.size() - 1; i >= 0; i--) {
			if (!(teams.get(i).isComputer())) {
				activeTeam = teams.get(i);
				i = -1;
			}
		}
	}

	public void setComputer(Team t) {
		t.setComputer(true);
		humanplayers--;
	}

	public Battle getBattle() {
		return battle;
	}

	public void setCurrentGladiator(Gladiator gl) {
		currentGladiator = gl;
	}

	public Gladiator getCurrentGladiator() {
		return currentGladiator;
	}

	public Team getActiveTeam() {
		return activeTeam;
	}

	public boolean canHire() {
		if (activeTeam.getGladiators().size() >= MAXIMUM_TEAM_SIZE)
			return false;
		else
			return true;
	}

	public String getActiveSquirrels() {
		return activeTeam.getSquirrels() + " sq";
	}

	public boolean hireGladiator(String n) {
		boolean success = tavern.purchase(activeTeam, n, null, null);
		if (success) {
			activeTeam.addGladiator(tavern.getGladiator(n));
			tavern.takeGladiators(n);
		}
		return success;
	}

	public void purchaseItem(String n, View v) {
		boolean success = blacksmith.purchase(activeTeam, n, currentGladiator, v);
		if (!success)
			success = spellshop.purchase(activeTeam, n, currentGladiator, v);
		if (success) {
			v.setSquirrels(this.getActiveSquirrels());
			v.showGladiator(currentGladiator);
		} else
			v.addText("Get out of here you beggar!");
	}

	public boolean checkIfBeholder() {
		if (currentGladiator.getRace().equals("Beholder") || currentGladiator.getRace().equals("Beholder_Hero"))
			return true;
		else
			return false;
	}

	public List<Gladiator> getCurrentGladiators(boolean change) {
		if (activeTeam.getGladiators() != null && !activeTeam.getGladiators().isEmpty() && change)
			currentGladiator = activeTeam.getGladiators().get(0);
		return activeTeam.getGladiators();
	}

	public String getActiveTeamName() {
		return activeTeam.getName();
	}

	public boolean changeTeamName(String s) {
		if (!this.teamNameExists(s)) {
			activeTeam.setName(s);
			return true;
		} else
			return false;
	}

	public boolean teamNameExists(String s) {
		for (int i = teams.size() - 1; i >= 0; i--) {
			if (teams.get(i).getName().equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}

	public Gladiator getGladiator(String n, String where) {
		Gladiator g = null;
		if (where.equals("TAVERN")) {
			g = tavern.getGladiator(n);
		}
		return g;
	}

	public List<MeleeWeapon> getMeleeBlacksmith() {
		return blacksmith.getMelees();
	}

	public List<RangedWeapon> getRangedBlacksmith() {
		return blacksmith.getRangeds();
	}

	public List<Armor> getArmorBlacksmith() {
		return blacksmith.getArmors();
	}

	public List<Gladiator> getTavernGladiators() {
		return tavern.getGladiators();
	}

	public List<Spell> getSpells(boolean d) {
		if (d)
			return spellshop.getDamageSpells();
		else
			return spellshop.getHealingSpells();
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void newSeason(View v) {
		Team champion = season.getChampion();
		champion.addSquirrels(5000);
		champion.increaseChampionWins();
		List<Team> risers = season.getLeagueRisers();
		List<Team> lowers = season.getLeagueLowers();
		risers.forEach(riser -> { 
			riser.addSquirrels(500);
			riser.setLeague(riser.getLeague() - 1);
		});
		lowers.forEach(lower -> {
			lower.setLeague(lower.getLeague() + 1);
		});
		new StringBuilder();
		v.addText(champion.getName().toUpperCase() + " IS THE CHAMPION OF SEASON AND EARNS 5000 SQUIRRELS!");
		v.addText("Following teams have risen up in the leagueladder: " + risers + ". All ladder-risers earn 500 bonus squirrels!");
		v.addText("While following teams have succumbed to lower league: " + lowers + ".");
		v.addText("New season has started!");
		season.clearTeamBattlesInfo();
		season = new Season(teams, this);
		currentseason++;
		v.setCurSeason(currentseason);
		v.setSquirrels(this.getActiveSquirrels());
	}

	public int getCurrentSeason() {
		return currentseason;
	}

	public void fireGladiator() {
		activeTeam.fireGladiator(currentGladiator);
		currentGladiator = null;
		getCurrentGladiators(true);
	}

	public ArrayList<File> getSavedGames() {
		ArrayList<File> files = new ArrayList<File>();
		File folder = new File("saves");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String name = listOfFiles[i].getName();
				if (name.contains(".gla") && listOfFiles[i].canWrite()) {
					files.add(listOfFiles[i]);
				}
			}
		}
		return files;
	}

	public void saveGame(String n) {
		try {
			this.writeObject(n);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public Game loadGame(String n) {
		Game ga = null;
		try {
			ga = this.readObject(n);
		} catch (ClassNotFoundException | IOException ex) {
			ex.printStackTrace();
		}
		return ga;
	}

	private Game readObject(String n) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("saves/" + n + ".gla"));
			return (Game) ois.readObject();
		} catch (EOFException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}


	private void writeObject(String file) throws IOException {
		FileOutputStream fos = new FileOutputStream("saves/" + file + ".gla");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
		fos.close();
	}

}