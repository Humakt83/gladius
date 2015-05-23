package com.ukkosnetti.gladius.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.controller.Controller;
import com.ukkosnetti.gladius.gui.components.GradientPanel;
import com.ukkosnetti.gladius.gui.components.RedButton;

import java.awt.*;
import java.util.*;
public class TeamPanel extends GradientPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8207837241495714432L;
	private JButton ret;
	private JPanel table = new JPanel(){
		private static final long serialVersionUID = -5151415922021460L;
		{
			this.setBorder(new BevelBorder(BevelBorder.RAISED));
		}
		public void paintComponent(Graphics g){
	    	Graphics2D g2d = (Graphics2D) g;
	    	Dimension d = this.getSize();
	    	g2d.setPaint(new GradientPaint(0, 0, new Color(100,0,0), d.width, 0, new Color(255,0,0)));
	    	g2d.fillRect(0, 0, d.width, d.height);
	    }
	};
	private JLabel league1 = new JLabel("League 1");
	private JLabel league2 = new JLabel("League 2");
	private JLabel league3 = new JLabel("League 3");
	private JLabel league4 = new JLabel("League 4");
	private JPanel le1panel = new JPanel();
	private JPanel le2panel = new JPanel();
	private JPanel le3panel = new JPanel();
	private JPanel le4panel = new JPanel();
	private final Font fonN = new Font("High Tower Text", Font.PLAIN, 16);
	private final Font fonH = new Font("High Tower Text", Font.BOLD, 20);
	public TeamPanel(){
		init();
	}
	public void init(){
		league1.setFont(fonH);
		league2.setFont(fonH);
		league3.setFont(fonH);
		league4.setFont(fonH);
		table.setBackground(Color.RED);
		table.setLayout(new GridLayout(0,2));
		le1panel.setOpaque(false);
		le2panel.setOpaque(false);
		le3panel.setOpaque(false);
		le4panel.setOpaque(false);
		le1panel.setLayout(new GridLayout(0,2));
		le2panel.setLayout(new GridLayout(0,2));
		le3panel.setLayout(new GridLayout(0,2));
		le4panel.setLayout(new GridLayout(0,2));
		table.add(le1panel);
		table.add(le2panel);
		table.add(le3panel);
		table.add(le4panel);
		table.setPreferredSize(new Dimension(460,280));
		ret = new RedButton("LEAVE");
		ret.setBackground(Color.RED);
		ret.setForeground(Color.WHITE);
		this.setLayout(new FlowLayout());
		this.add(table);
		this.add(ret);
	}
	public void setText(Vector<Team> teams){
		le1panel.removeAll();
		le1panel.add(league1);
		le1panel.add(new JLabel());
		Iterator<Team> it = teams.iterator();
		Team apu1 = null, apu2 = null, apu3 = null, apu4 = null;
		while(it.hasNext()){
			Team apu = it.next();
			if(apu.getLeague()==1){
				if(apu1!=null){
					if(apu.getMatchWins()>apu1.getMatchWins()){
						if(apu2==null)apu2 = apu1;
						else if(apu3==null){
							apu3 = apu2;
							apu2 = apu1;
						}else{
							apu4 = apu3;
							apu3 = apu2;
							apu2 = apu1;
						}
						apu1 = apu;
					}else if(apu2!=null){
						if(apu.getMatchWins()>apu2.getMatchWins()){
							if(apu3==null){
								apu3 = apu2;
							}else{
								apu4 = apu3;
								apu3 = apu2;
							}
							apu2 = apu;
						}else if(apu3!=null){
							if(apu.getMatchWins()>apu3.getMatchWins()){
								apu4 = apu3;
								apu3 = apu;
							}else apu4 = apu;
						}else apu3 = apu;
					}else apu2 = apu;
				}else apu1 = apu;
			}
		}
		le1panel.add(new JLabel(apu1.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = 8163173328916388332L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le1panel.add(new JLabel(" "+apu1.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -5102829313519541011L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le1panel.add(new JLabel(apu2.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = 5398692031378367607L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le1panel.add(new JLabel(" "+apu2.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = 6203595328722843384L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le1panel.add(new JLabel(apu3.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -3179774603041110201L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le1panel.add(new JLabel(" "+apu3.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -3628016663517107367L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le1panel.add(new JLabel(apu4.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = 1010304654665010363L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le1panel.add(new JLabel(" "+apu4.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = 2322451167492796665L;

			{this.setForeground(Color.white); this.setFont(fonN);}});

		le2panel.removeAll();
		le2panel.add(league2);
		le2panel.add(new JLabel());
		Iterator<Team> it2 = teams.iterator();
		apu1 = null;
		apu2 = null; 
		apu3 = null; 
		apu4 = null;
		while(it2.hasNext()){
			Team apu = it2.next();
			if(apu.getLeague()==2){
				if(apu1!=null){
					if(apu.getMatchWins()>apu1.getMatchWins()){
						if(apu2==null)apu2 = apu1;
						else if(apu3==null){
							apu3 = apu2;
							apu2 = apu1;
						}else{
							apu4 = apu3;
							apu3 = apu2;
							apu2 = apu1;
						}
						apu1 = apu;
					}else if(apu2!=null){
						if(apu.getMatchWins()>apu2.getMatchWins()){
							if(apu3==null){
								apu3 = apu2;
							}else{
								apu4 = apu3;
								apu3 = apu2;
							}
							apu2 = apu;
						}else if(apu3!=null){
							if(apu.getMatchWins()>apu3.getMatchWins()){
								apu4 = apu3;
								apu3 = apu;
							}else apu4 = apu;
						}else apu3 = apu;
					}else apu2 = apu;
				}else apu1 = apu;
			}
		}
		le2panel.add(new JLabel(apu1.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -8281190949703739585L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le2panel.add(new JLabel(" "+apu1.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -2767453368148481496L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le2panel.add(new JLabel(apu2.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = 2602904580022028417L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le2panel.add(new JLabel(" "+apu2.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -2760962130109250085L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le2panel.add(new JLabel(apu3.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = 1739070647416208258L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le2panel.add(new JLabel(" "+apu3.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -4105059292729730340L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le2panel.add(new JLabel(apu4.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -5849047140987283404L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le2panel.add(new JLabel(" "+apu4.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -7802309352760347018L;

			{this.setForeground(Color.white); this.setFont(fonN);}});

		le3panel.removeAll();
		le3panel.add(league3);
		le3panel.add(new JLabel());
		Iterator<Team> it3 = teams.iterator();
		apu1 = null;
		apu2 = null; 
		apu3 = null; 
		apu4 = null;
		while(it3.hasNext()){
			Team apu = it3.next();
			if(apu.getLeague()==3){
				if(apu1!=null){
					if(apu.getMatchWins()>apu1.getMatchWins()){
						if(apu2==null)apu2 = apu1;
						else if(apu3==null){
							apu3 = apu2;
							apu2 = apu1;
						}else{
							apu4 = apu3;
							apu3 = apu2;
							apu2 = apu1;
						}
						apu1 = apu;
					}else if(apu2!=null){
						if(apu.getMatchWins()>apu2.getMatchWins()){
							if(apu3==null){
								apu3 = apu2;
							}else{
								apu4 = apu3;
								apu3 = apu2;
							}
							apu2 = apu;
						}else if(apu3!=null){
							if(apu.getMatchWins()>apu3.getMatchWins()){
								apu4 = apu3;
								apu3 = apu;
							}else apu4 = apu;
						}else apu3 = apu;
					}else apu2 = apu;
				}else apu1 = apu;
			}
		}
		le3panel.add(new JLabel(apu1.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -5583835955252101366L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le3panel.add(new JLabel(" "+apu1.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = 503120143223831120L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le3panel.add(new JLabel(apu2.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -4790099384730752213L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le3panel.add(new JLabel(" "+apu2.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -381189985586483155L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le3panel.add(new JLabel(apu3.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = 4293168006493611351L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le3panel.add(new JLabel(" "+apu3.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = 396004252984519985L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le3panel.add(new JLabel(apu4.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -1943989313657248197L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le3panel.add(new JLabel(" "+apu4.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -3553369306766709267L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		
		le4panel.removeAll();
		le4panel.add(league4);
		le4panel.add(new JLabel());
		Iterator<Team> it4 = teams.iterator();
		apu1 = null;
		apu2 = null; 
		apu3 = null; 
		apu4 = null;
		while(it4.hasNext()){
			Team apu = it4.next();
			if(apu.getLeague()==4){
				if(apu1!=null){
					if(apu.getMatchWins()>apu1.getMatchWins()){
						if(apu2==null)apu2 = apu1;
						else if(apu3==null){
							apu3 = apu2;
							apu2 = apu1;
						}else{
							apu4 = apu3;
							apu3 = apu2;
							apu2 = apu1;
						}
						apu1 = apu;
					}else if(apu2!=null){
						if(apu.getMatchWins()>apu2.getMatchWins()){
							if(apu3==null){
								apu3 = apu2;
							}else{
								apu4 = apu3;
								apu3 = apu2;
							}
							apu2 = apu;
						}else if(apu3!=null){
							if(apu.getMatchWins()>apu3.getMatchWins()){
								apu4 = apu3;
								apu3 = apu;
							}else apu4 = apu;
						}else apu3 = apu;
					}else apu2 = apu;
				}else apu1 = apu;
			}
		}
		le4panel.add(new JLabel(apu1.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = 4303993603489711440L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le4panel.add(new JLabel(" "+apu1.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -5646985968749503734L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le4panel.add(new JLabel(apu2.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -6437314447640375783L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le4panel.add(new JLabel(" "+apu2.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -5651288986921942949L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le4panel.add(new JLabel(apu3.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -3362191321433171692L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le4panel.add(new JLabel(" "+apu3.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = 6809073226955950432L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le4panel.add(new JLabel(apu4.getName()){/**
		 * 
		 */
			private static final long serialVersionUID = -6711822949330062606L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
		le4panel.add(new JLabel(" "+apu4.getMatchWins()){/**
		 * 
		 */
			private static final long serialVersionUID = -2163270534409173368L;

			{this.setForeground(Color.white); this.setFont(fonN);}});
	}
	public void setController(Controller c){
		ret.setActionCommand("TAVERN_LEAVE");
		ret.addActionListener(c);		
	}
}

