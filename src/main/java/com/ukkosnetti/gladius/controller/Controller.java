package com.ukkosnetti.gladius.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	private final Map<String, Runnable> commandMap;

	public Controller(View v, Game g, MainPanel m, TavernPanel t, ShopPanel s, BattlePanel b, SeasonPanel sep, TeamPanel tep) {
		this.game = g;
		this.view = v;
		this.seasonPanel = sep;
		tavernPanel = t;
		shopPanel = s;
		battlePanel = b;
		this.teamPanel = tep;
		commandMap = initializeCommandMap();
	}

	private Map<String, Runnable> initializeCommandMap() {
		Map<String, Runnable> commandMap = new HashMap<>();
		commandMap.put("EXIT", () -> {
			System.exit(0);
		});
		commandMap.put("NEW", () -> {
			displayNewGameView();
		});
		commandMap.put("HELP", () -> {
			displayHelpPDF();
		});
		commandMap.put("ABOUT", () -> {
			displayAbout();
		});
		commandMap.put("START_NEW_GAME", () -> {
			startNewGame();
		});
		commandMap.put("LOAD", () -> {
			displayLoadGameDialog();
		});
		commandMap.put("LOADTHISGAME", () -> {
			loadGame();
		});
		commandMap.put("SAVE", () -> {
			displaySaveGameDialog();
		});
		commandMap.put("SAVETHISGAME", () -> {
			saveGame();
		});
		commandMap.put("RESIGN", () -> {
			resign();
		});
		commandMap.put("STARTBATTLE", () -> {
			startBattle();
		});
		commandMap.put("TAVERN", () -> {
			displayTavern();
		});
		commandMap.put("BLACKSMITH", () -> {
			displayBlacksmith();;
		});
		commandMap.put("KO", () -> {
			displayTopKOs();
		});
		commandMap.put("TOP_TEAMS", () -> {
			displayTopTeams();
		});
		commandMap.put("SEASON", () -> {
			displaySeason();
		});
		commandMap.put("OTHERTEAMS", () -> {
			displayTeams();
		});
		commandMap.put("TEAM_NAME", () -> {
			displayChangeTeamNameDialog();
		});
		commandMap.put("GLADIATOR_NAME", () -> {
			displayChangeGladiatorNameDialog();
		});
		commandMap.put("CHANGE_GLADIATOR_NAME", () -> {
			changeGladiatorName();
		});
		commandMap.put("CHANGE_TEAM_NAME", () -> {
			changeTeamName();
		});
		commandMap.put("TAVERN_LEAVE", () -> {
			displayMainPanel();
		});
		commandMap.put("TAVERN_HIRE", () -> {
			hireGladiator();
		});
		commandMap.put("PURCHASE", () -> {
			purchaseItem();
		});
		commandMap.put("FIRE", () -> {
			fireGladiator();
		});
		commandMap.put("CONFIRM", () -> {
			confirm();
		});
		commandMap.put("CANCEL", () -> {
			cancel();
		});
		return commandMap;
	}

	public void actionPerformed(ActionEvent ae) {
		Runnable action = commandMap.get(ae.getActionCommand());
		if (action != null) {
			action.run();
		}
	}

	private void cancel() {
		view.setEnabled(true);
		askStuff.disposeThis();
	}

	private void confirm() {
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
		cancel();
	}

	private void fireGladiator() {
		Gladiator gl = game.getCurrentGladiator();
		if (gl != null) {
			whichaskaction = 1;
			view.setEnabled(false);
			askStuff = new Askstuff("Are thou sure thou want to fire " + gl.getName() + "?", this);
		} else
			view.addText("You have no gladiator selected.");
	}

	private void purchaseItem() {
		Gladiator gl = game.getCurrentGladiator();
		if (gl != null) {
			String n = shopPanel.getSelectedItem();
			game.purchaseItem(n, view);
		} else
			view.addText("You have no gladiator selected.");
	}

	private void hireGladiator() {
		if (game.canHire()) {
			String n = tavernPanel.getSelectedGladiator();
			boolean success = game.hireGladiator(n);
			if (success) {
				view.addText("You hired a brand old gladiator.");
				displayMainPanel();
				view.clearGladiatorPanels();
				view.addGladiatorstoPanels(game.getCurrentGladiators(true));
				view.setSquirrels(game.getActiveSquirrels());
			} else
				view.addText("Not enough squirrels!");
		} else
			view.addText("Too many gladiators in team already.");
	}

	private void displayMainPanel() {
		view.changePanel("mp");
	}

	private void changeTeamName() {
		String n = view.getName();
		if (!n.equals("") && !n.isEmpty()) {
			if (game.changeTeamName(n)) {
				view.setTeamName(n);
			} else {
				view.addText("There is already a team with that name.");
			}
		}
	}

	private void changeGladiatorName() {
		String n = view.getName();
		if (!n.equals("") && !n.isEmpty()) {
			game.getCurrentGladiator().setName(n);
			view.showGladiator(game.getCurrentGladiator());
		}
	}

	private void displayChangeGladiatorNameDialog() {
		if (!(game.getCurrentGladiators(false) == null))
			view.teamorgladiatorNameOpen(false, game.getCurrentGladiator().getName());
		else
			view.addText("No gladiator selected.");
	}

	private void displayChangeTeamNameDialog() {
		view.teamorgladiatorNameOpen(true, game.getActiveTeamName());
	}

	private void displayTeams() {
		teamPanel.setText(game.getTeams());
		view.changePanel("tep");
	}

	private void displaySeason() {
		seasonPanel.setText(game.getActiveTeam());
		view.changePanel("sep");
	}

	private void displayTopTeams() {
		if (topteam != null)
			topteam.dispose();
		topteam = new TopTeams(game.getTeams());
	}

	private void displayTopKOs() {
		if (topKOs != null)
			topKOs.dispose();
		topKOs = new TopKOs(game.getTeams());
	}

	private void displayBlacksmith() {
		if (!itemsadded) {
			view.addText("Greetings noble customer! I have a wide range of different armaments, exquisitive armor and exotic spells available for extraordinary prices. \nFeel free to browse my vast selection of goods and peep if anything is of interest.");
			shopPanel.addShopItems(game.getMeleeBlacksmith(), game.getRangedBlacksmith(), game.getArmorBlacksmith(), game.getSpells(true), game.getSpells(false));
			itemsadded = true;
		} else
			view.addText("Thou has entered a local shop. \nVariety of different armors and weapons, that are covered with cobwebs, are stockpiled here.");
		view.changePanel("sp");
	}

	private void displayTavern() {
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
			race[i] = apu.getRace().toString();
			price[i] = 10 * apu.getUpkeep();
			i++;
		}
		tavernPanel.setGladiators(name, race, price);
		tavernPanel.setRow();
		view.changePanel("tp");
	}

	private void startBattle() {
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

	private void resign() {
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

	private void saveGame() {
		game.saveGame(saveGame.getSavedName());
		saveGame.dispose();
		displayMainPanel();
	}

	private void displaySaveGameDialog() {
		if (saveGame == null) {
			saveGame = new SaveGame(this);
		} else {
			saveGame.dispose();
			saveGame = new SaveGame(this);
		}
	}

	private void loadGame() {
		displayMainPanel();
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

	private void displayLoadGameDialog() {
		if (loadGame != null)
			loadGame.dispose();
		ArrayList<File> files = game.getSavedGames();
		if (!files.isEmpty()) {
			loadGame = new LoadGame(game.getSavedGames(), this);
		} else {
			view.addText("No saved games exist.");
		}
	}

	private void startNewGame() {
		if (gamestarted)
			game = new Game();
		List<String> teamNames = newGameView.getTeamNames();
		if (validateTeamNames(teamNames)) {
			view.setTeamName(teamNames.get(0));
			game.setTeams(teamNames);
			gamestarted = true;
			newGameView.disposeThis();
			displayMainPanel();
			view.clearGladiatorPanels();
			view.setTeamName(game.getActiveTeamName());
			view.setSquirrels(game.getActiveSquirrels());
			view.enableStuff();
			view.setCurSeason(game.getCurrentSeason());
			view.addText("O noble player, thy first task in this game was a magnificent success!\nNow recruit gladiators, purchase equipments and spells and enter arena!");
		} else
			view.addText("Invalid names!");
	}

	private void displayAbout() {
		if (about != null)
			about.dispose();
		about = new About();
	}

	private void displayHelpPDF() {
		try {
			String command = "rundll32 url.dll,FileProtocolHandler " + "USER_MANUAL.pdf";
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			view.addText("Could not open pdf file");
		}
	}

	private void displayNewGameView() {
		if (newGameView != null)
			newGameView.dispose();
		newGameView = new NewGameView(this);
	}

	private boolean validateTeamNames(List<String> teamNames) {
		if (teamNames.isEmpty())
			return false;
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