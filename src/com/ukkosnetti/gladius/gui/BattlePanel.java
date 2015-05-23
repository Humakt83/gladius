/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BattlePanel.java
 *
 * Created on 3.6.2009, 12:41:13
 */

package com.ukkosnetti.gladius.gui;
import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.ukkosnetti.gladius.Battle;
import com.ukkosnetti.gladius.gui.components.GradientPanel;
/**
 *
 * @author Humakt83
 */
public class BattlePanel extends GradientPanel implements Runnable{
	private static final long serialVersionUID = 4822004098192621280L;
	private Thread th;
	private boolean running = false;
    /** Creates new form BattlePanel */
	private boolean missOrSplat = false; //Used for showing either miss or hit image.
	private int hitx = 0, hity = 0; //Where on the battletable (Battle) e was hit made.
	private boolean showhit = false;
    private ImageIcon splatter = new ImageIcon("res/Splatter.gif");
    private ImageIcon miss = new ImageIcon("res/Miss.gif");
    private ImageIcon ko = new ImageIcon("res/KOSplatter.gif");
    private ImageIcon tree = new ImageIcon("res/Tree.gif");
    private ImageIcon rock = new ImageIcon("res/Rock.gif");
    private Random ran = new Random();
    private boolean battlestarted = false;
    private Battle battle;
    private GradientPaint battlepaint = new GradientPaint(0,0, new Color(0,255,0), 40,40, new Color(0,100,0),true);
    private GradientPaint movepaint = new GradientPaint(0,0, new Color(150,150,255),40,40, new Color(255,255,255), true);
    private int[][] treetable;
    private int[][] rocktable;
    public BattlePanel() {
        initComponents();
        this.setDoubleBuffered(true);
    }
    public void setController(Battle b){
    	battle = null;
        battle = b;
        this.addMouseListener(battle);
        running = true;
    	if(th!=null)th = null;
    	th = new Thread(this);
        th.start();
    }
    public void paintBattle(){
        battlestarted = true;
        repaint();
    }
    public void setSceneryItems(){
    	treetable = new int[5][2];
    	rocktable = new int[5][2];
    	for(int i = 0; i<5; i++){
    		treetable[i][0] = ran.nextInt(10);
    		treetable[i][1] = ran.nextInt(8);
    		rocktable[i][0] = ran.nextInt(10);
    		rocktable[i][1] = ran.nextInt(8);
    	}
    }
   
    //Thread which purpose is to make miss or hit image to disappear after 400 milliseconds.
    public void run(){
    	while(running){
    		this.repaint();
    		try{
    			Thread.sleep(400);
            }
            catch(InterruptedException e){}
    		showhit = false;
    	}
    }
    /*
     * Function for stopping the thread.
     */
    public void stop(){
    	running = false;
    	th = null;
    	battlestarted = false;
    	this.removeMouseListener(battle);
    	this.removeKeyListener(battle);
    	battle = null;
    }
    /*
     * Function for showing hit or miss.
     */
    public void hitmiss(int x, int y, boolean miss){
    	missOrSplat = miss;
    	hitx = x;
    	hity = y;
    	showhit = true;
    	this.repaint();
    }
    public void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
		//Paints battle squares green with darker borders.
    	for(int x = 0; x<10; x++){
            for(int y = 0; y<8; y++){
                g2d.setPaint(battlepaint);
                g2d.fillRect(x*40, y*40, 40, 40);
                g.setColor(new Color(0, 50,0));
                g.drawRect(x*40, y*40, 40, 40);
            }
        }
        if(battlestarted){
        	for(int i = 0; i<5; i++)g.drawImage(tree.getImage(), treetable[i][0]*40, treetable[i][1]*40, null);
        	for(int i = 0; i<5; i++)g.drawImage(rock.getImage(), rocktable[i][0]*40, rocktable[i][1]*40, null);
            for(int x = 0; x<10; x++){
                for(int y = 0; y<8; y++){
                    if(battle.getMoveTableValue(x, y)==20){
                        g2d.setPaint(movepaint);
                        g2d.fillRect(x*40, y*40, 40, 40);
                        g.setColor(Color.BLACK);
                        g.drawRect(x*40, y*40, 40, 40);
                    }
                    if(battle.getBattleTableValue(x, y)>0&&battle.getBattleTableValue(x, y)<10){
                        g.drawImage(battle.team1.getGladiators().elementAt(battle.getBattleTableValue(x, y)-1).getImage().getImage(), x*40, y*40, null);
                        if(battle.team1.getGladiators().elementAt(battle.getBattleTableValue(x, y)-1).getHealth()<=0){
                            //g.setColor(Color.RED);
                            //g.drawLine(x*40, y*40, (x*40)+40, (y*40)+40);
                            //g.drawLine((x*40)+40, y*40, x*40, (y*40)+40);
                        	g.drawImage(ko.getImage(), x*40, y*40, null);
                        }
                    }
                    if(battle.getBattleTableValue(x, y)<0&&battle.getBattleTableValue(x, y)>-10){
                         g.drawImage(battle.team2.getGladiators().elementAt((-battle.getBattleTableValue(x, y))-1).getImage().getImage(), x*40, y*40, null);
                         if(battle.team2.getGladiators().elementAt(-(battle.getBattleTableValue(x, y))-1).getHealth()<=0){
                        	 g.drawImage(ko.getImage(), x*40, y*40, null);
                        }
                    }
                }
            }
            if(showhit){
            	if(missOrSplat)
            		g.drawImage(miss.getImage(), hitx*40, hity*40, null);
            	else
            		g.drawImage(splatter.getImage(), hitx*40, hity*40, null);
            }
        }
	}

    /** This method is called from within the constructor to
     * initialize the form.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 51, 255));
        setPreferredSize(new java.awt.Dimension(412, 333));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
