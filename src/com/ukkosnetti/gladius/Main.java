package com.ukkosnetti.gladius;

import com.ukkosnetti.gladius.controller.Controller;
import com.ukkosnetti.gladius.gui.BattlePanel;
import com.ukkosnetti.gladius.gui.Loadscreen;
import com.ukkosnetti.gladius.gui.MainPanel;
import com.ukkosnetti.gladius.gui.SeasonPanel;
import com.ukkosnetti.gladius.gui.ShopPanel;
import com.ukkosnetti.gladius.gui.TavernPanel;
import com.ukkosnetti.gladius.gui.TeamPanel;
import com.ukkosnetti.gladius.gui.View;

public class Main {
	public Main(){
		Loadscreen ls = new Loadscreen();
        MainPanel mp = new MainPanel();
        BattlePanel bp = new BattlePanel();
        TavernPanel tp = new TavernPanel();
        ShopPanel sp = new ShopPanel();
        SeasonPanel sep = new SeasonPanel();
        TeamPanel tep = new TeamPanel();
        Game g = new Game();
        ls.closeLoadscreen();
		View v = new View(mp, tp, sp, bp, sep, tep);
		Controller c = new Controller(v, g, mp, tp, sp, bp, sep, tep);
		v.setController(c);
	}
	public static void main(String args[]){		
        new Main();
	}
}