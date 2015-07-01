package com.ukkosnetti.gladius.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ukkosnetti.gladius.Game;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.About;
import com.ukkosnetti.gladius.gui.Askstuff;
import com.ukkosnetti.gladius.gui.BattlePanel;
import com.ukkosnetti.gladius.gui.LoadGame;
import com.ukkosnetti.gladius.gui.MainPanel;
import com.ukkosnetti.gladius.gui.NewGameView;
import com.ukkosnetti.gladius.gui.SaveGame;
import com.ukkosnetti.gladius.gui.SeasonPanel;
import com.ukkosnetti.gladius.gui.ShopPanel;
import com.ukkosnetti.gladius.gui.TavernPanel;
import com.ukkosnetti.gladius.gui.TeamPanel;
import com.ukkosnetti.gladius.gui.TopKOs;
import com.ukkosnetti.gladius.gui.TopTeams;
import com.ukkosnetti.gladius.gui.View;

/*
 * Controller acts as an action and mouse listener for various GUI components especially View.
 */
public class Controller implements ActionListener, MouseListener {

	private Game game;
	private View view;
	private LoadGame loadGame;
	private SaveGame saveGame;
	private NewGameView newGameView;
	private SeasonPanel seasonPanel;
	private TavernPanel tavernPanel;
	private ShopPanel shopPanel;
	private BattlePanel battlePanel;
	private TeamPanel teamPanel;
	private About about;
	private TopKOs topKOs;
	private TopTeams topteam;
	private Askstuff askStuff;
	private boolean gamestarted = false;
	private boolean itemsadded = false;
	private int whichaskaction = 0;

	public Controller(View v, Game g, MainPanel m, TavernPanel t, ShopPanel s, BattlePanel b, SeasonPanel sep, TeamPanel tep) {
		this.game = g;
		this.view = v;
		this.seasonPanel = sep;
		tavernPanel = t;
		shopPanel = s;
		battlePanel = b;
		this.teamPanel = tep;
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
		if (ae.getActionCommand().equals("NEW")) {
			if (newGameView != null)
				newGameView.dispose();
			newGameView = new NewGameView(this);
		}
		if (ae.getActionCommand().equals("HELP")) {
			try {
				String command = "rundll32 url.dll,FileProtocolHandler " + "USER_MANUAL.pdf";
				Runtime.getRuntime().exec(command);
			} catch (Exception e) {
				view.addText("Could not open pdf file");
			}
		}
		if (ae.getActionCommand().equals("ABOUT")) {
			if (about != null)
				about.dispose();
			about = new About();
		}
		if (ae.getActionCommand().equals("START_NEW_GAME")) {
			if (gamestarted)
				game = new Game();
			List<String> teamNames = newGameView.getTeamNames();
			if (validateTeamNames(teamNames)) {
				view.setTeamName(teamNames.get(0));
				game.setTeams(teamNames.toArray(new String[teamNames.size()]));
				gamestarted = true;
				newGameView.disposeThis();
				view.clearGladiatorPanels();
				view.setTeamName(game.getActiveTeamName());
				view.setSquirrels(game.getActiveSquirrels());
				view.changePanel("mp");
				view.enableStuff();
				view.setCurSeason(game.getCurrentSeason());
				view.addText("O noble player, thy first task in this game was a magnificent success!\nNow recruit gladiators, purchase equipments and spells and enter arena!");
			} else
				view.addText("Invalid names!");
		}
		if (ae.getActionCommand().equals("LOAD")) {
			if (loadGame == null)
				;
			else
				loadGame.dispose();
			ArrayList<File> files = game.getSavedGames();
			if (!files.isEmpty()) {
				loadGame = new LoadGame(game.getSavedGames(), this);
			} else {
				view.addText("No saved games exist.");
			}
		}
		if (ae.getActionCommand().equals("LOADTHISGAME")) {
			view.changePanel("mp");
			Game gah = game.loadGame(loadGame.getSelectedFile());
			if (gah != null) {
				game = gah;
			}
			view.clearGladiatorPanels();
			view.addGladiatorstoPanels(game.getCurrentGladiators(true));
			view.showGladiator(game.getCurrentGladiator());
			view.setTeamName(game.getActiveTeamName());
			view.setSquirrels(game.getActiveSquirrels());
			view.setCurSeason(game.getCurrentSeason());
			view.enableStuff();
			gamestarted = true;
			loadGame.dispose();
		}
		if (gamestarted) {
			if (ae.getActionCommand().equals("SAVE")) {
				if (saveGame == null) {
					saveGame = new SaveGame(this);
				} else {
					saveGame.dispose();
					saveGame = new SaveGame(this);
				}
			}
			if (ae.getActionCommand().equals("SAVETHISGAME")) {
				game.saveGame(saveGame.getSavedName());
				saveGame.dispose();
				view.changePanel("mp");
			}
			if (ae.getActionCommand().equals("RESIGN")) {
				if (game.getHumanPlayers() > 1) {
					if (!(game.getCurrentGladiators(true) == null)) {
						whichaskaction = 2;
						view.setEnabled(false);
						askStuff = new Askstuff("Are thou sure thou want to resign from glamorous manager position?", this);
					} else
						view.addText("Thou must have at least one active gladiator in team before resigning.");
				} else
					view.addText("Position of manager can only be resigned when more than one h�b�tti is managing the teams (in other words, there must be more than one player in game).");
			}
			if (ae.getActionCommand().equals("STARTBATTLE")) {
				if (!(game.getCurrentGladiators(true) == null)) {
					boolean battlestart = game.newBattle(battlePanel, view);

					if (battlestart) {
						view.changePanel("bp");
						view.disableStuff();
					} else {
						view.clearGladiatorPanels();
						view.setSquirrels(game.getActiveSquirrels());
						view.setTeamName(game.getActiveTeamName());
						view.addGladiatorstoPanels(game.getCurrentGladiators(true));
						view.showGladiator(game.getCurrentGladiator());
					}
				} else
					view.addText("Manager refuses to enter arena. Alas! Thou must hire gladiator.");
			}
			if (ae.getActionCommand().equals("TAVERN")) {
				view.addText("Thou has entered a local tavern. In the dark corners you can see variety of creeps howling for a chance to be gladiator.");
				List<Gladiator> vec = game.getTavernGladiators();
				String name[] = new String[vec.size()];
				String race[] = new String[vec.size()];
				int price[] = new int[vec.size()];
				Iterator<Gladiator> it = vec.iterator();
				int i = 0;
				while (it.hasNext()) {
					Gladiator apu = it.next();
					name[i] = apu.getName();
					race[i] = apu.getRace();
					price[i] = 10 * apu.getUpkeep();
					i++;
				}
				tavernPanel.setGladiators(name, race, price);
				tavernPanel.setRow();
				view.changePanel("tp");
			}
			if (ae.getActionCommand().equals("BLACKSMITH")) {
				if (!itemsadded) {
					view.addText("Greetings noble customer! I have a wide range of different armaments, exquisitive armor and exotic spells available for extraordinary prices. \nFeel free to browse my vast selection of goods and peep if anything is of interest.");
					shopPanel.addShopItems(game.getMeleeBlacksmith(), game.getRangedBlacksmith(), game.getArmorBlacksmith(), game.getSpells(true), game.getSpells(false));
					itemsadded = true;
				} else
					view.addText("Thou has entered a local shop. \nVariety of different armors and weapons, that are covered with cobwebs, are stockpiled here.");
				view.changePanel("sp");
			}
			if (ae.getActionCommand().equals("KO")) {
				if (topKOs != null)
					topKOs.dispose();
				topKOs = new TopKOs(game.getTeams());
			}
			if (ae.getActionCommand().equals("TOP_TEAMS")) {
				if (topteam != null)
					topteam.dispose();
				topteam = new TopTeams(game.getTeams());
			}
			if (ae.getActionCommand().equals("SEASON")) {
				seasonPanel.setText(game.getActiveTeam());
				view.changePanel("sep");
			}
			if (ae.getActionCommand().equals("OTHERTEAMS")) {
				teamPanel.setText(game.getTeams());
				view.changePanel("tep");
			}
			if (ae.getActionCommand().equals("TEAM_NAME")) {
				view.teamorgladiatorNameOpen(true, game.getActiveTeamName());
			}
			if (ae.getActionCommand().equals("GLADIATOR_NAME")) {
				if (!(game.getCurrentGladiators(false) == null))
					view.teamorgladiatorNameOpen(false, game.getCurrentGladiator().getName());
				else
					view.addText("No gladiator selected.");
			}
			if (ae.getActionCommand().equals("CHANGE_GLADIATOR_NAME")) {
				String n = view.getName();
				if (!n.equals("") && !n.isEmpty()) {
					game.getCurrentGladiator().setName(n);
					view.showGladiator(game.getCurrentGladiator());
				}
			}
			if (ae.getActionCommand().equals("CHANGE_TEAM_NAME")) {
				String n = view.getName();
				if (!n.equals("") && !n.isEmpty()) {
					if (game.changeTeamName(n)) {
						view.setTeamName(n);
					} else {
						view.addText("There is already a team with that name.");
					}
				}
			}
			if (ae.getActionCommand().equals("TAVERN_LEAVE")) {
				view.changePanel("mp");
			}
			if (ae.getActionCommand().equals("TAVERN_HIRE")) {
				if (game.canHire()) {
					String n = tavernPanel.getSelectedGladiator();
					boolean success = game.hireGladiator(n);
					if (success) {
						view.addText("You hired a brand old gladiator.");
						view.changePanel("mp");
						view.clearGladiatorPanels();
						view.addGladiatorstoPanels(game.getCurrentGladiators(true));
						view.setSquirrels(game.getActiveSquirrels());
					} else
						view.addText("Not enough squirrels!");
				} else
					view.addText("Too many gladiators in team already.");
			}
			if (ae.getActionCommand().equals("PURCHASE")) {
				Gladiator gl = game.getCurrentGladiator();
				if (gl != null) {
					String n = shopPanel.getSelectedItem();
					game.purchaseItem(n, view);
				} else
					view.addText("You have no gladiator selected.");
			}
			if (ae.getActionCommand().equals("FIRE")) {
				Gladiator gl = game.getCurrentGladiator();
				if (gl != null) {
					whichaskaction = 1;
					view.setEnabled(false);
					askStuff = new Askstuff("Are thou sure thou want to fire " + gl.getName() + "?", this);
				} else
					view.addText("You have no gladiator selected.");
			}
			if (ae.getActionCommand().equals("CONFIRM")) {
				switch (whichaskaction) {
				case 1:
					view.addText(game.getCurrentGladiator().getName() + " has been fired. Thou will never see him/her/whatever again.");
					game.fireGladiator();
					break;
				case 2:
					game.setComputer(game.getActiveTeam());
					boolean battlestart = game.newBattle(battlePanel, view);
					if (battlestart) {
						view.changePanel("bp");
						view.disableStuff();
					} else {
						view.setSquirrels(game.getActiveSquirrels());
						view.setTeamName(game.getActiveTeamName());
					}
					break;
				}
				view.clearGladiatorPanels();
				view.addGladiatorstoPanels(game.getCurrentGladiators(true));
				view.showGladiator(game.getCurrentGladiator());
				view.setEnabled(true);
				askStuff.disposeThis();
			}
			if (ae.getActionCommand().equals("CANCEL")) {
				view.setEnabled(true);
				askStuff.disposeThis();
			}
		}
	}

	private boolean validateTeamNames(List<String> teamNames) {
		for (String name : teamNames) {
			if (game.teamNameExists(name)) {
				return false;
			}
		}
		return true;
	}

	public void mouseClicked(MouseEvent evt) {
		Object o = evt.getSource();
		if (o.equals(tavernPanel.getTable())) {
			String gladiator = tavernPanel.getSelectedGladiator();
			Gladiator gl = game.getGladiator(gladiator, "TAVERN");
			view.showGladiator(gl);
		} else {
			game.setCurrentGladiator(view.getLabelGladiator(o));
			if (game == null)
				view.addText("You have no gladiator selected.");
		}
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
	}

	public void mousePressed(MouseEvent evt) {
	}
}