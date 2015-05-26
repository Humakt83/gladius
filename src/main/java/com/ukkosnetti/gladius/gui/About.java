package com.ukkosnetti.gladius.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.ukkosnetti.gladius.gui.components.GradientPanel;

/*
 * Simple class for About view.
 */
public class About extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452823389376599498L;
	private JTextArea abouttxt = new JTextArea(4, 24);
	private JPanel container = new GradientPanel();
	private ImageIcon gladius = new ImageIcon("Gladius.png");

	public About() {
		// Setting component properties.
		abouttxt.setOpaque(false);
		container.setOpaque(true);
		// Customizing JTextArea.
		abouttxt.setFont(new Font("High Tower Text", 1, 18));
		abouttxt.setForeground(Color.WHITE);
		abouttxt.setWrapStyleWord(true);
		abouttxt.setText("Gladius I Arenum. \nGame for unrealistic gladiator battles.\nProgram version 1.0. \nProgramming by Risto Salama. \n" + "Design by Risto Salama and Juha Railio.");
		abouttxt.setEditable(false);
		abouttxt.setLineWrap(true);
		// Adding components to panels.
		container.add(abouttxt);
		add(container);
		// Customizing frame.
		setTitle("About Gladius");
		container.setLayout(new FlowLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		// center frame to screen
		setLocation((screenSize.width - frameSize.width) / 3, (screenSize.height - frameSize.height) / 4);
		pack();
		this.setIconImage(gladius.getImage());
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
