package com.ukkosnetti.gladius.concept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ukkosnetti.gladius.Battle;
import com.ukkosnetti.gladius.Game;
import com.ukkosnetti.gladius.gui.BattlePanel;
import com.ukkosnetti.gladius.gui.View;

public class Season implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6167246742483603869L;
	private Team[][][] matchTable; // 8 matches 2 teams 3 rounds[8][2][3]
	private List<Team> teams;
	private Game game;
	private int currentMatch = 0;
	private int currentRound = 0;

	public Season(List<Team> teams, Game g) {
		game = g;
		this.teams = teams;
		matchTable = new Team[8][2][3];
		for (int i = 0; i < 3; i++) {
			int formerk = 0;
			List<String> names = new ArrayList<String>();
			for (int j = 0; j < 8; j++) {
				for (int k = formerk; k < teams.size(); k++) {
					if (!(teams.get(k).getMatchesAlready() >= 3) && !(names.contains(teams.get(k).getName()))) {
						matchTable[j][0][i] = teams.get(k);
						teams.get(k).increaseMatchesAlready();
						int league = teams.get(k).getLeague();
						String name1 = teams.get(k).getOpponent1();
						String name2 = teams.get(k).getOpponent2();
						names.add(teams.get(k).getName());
						formerk = k;
						for (k = k + 1; k < teams.size(); k++) {
							if (teams.get(k).getLeague() == league && !(teams.get(k).getMatchesAlready() >= 3) && !(teams.get(k).getName().equals(name1) || teams.get(k).getName().equals(name2))
									&& !(names.contains(teams.get(k).getName()))) {
								matchTable[j][1][i] = teams.get(k);
								names.add(teams.get(k).getName());
								teams.get(k).increaseMatchesAlready();
								if (teams.get(k).getOpponent1().equals(""))
									teams.get(k).setOpponent1(teams.get(formerk).getName());
								else if (teams.get(k).getOpponent2().equals(""))
									teams.get(k).setOpponent2(teams.get(formerk).getName());
								else
									teams.get(k).setOpponent3(teams.get(formerk).getName());
								if (teams.get(formerk).getOpponent1().equals(""))
									teams.get(formerk).setOpponent1(teams.get(k).getName());
								else if (teams.get(formerk).getOpponent2().equals(""))
									teams.get(formerk).setOpponent2(teams.get(k).getName());
								else
									teams.get(formerk).setOpponent3(teams.get(k).getName());
								k = teams.size();
							}
						}
						formerk++;
					}
				}
			}
		}
	}

	public Team getTeamAForCurrentBattle() {
		return matchTable[currentMatch][0][currentRound];
	}

	public Team getTeamBForCurrentBattle() {
		return matchTable[currentMatch][1][currentRound];
	}

	public void nextBattle(BattlePanel bp, View v) {
		if (currentMatch >= 7) {
			currentMatch = 0;
			v.changePanel("mp");
			v.addText("Current round of arena battles are over. ");
			v.enableStuff();
			game.roundOverStartFromFirstTeam();
			v.setTeamName(game.getActiveTeamName());
			v.setSquirrels(game.getActiveSquirrels());
			v.clearGladiatorPanels();
			v.addGladiatorstoPanels(game.getCurrentGladiators(true));
			v.showGladiator(game.getCurrentGladiator());
			v.makeRed();
			if (currentRound >= 2) {
				v.addText("Current season is over.");
				game.newSeason(v);
			} else
				currentRound++;
		} else {
			currentMatch++;
			new Battle(this.getTeamAForCurrentBattle(), this.getTeamBForCurrentBattle(), this, bp, v);
		}
	}

	public void clearTeamBattlesInfo() {
		Iterator<Team> it = teams.iterator();
		while (it.hasNext())
			it.next().resetMatchWins();
	}

}
