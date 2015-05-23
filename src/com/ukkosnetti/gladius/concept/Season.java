package com.ukkosnetti.gladius.concept;
import java.io.Serializable;
import java.util.*;

import com.ukkosnetti.gladius.Battle;
import com.ukkosnetti.gladius.Game;
import com.ukkosnetti.gladius.gui.BattlePanel;
import com.ukkosnetti.gladius.gui.View;
public class Season implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6167246742483603869L;
	private Team[][][] matchTable; //8 matches 2 teams 3 rounds[8][2][3]
    private List<Team> teams;
    private Game game;
	private int currentmatch = 0;
    private int currentround = 0;
    public Season(Vector<Team> teams, Game g){
        game = g;
		this.teams = teams;
        matchTable = new Team[8][2][3];
        for(int i = 0; i<3; i++){
            int formerk = 0;
            List<String> names = new ArrayList<String>();
            for(int j = 0; j<8; j++){
                for(int k = formerk; k<teams.size(); k++){
                    if(!(teams.elementAt(k).getMatchesAlready()>=3)&&!(names.contains(teams.elementAt(k).getName()))){
                        matchTable[j][0][i] = teams.elementAt(k);
                        teams.elementAt(k).increaseMatchesAlready();
                        int league = teams.elementAt(k).getLeague();
                        String name1 = teams.elementAt(k).getOpponent1();
                        String name2 = teams.elementAt(k).getOpponent2();
                        names.add(teams.elementAt(k).getName());
                        formerk = k;
                        for(k = k+1; k<teams.size(); k++){
                            if(teams.elementAt(k).getLeague()==league 
                            		&&!(teams.elementAt(k).getMatchesAlready()>=3)&&!(teams.elementAt(k).getName().equals(name1)
                            				||teams.elementAt(k).getName().equals(name2))&&!(names.contains(teams.elementAt(k).getName()))){
                                matchTable[j][1][i] = teams.elementAt(k);
                                names.add(teams.elementAt(k).getName());
                                teams.elementAt(k).increaseMatchesAlready();
                                if(teams.elementAt(k).getOpponent1().equals(""))teams.elementAt(k).setOpponent1(teams.elementAt(formerk).getName());
                                else if(teams.elementAt(k).getOpponent2().equals(""))teams.elementAt(k).setOpponent2(teams.elementAt(formerk).getName());
                                    else teams.elementAt(k).setOpponent3(teams.elementAt(formerk).getName());
                                if(teams.elementAt(formerk).getOpponent1().equals(""))teams.elementAt(formerk).setOpponent1(teams.elementAt(k).getName());
                                else if(teams.elementAt(formerk).getOpponent2().equals(""))teams.elementAt(formerk).setOpponent2(teams.elementAt(k).getName());
                                    else teams.elementAt(formerk).setOpponent3(teams.elementAt(k).getName());
                                k = teams.size();
                            }
                        }
                        formerk++;
                    }
                }
            }
        }
	}
    public Team getTeamAForCurrentBattle(){
        return matchTable[currentmatch][0][currentround];
    }
    public Team getTeamBForCurrentBattle(){
        return matchTable[currentmatch][1][currentround];
    }
    public void nextBattle(BattlePanel bp, View v){
        if(currentmatch>=7){
            currentmatch = 0;
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
            if(currentround>=2){
                v.addText("Current season is over.");
                game.newSeason(v);
            }else currentround++;
        }else{
            currentmatch++;
            new Battle(this.getTeamAForCurrentBattle(), this.getTeamBForCurrentBattle(), this, bp, v);
        }
    }
    public void clearTeamBattlesInfo(){
        Iterator<Team> it = teams.iterator();
        while(it.hasNext())it.next().resetMatchWins();
    }
    public Vector<Team> getSeasonSide1(){
    	Vector<Team> s = new Vector<Team>();
    	for(int j = 0; j<8; j++){
    		for(int i = 0; i<8;i++){
    			s.add(matchTable[i][0][j]);
    		}	
    	}
    	return s;
    }
    public Vector<Team> getSeasonSide2(){
    	Vector<Team> s = new Vector<Team>();
    	for(int j = 0; j<8; j++){
    		for(int i = 0; i<8;i++){
    			s.add(matchTable[i][1][j]);
    		}	
    	}
    	return s;
    }
}
