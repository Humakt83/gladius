package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;
/*
 * Controller acts as an action and mouse listener for various GUI components especially View.
 */
public class Controller implements ActionListener, MouseListener{
	private Game g;
	private View v;
	private LoadGame lg;
	private SaveGame sg;
    private NewGameView ngv;
    private SeasonPanel sep;
    private TavernPanel tp;
    private ShopPanel sp;
    private BattlePanel bp;
    private TeamPanel tep;
    private About ab;
    private TopKOs top;
    private TopTeams topteam;
    private Askstuff ask;
    private boolean gamestarted = false;
    private boolean itemsadded = false;
    private int whichaskaction = 0;
	public Controller(View v, Game g, MainPanel m, TavernPanel t, ShopPanel s, BattlePanel b, SeasonPanel sep, TeamPanel tep){
		this.g = g;
		this.v= v;
        this.sep = sep;
        tp = t;
        sp = s;
        bp = b;
        this.tep = tep;
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("EXIT")){
			System.exit(0);
		}
		if(ae.getActionCommand().equals("NEW")){
            if(ngv != null) ngv.dispose();
            ngv = new NewGameView(this);
		}
		if(ae.getActionCommand().equals("HELP")){
			try                                      //try statement
	        {
				String command = "rundll32 url.dll,FileProtocolHandler " + "USER_MANUAL.pdf";
				Runtime.getRuntime().exec(command);

	        } catch (Exception e)                    //catch any exceptions here
	          {
	              v.addText("Error" + e );  //print the error
	          }
		}
		if(ae.getActionCommand().equals("ABOUT")){
			if(ab!=null)ab.dispose();
			ab=new About();
		}
        if(ae.getActionCommand().equals("START_NEW_GAME")){
            if(gamestarted)g = new Game();
            String t[] = ngv.getTeamNames();
            boolean check = false;
            if(t[0] != null && !t[0].equals("")&&g.checkExistingTeamNames(t[0])){
                v.setTeamName(t[0]);
                check = true;
            }else if(t[1] != null&& !t[1].equals("")&&g.checkExistingTeamNames(t[1])){
                    v.setTeamName(t[1]);
                    check = true;
            }else if(t[2] != null&& !t[2].equals("")&&g.checkExistingTeamNames(t[2])){
                    v.setTeamName(t[2]);
                    check = true;
            }else if(t[3] != null&& !t[3].equals("")&&g.checkExistingTeamNames(t[3])){
                    v.setTeamName(t[3]);
                    check = true;
            }
            if(check){
                g.setTeams(t);
                gamestarted = true;
                ngv.disposeThis();
                v.clearGladiatorPanels();
                v.setTeamName(g.getActiveTeamName());
                v.setSquirrels(g.getActiveSquirrels());
                v.changePanel("mp");
                v.enableStuff();
                v.setCurSeason(g.getCurrentSeason());
                v.addText("O noble player, thy first task in this game was a magnificent success!\nNow recruit gladiators, purchase equipments and spells and enter arena!");
            }else v.addText("No valid names!");
        }
		if(ae.getActionCommand().equals("LOAD")){
			if(lg==null);
			else lg.dispose();
			ArrayList<File> files = g.getSavedGames();
			if(!files.isEmpty()){
				lg = new LoadGame(g.getSavedGames(),this);
			}else{
				v.addText("No saved games exist.");
			}
		}
		if(ae.getActionCommand().equals("LOADTHISGAME")){
			v.changePanel("mp");
			Game gah = g.loadGame(lg.getSelectedFile());
			if(gah!=null){
				g = gah;
			}
			v.clearGladiatorPanels();
			v.addGladiatorstoPanels(g.getCurrentGladiators(true));
			v.showGladiator(g.getCurrentGladiator());
			v.setTeamName(g.getActiveTeamName());
            v.setSquirrels(g.getActiveSquirrels());
            v.setCurSeason(g.getCurrentSeason());
            v.enableStuff();
            gamestarted = true;
            lg.dispose();
		}
		if(gamestarted){
			if(ae.getActionCommand().equals("SAVE")){
				if(sg==null){
					sg = new SaveGame(this);
				}else{
					sg.dispose();
					sg = new SaveGame(this);
				}
			}
			if(ae.getActionCommand().equals("SAVETHISGAME")){
				g.saveGame(sg.getSavedName());
				sg.dispose();
				v.changePanel("mp");
			}
			if(ae.getActionCommand().equals("RESIGN")){
				if(g.getHumanPlayers()> 1){
					if(!(g.getCurrentGladiators(true)==null)){
						whichaskaction = 2;
	            		v.setEnabled(false);
	            		ask = new Askstuff("Are thou sure thou want to resign from glamorous manager position?", this);
		            }else v.addText("Thou must have at least one active gladiator in team before resigning.");
				}else v.addText("Position of manager can only be resigned when more than one h�b�tti is managing the teams (in other words, there must be more than one player in game).");
			}
            if(ae.getActionCommand().equals("STARTBATTLE")){               
                if(!(g.getCurrentGladiators(true)==null)){
                    boolean battlestart = g.newBattle(bp, v);

                    if(battlestart){
                        v.changePanel("bp");
                        v.disableStuff();
                    }else{
                        v.clearGladiatorPanels();
                        v.setSquirrels(g.getActiveSquirrels());
                        v.setTeamName(g.getActiveTeamName());
                        v.addGladiatorstoPanels(g.getCurrentGladiators(true));
                        v.showGladiator(g.getCurrentGladiator());
                    }
                }else v.addText("Manager refuses to enter arena. Alas! Thou must hire gladiator.");
            }
            if(ae.getActionCommand().equals("TAVERN")){
            	v.addText("Thou has entered a local tavern. In the dark corners you can see variety of creeps howling for a chance to be gladiator.");
                Vector<Gladiator> vec = g.getTavernGladiators();
                String name[] = new String[vec.size()];
                String race[] = new String[vec.size()];
                int price[] = new int[vec.size()];
                Iterator<Gladiator> it = vec.iterator();
                int i = 0;
                while(it.hasNext()){
                    Gladiator apu = it.next();
                    name[i] = apu.getName();
                    race[i] = apu.getRace();
                    price[i] = 10*apu.getUpkeep();
                    i++;
                }
                tp.setGladiators(name, race, price);
                tp.setRow();
                v.changePanel("tp");
            }
            if(ae.getActionCommand().equals("BLACKSMITH")){
                if(!itemsadded){
                	v.addText("Greetings noble customer! I have a wide range of different armaments, exquisitive armor and exotic spells available for extraordinary prices. \nFeel free to browse my vast selection of goods and peep if anything is of interest.");
                    sp.addShopItems(g.getMeleeBlacksmith(), g.getRangedBlacksmith(), g.getArmorBlacksmith(), g.getSpells(true), g.getSpells(false));
                    itemsadded = true;
                }else v.addText("Thou has entered a local shop. \nVariety of different armors and weapons, that are covered with cobwebs, are stockpiled here.");
                v.changePanel("sp");
            }      
            if(ae.getActionCommand().equals("KO")){
            	if(top!=null)top.dispose();
    			top=new TopKOs(g.getTeams());
            }
            if(ae.getActionCommand().equals("TOP_TEAMS")){
            	if(topteam!=null)topteam.dispose();
            	topteam = new TopTeams(g.getTeams());
            }
            if(ae.getActionCommand().equals("SEASON")){
            	sep.setText(g.getActiveTeam());
            	v.changePanel("sep");
            }
            if(ae.getActionCommand().equals("OTHERTEAMS")){
            	tep.setText(g.getTeams());
            	v.changePanel("tep");
            }
            if(ae.getActionCommand().equals("TEAM_NAME")){
                    v.teamorgladiatorNameOpen(true, g.getActiveTeamName());
            }
            if(ae.getActionCommand().equals("GLADIATOR_NAME")){
                if(!(g.getCurrentGladiators(false)==null))
                    v.teamorgladiatorNameOpen(false, g.getCurrentGladiator().getName());
                else v.addText("No gladiator selected.");
            }
            if(ae.getActionCommand().equals("CHANGE_GLADIATOR_NAME")){
                String n = v.getName();
                if(!n.equals("")&&!n.isEmpty()){
                    g.getCurrentGladiator().setName(n);
                    v.showGladiator(g.getCurrentGladiator());
                }
            }
            if(ae.getActionCommand().equals("CHANGE_TEAM_NAME")){
                String n = v.getName();
                if(!n.equals("")&&!n.isEmpty()){
                    if(g.changeTeamName(n)){
                    	v.setTeamName(n);
                    }
                    else{
                    	v.addText("There is already a team with that name.");
                    }
                }
            }
            if(ae.getActionCommand().equals("TAVERN_LEAVE")){
                v.changePanel("mp");
            }
            if(ae.getActionCommand().equals("TAVERN_HIRE")){
                if(g.canHire()){
                    String n = tp.getSelectedGladiator();
                    boolean success = g.hireGladiator(n);
                    if(success){
                        v.addText("You hired a brand old gladiator.");
                        v.changePanel("mp");
                        v.clearGladiatorPanels();
                        v.addGladiatorstoPanels(g.getCurrentGladiators(true));
                        v.setSquirrels(g.getActiveSquirrels());
                    }
                    else v.addText("Not enough squirrels!");
                }else v.addText("Too many gladiators in team already.");
            }
            if(ae.getActionCommand().equals("PURCHASE")){
                Gladiator gl = g.getCurrentGladiator();                
                if(gl!=null){
                    String n = sp.getSelectedItem();
                    g.purchaseItem(n, v);                    
                }else v.addText("You have no gladiator selected.");
            }
            if(ae.getActionCommand().equals("FIRE")){
            	Gladiator gl = g.getCurrentGladiator();
            	if(gl!=null){
            		whichaskaction = 1;
            		v.setEnabled(false);
            		ask = new Askstuff("Are thou sure thou want to fire " + gl.getName()+"?", this);
                }else v.addText("You have no gladiator selected.");
            }
            if(ae.getActionCommand().equals("CONFIRM")){
            	switch(whichaskaction){
            	case 1:
            		v.addText(g.getCurrentGladiator().getName() + " has been fired. Thou will never see him/her/whatever again.");
                    g.fireGladiator();
                    break;
            	case 2:
            		g.setComputer(g.getActiveTeam());
	                boolean battlestart = g.newBattle(bp, v);
	                if(battlestart){
	                    v.changePanel("bp");
	                    v.disableStuff();
	                }else{
	                    v.setSquirrels(g.getActiveSquirrels());
	                    v.setTeamName(g.getActiveTeamName());
	                }
	                break;
            	}
            	v.clearGladiatorPanels();
                v.addGladiatorstoPanels(g.getCurrentGladiators(true));
                v.showGladiator(g.getCurrentGladiator());
                v.setEnabled(true);
            	ask.disposeThis();
            }
            if(ae.getActionCommand().equals("CANCEL")){
            	v.setEnabled(true);
            	ask.disposeThis();
            }
        }
	}
    public void mouseClicked(MouseEvent evt){
        Object o = evt.getSource();
        if(o.equals(tp.getTable())){
            String gladiator = tp.getSelectedGladiator();
            Gladiator gl = g.getGladiator(gladiator, "TAVERN");
            v.showGladiator(gl);
        }else{
            g.setCurrentGladiator(v.getLabelGladiator(o));
            if(g==null)v.addText("You have no gladiator selected.");
        }
    }
    public void mouseExited(MouseEvent evt){}
    public void mouseEntered(MouseEvent evt){}
    public void mouseReleased(MouseEvent evt){}
    public void mousePressed(MouseEvent evt){}
}