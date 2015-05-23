package com.ukkosnetti.gladius.gui.components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.swing.border.*;
public class GradientPanel extends JPanel{
	private static final long serialVersionUID = -8465625248180103707L;
	public GradientPanel(){
		this.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.lightGray, Color.white, Color.black, Color.darkGray));
	}
	public void paintComponent(Graphics g){
    	Graphics2D g2d = (Graphics2D) g;
    	Dimension d = this.getSize();
    	g2d.setPaint(new GradientPaint(0, 0, new Color(200,200,200), d.width, 0, new Color(0,0,0)));
    	g2d.fillRect(0, 0, d.width, d.height);
    }
}
