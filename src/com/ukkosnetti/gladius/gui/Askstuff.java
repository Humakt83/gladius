package com.ukkosnetti.gladius.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ukkosnetti.gladius.controller.Controller;
import com.ukkosnetti.gladius.gui.components.GradientPanel;
import com.ukkosnetti.gladius.gui.components.RedButton;

/*
 * JFrame for question message.
 */
public class Askstuff extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8528476533916721566L;
	private JPanel container = new GradientPanel();
	private ImageIcon gladius = new ImageIcon("Gladius.png");
	private JLabel message;
	private JButton confirm = new RedButton("YES");
	private JButton cancel = new RedButton("NO");

	public Askstuff(String m, Controller c) {
		// Customizing components
		message = new JLabel(m);
		container.setBackground(new Color(50, 0, 255));
		message.setForeground(Color.WHITE);
		confirm.setBackground(new Color(255, 0, 50));
		cancel.setBackground(new Color(255, 0, 50));
		confirm.setForeground(Color.WHITE);
		cancel.setForeground(Color.WHITE);
		confirm.setActionCommand("CONFIRM");
		cancel.setActionCommand("CANCEL");
		confirm.addActionListener(c);
		cancel.addActionListener(c);
		// Adding components to containers
		container.add(message);
		container.add(confirm);
		container.add(cancel);
		this.add(container);
		// Customizing JFrame
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
		setResizable(false);
		this.setIconImage(gladius.getImage());
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void disposeThis() {
		this.dispose();
	}
}
