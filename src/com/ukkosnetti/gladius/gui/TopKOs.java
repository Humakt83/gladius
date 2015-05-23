package com.ukkosnetti.gladius.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.components.GradientPanel;
public class TopKOs extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3891142126001331729L;
	private JPanel container = new GradientPanel();
	private JTable toptable;
	private ImageIcon gladius = new ImageIcon("Gladius.png");
	public TopKOs(Vector<Team> t){
		String[][] top10 = new String[10][3];
		for(int i = 0; i<10; i++){
			for(int j = 0; j<3; j++){
				top10[i][j] = ""+0;
			}
		}
        Iterator<Team> it = t.iterator();
        while(it.hasNext()){
            Team apu = it.next();
            Vector <Gladiator> glads = apu.getGladiators();
            if(glads!=null){
            	Iterator<Gladiator> it2 = glads.iterator();
	            while(it2.hasNext()){
	                Gladiator gl = it2.next();
	                if(gl.getKnockdowns()>Integer.parseInt(top10[9][2])||Integer.parseInt(top10[9][2]) == 0){
	                	for(int i = 0; i<10; i++){
	                		if(gl.getKnockdowns()>Integer.parseInt(top10[i][2])||Integer.parseInt(top10[i][2]) == 0){
	                			String tn = apu.getName();
	                			String gn = gl.getName();
	                			String ko = ""+gl.getKnockdowns();
	                			for(; i<10; i++){
	                				String tn2 = top10[i][0];
	                				String gn2 = top10[i][1];
	                				String ko2 = top10[i][2];
	                				top10[i][0] = tn;
	                				top10[i][1] = gn;
	                				top10[i][2] = ko;
	                				tn = tn2;
	                				gn = gn2;
	                				ko = ko2;
	                			}
	                		}
	                	}
	                }
	            }
            }
        }
        
        String[] columnnames = {"Team", "Gladiator", "KO"};
		//Creating table and customizing it:
        toptable = new JTable(top10, columnnames);;
		toptable.setShowGrid(true);
		toptable.setRowSelectionAllowed(false);
		toptable.setColumnSelectionAllowed(false);
		toptable.setEnabled(false);
		toptable.setBackground(Color.RED);
		toptable.setForeground(Color.WHITE);
		toptable.getColumnModel().getColumn(0).setPreferredWidth(150);
		toptable.getColumnModel().getColumn(1).setPreferredWidth(150);
		toptable.getColumnModel().getColumn(2).setPreferredWidth(50);
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
		setTitle("Top Knockouts");
		this.setSize(400,225);
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
