package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class RedButton extends JButton{
	private static final long serialVersionUID = -7576541538477067510L;
	public RedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        Dimension d = new Dimension(100, 30);
        setPreferredSize(d);
        setMinimumSize(d);
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
    }
	public RedButton() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        Dimension d = new Dimension(100, 30);
        setPreferredSize(d);
        setMinimumSize(d);
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
    }
    protected void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	Dimension d = this.getSize();
    	g2d.setPaint(new GradientPaint(0, 0, new Color(100,0,0), d.width, 0, new Color(255,0,0)));
    	g2d.fillRect(0, 0, d.width, d.height);
        super.paintComponent(g);
    }
}
