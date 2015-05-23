package com.ukkosnetti.gladius.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gui.components.GradientPanel;
/*
 * JFrame class for showing ten top teams, which are ordered by champions and wins.
 */
public class TopTeams extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2693286140825102283L;
	private ImageIcon gladius = new ImageIcon("Gladius.png");
	private JPanel container = new GradientPanel();
	private JTable toptable; //Customized JTable is used to show data in this JFrame.
	public TopTeams(Vector<Team> t){
		String[][] top10 = new String[10][3];
		//Resetting values of 2D String array.
		for(int i = 0; i<10; i++){
			for(int j = 0; j<3; j++){
				top10[i][j] = ""+0;
			}
		}
		//Going through Team Vector using iterator.
        Iterator<Team> it = t.iterator();
        while(it.hasNext()){
            Team apu = it.next();
            //First if clause is checking if the Team has a place in Top10 thus we compare it to last entry in array.
            if(apu.getChampionWins()>Integer.parseInt(top10[9][2])||(apu.getChampionWins()==Integer.parseInt(top10[9][2])&&(apu.getMatchWinsAllTime()>Integer.parseInt(top10[9][1])))||Integer.parseInt(top10[9][2]) == 0){
            	//Going the array through using loop in order to place the team entry into proper place while moving others.
            	for(int i = 0; i<10; i++){
            		if(apu.getChampionWins()>Integer.parseInt(top10[i][2])||(Integer.parseInt(top10[i][2]) == 0&&apu.getMatchWinsAllTime()>=Integer.parseInt(top10[i][1]))
            				||(apu.getChampionWins()==Integer.parseInt(top10[i][2])&&(apu.getMatchWinsAllTime()>Integer.parseInt(top10[i][1])))){
            			String tn = apu.getName();
            			String wi = ""+apu.getMatchWinsAllTime();
            			String cw = ""+apu.getChampionWins();
            			for(; i<10; i++){
            				//Storing up old values into variables.
            				String tn2 = top10[i][0];
            				String wi2 = top10[i][1];
            				String cw2 = top10[i][2];
            				top10[i][0] = tn;
            				top10[i][1] = wi;
            				top10[i][2] = cw;
            				tn = tn2;
            				wi = wi2;
            				cw = cw2;
            			}
            		}
	            }
            }
        }
        
        String[] columnnames = {"Team", "Wins", "Champions"};
		//Creating table and customizing it:
        toptable = new JTable(top10, columnnames);
		toptable.setShowGrid(true);
		toptable.setRowSelectionAllowed(false);
		toptable.setColumnSelectionAllowed(false);
		toptable.setEnabled(false);
		toptable.setBackground(Color.RED);
		toptable.setForeground(Color.WHITE);
		toptable.getColumnModel().getColumn(0).setPreferredWidth(150);
		toptable.getColumnModel().getColumn(1).setPreferredWidth(70);
		toptable.getColumnModel().getColumn(2).setPreferredWidth(100);
		JTableHeader hd = toptable.getTableHeader();
		hd.setEnabled(false);
		hd.setBackground(Color.YELLOW);
		hd.setForeground(Color.BLACK);
		hd.setReorderingAllowed(false);
		hd.setResizingAllowed(false);        
		// Setting component properties
		container.setOpaque(true);
		// Customizing JTextArea.
		toptable.setFont(new Font("High Tower Text", 1, 16));
		hd.setFont(new Font("High Tower Text", 1, 16));
		container.setBackground(Color.BLUE);
		// Adding components to panels.
		container.add(hd);
		container.add(toptable);
		add(container);
		// Customizing frame.
		setTitle("Top Teams");
		setSize(400,225);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        //center frame to screen
        setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
		//pack();
        this.setIconImage(gladius.getImage());
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
