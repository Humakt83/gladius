package com.ukkosnetti.gladius.gui;
import javax.swing.*;
import javax.swing.border.*;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.controller.Controller;
import com.ukkosnetti.gladius.gui.components.GradientPanel;
import com.ukkosnetti.gladius.gui.components.RedButton;

import java.awt.*;
public class SeasonPanel extends GradientPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6296982313044553324L;
	private JButton ret;
	private JPanel table = new JPanel(){
		private static final long serialVersionUID = -1212412875932538308L;
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
	private JLabel opponent = new JLabel("OPPONENT   ");
	private JLabel opponent1 = new JLabel("...................");
	private JLabel opponent2 = new JLabel("...................");
	private JLabel opponent3 = new JLabel("...................");
	private JLabel result = new JLabel("RESULT");
	private JLabel result1 = new JLabel("N/A");
	private JLabel result2 = new JLabel("N/A");
	private JLabel result3 = new JLabel("N/A");
	private final Font fonN = new Font("High Tower Text", Font.PLAIN, 16);
	private final Font fonH = new Font("High Tower Text", Font.BOLD, 20);
	public SeasonPanel(){
		init();
	}
	public void init(){
		opponent1.setFont(fonN);
		opponent1.setForeground(Color.WHITE);
		opponent2.setForeground(Color.WHITE);
		opponent3.setForeground(Color.WHITE);
		result1.setForeground(Color.WHITE);
		result2.setForeground(Color.WHITE);
		result3.setForeground(Color.WHITE);
		opponent2.setFont(fonN);
		opponent3.setFont(fonN);
		result1.setFont(fonN);
		result2.setFont(fonN);
		result3.setFont(fonN);
		result.setFont(fonH);
		opponent.setFont(fonH);
		table.setBackground(Color.RED);
		table.setLayout(new GridLayout(0,2));
		table.add(opponent);
		table.add(result);
		table.add(opponent1);
		table.add(result1);
		table.add(opponent2);
		table.add(result2);
		table.add(opponent3);
		table.add(result3);
		ret = new RedButton("LEAVE");
		ret.setBackground(Color.RED);
		ret.setForeground(Color.WHITE);
		this.setBackground(Color.BLUE);
		this.setLayout(new FlowLayout());
		this.add(table);
		this.add(ret);
	}
	public void setText(Team cur){
		opponent1.setText(cur.getOpponent1());
		opponent2.setText(cur.getOpponent2());
		opponent3.setText(cur.getOpponent3());
		int r1 = cur.getResult(1);
		switch(r1){
			case 0:
				result1.setText("N/A");
				break;
			case 1:
				result1.setText("WIN");
				break;
			case 2:
				result1.setText("LOSS");
				break;
			case 3:
				result1.setText("DRAW");
				break;
		}
		int r2 = cur.getResult(2);
		switch(r2){
			case 0:
				result2.setText("N/A");
				break;
			case 1:
				result2.setText("WIN");
				break;
			case 2:
				result2.setText("LOSS");
				break;
			case 3:
				result2.setText("DRAW");
				break;
		}
	}
	public void setController(Controller c){
		ret.setActionCommand("TAVERN_LEAVE");
		ret.addActionListener(c);		
	}
}
