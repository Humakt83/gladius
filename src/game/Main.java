package game;

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