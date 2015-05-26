package com.ukkosnetti.gladius.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.ukkosnetti.gladius.controller.Controller;
import com.ukkosnetti.gladius.gui.components.RedButton;

public class LoadGame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2813834136126267964L;
	private JPanel container = new JPanel();
	private JScrollPane scroller;
	private JList<String> saves;
	private DefaultListModel<String> saveslist;
	private ImageIcon gladius = new ImageIcon("Gladius.png");
	private JButton loadgame = new RedButton("LOAD");

	public LoadGame(ArrayList<File> files, Controller c) {
		loadgame.setActionCommand("LOADTHISGAME");
		loadgame.addActionListener(c);
		loadgame.setForeground(Color.WHITE);
		loadgame.setBackground(new Color(255, 0, 50));
		scroller = new JScrollPane();
		saveslist = new DefaultListModel<>();
		Iterator<File> iter = files.iterator();
		while (iter.hasNext()) {
			saveslist.addElement(iter.next().getName().replace(".gla", ""));
		}
		saves = new JList<>(saveslist);
		saves.setVisibleRowCount(7);
		saves.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		saves.setFixedCellWidth(160);
		scroller.setWheelScrollingEnabled(true);
		scroller.getViewport().setView(saves);
		container.setBackground(new Color(50, 0, 255));
		container.add(scroller);
		container.add(loadgame);
		this.add(container);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(180, 200);
		this.setIconImage(gladius.getImage());
		this.setTitle("Load Game");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		// center frame to screen
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 4);
	}

	public String getSelectedFile() {
		return saves.getSelectedValue().toString();
	}
}
