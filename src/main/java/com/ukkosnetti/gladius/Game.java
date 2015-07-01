package com.ukkosnetti.gladius;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	/**
	 * 
	 */
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

	public void setTeams(String t[]) {
		if (t[3] != null && !t[3].equals("")) {
			for (int i = 0; i < teams.size(); i++)
				if (teams.get(i).getLeague() == 4 && (teams.get(i).getComputer())) {
					teams.remove(teams.get(i));
					i = teams.size();
				}
			activeTeam = new Team(t[3], 4, 1000, false);
			teams.add(activeTeam);
			humanplayers++;
		}
		if (t[2] != null && !t[2].equals("")) {
			for (int i = 0; i < teams.size(); i++)
				if (teams.get(i).getLeague() == 4 && (teams.get(i).getComputer())) {
					teams.remove(teams.get(i));
					i = teams.size();
				}
			activeTeam = new Team(t[2], 4, 1000, false);
			teams.add(activeTeam);
			humanplayers++;
		}
		if (t[1] != null && !t[1].equals("")) {
			for (int i = 0; i < teams.size(); i++)
				if (teams.get(i).getLeague() == 4 && (teams.get(i).getComputer())) {
					teams.remove(teams.get(i));
					i = teams.size();
				}
			activeTeam = new Team(t[1], 4, 1000, false);
			teams.add(activeTeam);
			humanplayers++;
		}
		if (t[0] != null && !t[0].equals("")) {
			for (int i = 0; i < teams.size(); i++)
				if (teams.get(i).getLeague() == 4 && (teams.get(i).getComputer())) {
					teams.remove(teams.get(i));
					i = teams.size();
				}
			activeTeam = new Team(t[0], 4, 1000, false);
			teams.add(activeTeam);
			humanplayers++;
		}
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
			if (!(teams.get(i).getComputer())) {
				activeTeam = teams.get(i);
				return false;
			}
		}
		battle = new Battle(season.getTeamAForCurrentBattle(), season.getTeamBForCurrentBattle(), season, bp, v);
		return true;
	}

	public void roundOverStartFromFirstTeam() {
		for (int i = teams.size() - 1; i >= 0; i--) {
			if (!(teams.get(i).getComputer())) {
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
		if (activeTeam.getMembers() >= 6)
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
		if (this.checkExistingTeamNames(s)) {
			activeTeam.setName(s);
			return true;
		} else
			return false;
	}

	public boolean checkExistingTeamNames(String s) {
		for (int i = teams.size() - 1; i >= 0; i--) {
			if (teams.get(i).getName().equals(s)) {
				return false;
			}
		}
		return true;
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
		String league1high = null, league2high = null, league3high = null, league4high = null, league1low = null, league2low = null, league3low = null;
		int league1highint = -1, league2highint = -1, league3highint = -1, league4highint = -1, league1lowint = -1, league2lowint = -1, league3lowint = -1;
		Iterator<Team> it = teams.iterator();
		int co = 0;
		while (it.hasNext()) {
			Team apu = it.next();
			int i = apu.getLeague();
			switch (i) {
			case 1:
				if (league1high != null) {
					if (teams.get(league1highint).getMatchWins() < apu.getMatchWins()) {
						if (league1low != null) {
							if (teams.get(league1highint).getMatchWins() < teams.get(league1lowint).getMatchWins()) {
								league1lowint = league1highint;
								league1low = league1high;
							}
						} else {
							league1lowint = league1highint;
							league1low = league1high;
						}
						league1highint = co;
						league1high = apu.getName();
					} else {
						if (league1low != null) {
							if (apu.getMatchWins() < teams.get(league1lowint).getMatchWins()) {
								league1lowint = co;
								league1low = apu.getName();
							}
						} else {
							league1lowint = co;
							league1low = apu.getName();
						}
					}
				} else {
					league1high = apu.getName();
					league1highint = co;
				}
				break;
			case 2:
				if (league2high != null) {
					if (teams.get(league2highint).getMatchWins() < apu.getMatchWins()) {
						if (league2low != null) {
							if (teams.get(league2highint).getMatchWins() < teams.get(league2lowint).getMatchWins()) {
								league2lowint = league2highint;
								league2low = league2high;
							}
						} else {
							league2lowint = league2highint;
							league2low = league2high;
						}
						league2highint = co;
						league2high = apu.getName();
					} else {
						if (league2low != null) {
							if (apu.getMatchWins() < teams.get(league2lowint).getMatchWins()) {
								league2lowint = co;
								league2low = apu.getName();
							}
						} else {
							league2lowint = co;
							league2low = apu.getName();
						}
					}
				} else {
					league2high = apu.getName();
					league2highint = co;
				}
				break;
			case 3:
				if (league3high != null) {
					if (teams.get(league3highint).getMatchWins() < apu.getMatchWins()) {
						if (league3low != null) {
							if (teams.get(league3highint).getMatchWins() < teams.get(league3lowint).getMatchWins()) {
								league3lowint = league3highint;
								league3low = league3high;
							}
						} else {
							league3lowint = league3highint;
							league3low = league3high;
						}
						league3highint = co;
						league3high = apu.getName();
					} else {
						if (league3low != null) {
							if (apu.getMatchWins() < teams.get(league3lowint).getMatchWins()) {
								league3lowint = co;
								league3low = apu.getName();
							}
						} else {
							league3lowint = co;
							league3low = apu.getName();
						}
					}
				} else {
					league3high = apu.getName();
					league3highint = co;
				}
				break;
			case 4:
				if (league4high != null) {
					if (teams.get(league4highint).getMatchWins() < apu.getMatchWins()) {
						league4highint = co;
						league4high = apu.getName();
					}
				} else {
					league4high = apu.getName();
					league4highint = co;
				}
				break;
			}
			co++;
		}
		// change league statuses:
		Iterator<Team> it2 = teams.iterator();
		while (it2.hasNext()) {
			Team apu = it2.next();
			if (apu.getName().equals(league1high)) {
				apu.setSquirrels(apu.getSquirrels() + 5000);
				apu.increaseChampionWins();
			}
			if (apu.getName().equals(league2high)) {
				apu.setLeague(1);
				apu.setSquirrels(apu.getSquirrels() + 500);
			}
			if (apu.getName().equals(league3high)) {
				apu.setLeague(2);
				apu.setSquirrels(apu.getSquirrels() + 500);
			}
			if (apu.getName().equals(league4high)) {
				apu.setLeague(3);
				apu.setSquirrels(apu.getSquirrels() + 500);
			}
			if (apu.getName().equals(league1low)) {
				apu.setLeague(2);
			}
			if (apu.getName().equals(league2low)) {
				apu.setLeague(3);
			}
			if (apu.getName().equals(league3low)) {
				apu.setLeague(4);
			}
		}
		v.addText(league1high.toUpperCase() + " IS THE CHAMPION OF SEASON AND EARNS 5000 SQUIRRELS!");
		v.addText("Following teams have risen up in the leagueladder: " + league2high + ", " + league3high + " and " + league4high + ". All ladder-risers earn 500 bonus squirrels!");
		v.addText("While following teams have succumbed to lower league: " + league1low + ", " + league2low + " and " + league3low + ".");
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
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return ga;
	}

	private Game readObject(String n) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		Game ga = null;
		// Construct the ObjectInputStream object
		try {
			ois = new ObjectInputStream(new FileInputStream("saves/" + n + ".gla"));
			Object obj = null;
			if ((obj = ois.readObject()) != null) {
				if (obj instanceof Game) {
					ga = (Game) obj;
					// System.out.println(ga.getActiveTeamName());
					// System.out.println(ga.getCurrentGladiator().getName());

				}
			}
		} catch (EOFException ex) {
			ex.printStackTrace();
		}
		// This exception will be caught when EOF is reached

		// System.out.println(ga.getActiveTeamName());
		if (ois != null) {
			ois.close();
		}
		return ga;
	}

	private void writeObject(String file) throws IOException {
		FileOutputStream fos = new FileOutputStream("saves/" + file + ".gla");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
		fos.close();
	}

}