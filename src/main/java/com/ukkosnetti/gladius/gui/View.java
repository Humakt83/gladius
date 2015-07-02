/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * View.java
 *
 * Created on 22.4.2009, 16:52:52
 */

package com.ukkosnetti.gladius.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ukkosnetti.gladius.Battle;
import com.ukkosnetti.gladius.controller.Controller;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.components.GradientPanel;
import com.ukkosnetti.gladius.gui.components.RedButton;

/**
 *
 * @author Humakt83
 */
public class View extends javax.swing.JFrame implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7086547429633216078L;

	/** Creates new form View */
	public View(MainPanel m, TavernPanel t, ShopPanel s, BattlePanel b, SeasonPanel se, TeamPanel te) {
		tp = t;
		mp = m;
		sp = s;
		bp = b;
		sep = se;
		tep = te;
		initComponents();
		ChangingPanel.setLayout(cl);
		ChangingPanel.add(mp, "mp");
		ChangingPanel.add(tp, "tp");
		ChangingPanel.add(sp, "sp");
		ChangingPanel.add(bp, "bp");
		ChangingPanel.add(sep, "sep");
		ChangingPanel.add(tep, "tep");
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container.setEnabled(false);
		// forming team name view components
		namelabel.setOpaque(false);
		namelabel.setForeground(Color.WHITE);
		namepanel.setLayout(new FlowLayout());
		namepanel.add(namelabel);
		namepanel.add(namefield);
		namepanel.add(confirmnamechanges);
		namepanel.setBackground(Color.RED);
		setName.add(namepanel);
		setName.setDefaultCloseOperation(HIDE_ON_CLOSE);
		setName.setSize(250, 120);
		setName.setResizable(false);
		setName.setIconImage(gladius.getImage());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = setName.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setName.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		// center frame to screen
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 4);
	}

	public void setController(Controller c) {
		mp.setController(c);
		tp.setController(c);
		sp.setController(c);
		sep.setController(c);
		tep.setController(c);
		NewGame.setActionCommand("NEW");
		NewGame.addActionListener(c);
		Exit.setActionCommand("EXIT");
		Exit.addActionListener(c);
		about.addActionListener(c);
		about.setActionCommand("ABOUT");
		kostats.addActionListener(c);
		kostats.setActionCommand("KO");
		helper.setActionCommand("HELP");
		helper.addActionListener(c);
		teamstats.addActionListener(c);
		teamstats.setActionCommand("TOP_TEAMS");
		save.setActionCommand("SAVE");
		save.addActionListener(c);
		MenuGladiatorName.setActionCommand("GLADIATOR_NAME");
		MenuTeamName.setActionCommand("TEAM_NAME");
		MenuGladiatorName.addActionListener(c);
		MenuTeamName.addActionListener(c);
		confirmnamechanges.addActionListener(c);
		LoadGame.setActionCommand("LOAD");
		LoadGame.addActionListener(c);
		resign.setActionCommand("RESIGN");
		resign.addActionListener(c);
		fire.setActionCommand("FIRE");
		fire.addActionListener(c);
		Gladiator1.addMouseListener(c);
		Gladiator2.addMouseListener(c);
		Gladiator3.addMouseListener(c);
		Gladiator4.addMouseListener(c);
		Gladiator5.addMouseListener(c);
		Gladiator6.addMouseListener(c);
		jScrollPane1.getVerticalScrollBar().getModel().addChangeListener(this);
	}

	public void setBattleController(Battle b) {
		this.addKeyListener(b);
		this.setFocusable(true);
		this.requestFocus();
	}

	public void removeBattleController(Battle b) {
		this.removeKeyListener(b);
	}

	public void changePanel(String s) {
		cl.show(ChangingPanel, s);
	}

	public void addText(String s) {
		shouldscroll = true;
		MessageArea.append(s + "\n");
	}

	public void setTeamName(String s) {
		TeamName.setText(s);
	}

	public void enableStuff() {
		MenuGladiatorName.setEnabled(true);
		MenuTeamName.setEnabled(true);
		kostats.setEnabled(true);
		teamstats.setEnabled(true);
		save.setEnabled(true);
		fire.setEnabled(true);
		LoadGame.setEnabled(true);
		resign.setEnabled(true);
		mp.enableStuff();
	}

	public void disableStuff() {
		MenuGladiatorName.setEnabled(false);
		MenuTeamName.setEnabled(false);
		fire.setEnabled(false);
		save.setEnabled(false);
		resign.setEnabled(false);
		LoadGame.setEnabled(false);
	}

	public void setSquirrels(String s) {
		Squirrels.setText(s);
	}

	public void teamorgladiatorNameOpen(boolean team, String n) {
		if (team) {
			setName.setTitle("Change Team Name");
			namelabel.setText("Team Name: ");
			confirmnamechanges.setActionCommand("CHANGE_TEAM_NAME");
		} else {
			setName.setTitle("Change Gladiator Name");
			namelabel.setText("Gladiator Name: ");
			confirmnamechanges.setActionCommand("CHANGE_GLADIATOR_NAME");
		}
		namefield.setText(n);
		setName.setVisible(true);
	}

	public String getName() {
		setName.setVisible(false);
		return namefield.getText();
	}

	public Gladiator getLabelGladiator(Object o) {
		Gladiator gl = null;
		if (o.equals(Gladiator1) && gladiators.size() > 0) {
			gl = gladiators.get(0);
			Gladiator1.setBackground(Color.RED);
			Gladiator1.setOpaque(true);
			this.showGladiator(gl);
		} else {
			Gladiator1.setBackground(Color.WHITE);
			Gladiator1.setOpaque(false);
		}
		if (o.equals(Gladiator2) && gladiators.size() > 1) {
			gl = gladiators.get(1);
			Gladiator2.setOpaque(true);
			Gladiator2.setBackground(Color.RED);
			this.showGladiator(gl);
		} else {
			Gladiator2.setBackground(Color.WHITE);
			Gladiator2.setOpaque(false);
		}
		if (o.equals(Gladiator3) && gladiators.size() > 2) {
			gl = gladiators.get(2);
			Gladiator3.setBackground(Color.RED);
			Gladiator3.setOpaque(true);
			this.showGladiator(gl);
		} else {
			Gladiator3.setBackground(Color.WHITE);
			Gladiator3.setOpaque(false);
		}
		if (o.equals(Gladiator4) && gladiators.size() > 3) {
			gl = gladiators.get(3);
			Gladiator4.setBackground(Color.RED);
			Gladiator4.setOpaque(true);
			this.showGladiator(gl);
		} else {
			Gladiator4.setBackground(Color.WHITE);
			Gladiator4.setOpaque(false);
		}
		if (o.equals(Gladiator5) && gladiators.size() > 4) {
			gl = gladiators.get(4);
			Gladiator5.setBackground(Color.RED);
			Gladiator5.setOpaque(true);
			this.showGladiator(gl);
		} else {
			Gladiator5.setBackground(Color.WHITE);
			Gladiator5.setOpaque(false);
		}
		if (o.equals(Gladiator6) && gladiators.size() > 5) {
			gl = gladiators.get(5);
			Gladiator6.setBackground(Color.RED);
			Gladiator6.setOpaque(true);
			this.showGladiator(gl);
		} else {
			Gladiator6.setBackground(Color.WHITE);
			Gladiator6.setOpaque(false);
		}
		return gl;
	}

	public void clearGladiatorPanels() {
		Gladiator1.setIcon(new ImageIcon("res/BLANK.gif"));
		Gladiator2.setIcon(new ImageIcon("res/BLANK.gif"));
		Gladiator3.setIcon(new ImageIcon("res/BLANK.gif"));
		Gladiator4.setIcon(new ImageIcon("res/BLANK.gif"));
		Gladiator5.setIcon(new ImageIcon("res/BLANK.gif"));
		Gladiator6.setIcon(new ImageIcon("res/BLANK.gif"));
	}

	public void makeRed() {
		this.getLabelGladiator(Gladiator1);
	}

	public void addGladiatorstoPanels(List<Gladiator> gl) {
		gladiators = gl;
		if (gl != null)
			for (int i = 0; i < gl.size(); i++) {
				switch (i) {
				case 0:
					Gladiator1.setIcon(gl.get(i).getImage());
					break;
				case 1:
					Gladiator2.setIcon(gl.get(i).getImage());
					break;
				case 2:
					Gladiator3.setIcon(gl.get(i).getImage());
					break;
				case 3:
					Gladiator4.setIcon(gl.get(i).getImage());
					break;
				case 4:
					Gladiator5.setIcon(gl.get(i).getImage());
					break;
				case 5:
					Gladiator6.setIcon(gl.get(i).getImage());
					break;
				}
			}
	}

	public void showGladiator(Gladiator gladiator) {
		if (gladiator == null) {
			return;
		}
		Gladiator_Name.setText(gladiator.getName());
		Health.setText(gladiator.getHealth() + "/" + gladiator.getMaxhealth());
		Strength.setText("" + gladiator.getStrength());
		Mana.setText("" + gladiator.getMana());
		Spear.setText(gladiator.getSpearskillAtt() + "/" + gladiator.getSpearskillDef());
		Sword.setText(gladiator.getSwordskillAtt() + "/" + gladiator.getSwordskillDef());
		Hammer.setText(gladiator.getHammerskillAtt() + "/" + gladiator.getHammerskillDef());
		Axe.setText(gladiator.getAxeskillAtt() + "/" + gladiator.getAxeskillDef());
		Resistance.setText("" + gladiator.getResistance());
		Evasion.setText("" + gladiator.getEvasion());
		Bow.setText("" + gladiator.getBowskill());
		Crossbow.setText("" + gladiator.getCrossbowskill());
		actualrace.setText(gladiator.getRace());
		upkeemam.setText("" + gladiator.getUpkeep());
		if (gladiator.getMelee() == null) {
			MeleeName.setText("None");
			MeleeDamage.setText("0");
		} else {
			MeleeName.setText(gladiator.getMelee().getName());
			MeleeDamage.setText(gladiator.getMelee().getMinDam() + "-" + gladiator.getMelee().getMaxDam());
		}
		if (gladiator.getRanged() == null) {
			RangedName.setText("None");
			RangedDamage.setText("0");
		} else {
			RangedName.setText(gladiator.getRanged().getName());
			RangedDamage.setText(gladiator.getRanged().getMinDam() + "-" + gladiator.getRanged().getMaxDam());
		}
		if (gladiator.getArmor() == null) {
			ArmorName.setText("None");
			ArmorProtection.setText("0");
		} else {
			ArmorName.setText(gladiator.getArmor().getName());
			ArmorProtection.setText("" + gladiator.getArmor().getArmor());
		}
		if (gladiator.getSpell1() == null) {
			Spell1Name.setText("None");
			Spell1Damage.setText("0");
			Spell1Mana.setText("0");
		} else {
			Spell1Name.setText(gladiator.getSpell1().getName());
			if (gladiator.getSpell1().getDamageSpell())
				Spell1Effect.setText("Damage");
			else
				Spell1Effect.setText("Healing");
			Spell1Damage.setText(gladiator.getSpell1().getMinDamage() + "-" + gladiator.getSpell1().getMaxDamage());
			Spell1Mana.setText("" + gladiator.getSpell1().getManaCost());
		}
		if (gladiator.getSpell2() == null) {
			Spell2Name.setText("None");
			Spell2Damage.setText("0");
			Spell2Mana.setText("0");
		} else {
			Spell2Name.setText(gladiator.getSpell2().getName());
			if (gladiator.getSpell2().getDamageSpell())
				Spell2Effect.setText("Damage");
			else
				Spell2Effect.setText("Healing");
			Spell2Damage.setText(gladiator.getSpell2().getMinDamage() + "-" + gladiator.getSpell2().getMaxDamage());
			Spell2Mana.setText("" + gladiator.getSpell1().getManaCost());
		}
	}

	public void stateChanged(ChangeEvent e) {
		// Test for flag. Otherwise, if we scroll unconditionally,
		// the scroll bar will be stuck at the bottom even when the
		// user tries to drag it. So we only scroll when we know
		// we've added text
		if (shouldscroll) {
			JScrollBar vertBar = jScrollPane1.getVerticalScrollBar();
			vertBar.setValue(vertBar.getMaximum());
			shouldscroll = false;
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		Container = new JPanel() {
			private static final long serialVersionUID = -8510359596315741787L;

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Dimension d = this.getSize();
				g2d.setPaint(new GradientPaint(0, 0, new Color(105, 0, 155), 50, 50, new Color(75, 0, 105), true));
				g2d.fillRect(0, 0, d.width, d.height);
			}
		};
		ChangingPanel = new javax.swing.JPanel();
		GladiatorPanel = new GradientPanel();
		jPanel1 = new javax.swing.JPanel();
		Gladiator1 = new javax.swing.JLabel();
		Gladiator2 = new javax.swing.JLabel();
		Gladiator3 = new javax.swing.JLabel();
		Gladiator4 = new javax.swing.JLabel();
		Gladiator5 = new javax.swing.JLabel();
		Gladiator6 = new javax.swing.JLabel();
		TeamName = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel() {
			private static final long serialVersionUID = -8565442463248186340L;
			{
				this.setBorder(new BevelBorder(BevelBorder.RAISED));
			}

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Dimension d = this.getSize();
				g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), d.width, 0, new Color(255, 0, 0)));
				g2d.fillRect(0, 0, d.width, d.height);
			}
		};
		jLabel4 = new javax.swing.JLabel();
		Gladiator_Name = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		Strength = new javax.swing.JLabel();
		Mana = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		Health = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel() {
			private static final long serialVersionUID = 1834377823101098450L;
			{
				this.setBorder(new BevelBorder(BevelBorder.RAISED));
			}

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Dimension d = this.getSize();
				g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), d.width, 0, new Color(255, 0, 0)));
				g2d.fillRect(0, 0, d.width, d.height);
			}
		};
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel16 = new javax.swing.JLabel();
		jLabel17 = new javax.swing.JLabel();
		jLabel18 = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		Spear = new javax.swing.JLabel();
		Sword = new javax.swing.JLabel();
		Axe = new javax.swing.JLabel();
		Hammer = new javax.swing.JLabel();
		Bow = new javax.swing.JLabel();
		Crossbow = new javax.swing.JLabel();
		Evasion = new javax.swing.JLabel();
		Resistance = new javax.swing.JLabel();
		Squirrels = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel() {
			private static final long serialVersionUID = 1669397330336216026L;
			{
				this.setBorder(new BevelBorder(BevelBorder.RAISED));
			}

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Dimension d = this.getSize();
				g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), d.width, 0, new Color(255, 0, 0)));
				g2d.fillRect(0, 0, d.width, d.height);
			}
		};
		jLabel20 = new javax.swing.JLabel();
		jLabel21 = new javax.swing.JLabel();
		MeleeName = new javax.swing.JLabel();
		jLabel22 = new javax.swing.JLabel();
		RangedName = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		RangedDamage = new javax.swing.JLabel();
		MeleeDamage = new javax.swing.JLabel();
		jPanel5 = new javax.swing.JPanel() {
			private static final long serialVersionUID = -2307242449287234293L;
			{
				this.setBorder(new BevelBorder(BevelBorder.RAISED));
			}

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Dimension d = this.getSize();
				g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), d.width, 0, new Color(255, 0, 0)));
				g2d.fillRect(0, 0, d.width, d.height);
			}
		};
		jLabel23 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		Spell1Name = new javax.swing.JLabel();
		jLabel25 = new javax.swing.JLabel();
		Spell2Name = new javax.swing.JLabel();
		Spell1Effect = new javax.swing.JLabel();
		Spell2Effect = new javax.swing.JLabel();
		Spell2Damage = new javax.swing.JLabel();
		Spell1Damage = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		Spell1Mana = new javax.swing.JLabel();
		Spell2Mana = new javax.swing.JLabel();
		jPanel6 = new javax.swing.JPanel() {
			private static final long serialVersionUID = 4681837100762777494L;
			{
				this.setBorder(new BevelBorder(BevelBorder.RAISED));
			}

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Dimension d = this.getSize();
				g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), d.width, 0, new Color(255, 0, 0)));
				g2d.fillRect(0, 0, d.width, d.height);
			}
		};
		jLabel26 = new javax.swing.JLabel();
		ArmorName = new javax.swing.JLabel();
		Labe = new javax.swing.JLabel();
		race = new JLabel("Race");
		actualrace = new JLabel("Kaarnapeikko");
		upkeep = new JLabel("Salary");
		upkeemam = new JLabel("##");
		ArmorProtection = new javax.swing.JLabel();
		InfoPanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		MessageArea = new javax.swing.JTextArea();
		jMenuBar1 = new javax.swing.JMenuBar() {
			private static final long serialVersionUID = -2307242449287234293L;

			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Dimension d = this.getSize();
				g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), d.width, 0, new Color(255, 0, 0)));
				g2d.fillRect(0, 0, d.width, d.height);
			}
		};
		jMenu1 = new javax.swing.JMenu();
		NewGame = new javax.swing.JMenuItem();
		LoadGame = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		Exit = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		MenuTeamName = new javax.swing.JMenuItem();
		jMenu5 = new javax.swing.JMenu();
		MenuGladiatorName = new javax.swing.JMenuItem();
		jMenu4 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		kostats = new JMenuItem("Knockouts");
		about = new JMenuItem("About");
		helper = new JMenuItem("Help");
		fire = new JMenuItem("Fire Gladiator");
		save = new JMenuItem("Save Game");
		resign = new JMenuItem("Resign");
		fire.setEnabled(false);
		teamstats = new JMenuItem("Top Teams");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Gladius I Arenum");
		setBackground(new Color(150, 0, 255));
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		Mana.setText("00");

		Container.setBackground(new Color(150, 0, 255));
		Container
				.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.black, java.awt.Color.darkGray));
		Container.setPreferredSize(new java.awt.Dimension(this.getWidth(), this.getHeight()));

		ChangingPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.black,
				java.awt.Color.darkGray));
		ChangingPanel.setName("ChangingPanel"); // NOI18N
		ChangingPanel.setPreferredSize(new java.awt.Dimension(412, 333));

		javax.swing.GroupLayout ChangingPanelLayout = new javax.swing.GroupLayout(ChangingPanel);
		ChangingPanel.setLayout(ChangingPanelLayout);
		ChangingPanelLayout.setHorizontalGroup(ChangingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 484, Short.MAX_VALUE));
		ChangingPanelLayout.setVerticalGroup(ChangingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 327, Short.MAX_VALUE));
		GladiatorPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.black,
				java.awt.Color.darkGray));
		GladiatorPanel.setName("GladiatorPanel"); // NOI18N

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));
		jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		Gladiator1.setIcon(new ImageIcon("res/BLANK.gif")); // NOI18N

		Gladiator2.setIcon(new ImageIcon("res/BLANK.gif")); // NOI18N

		Gladiator3.setIcon(new ImageIcon("res/BLANK.gif")); // NOI18N

		Gladiator4.setIcon(new ImageIcon("res/BLANK.gif")); // NOI18N

		Gladiator5.setIcon(new ImageIcon("res/BLANK.gif")); // NOI18N

		Gladiator6.setIcon(new ImageIcon("res/BLANK.gif")); // NOI18N

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(Gladiator1).addGap(26, 26, 26).addComponent(Gladiator2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 35, Short.MAX_VALUE).addComponent(Gladiator3).addGap(18, 18, 18).addComponent(Gladiator4)
						.addGap(18, 18, 18).addComponent(Gladiator5).addGap(18, 18, 18).addComponent(Gladiator6).addGap(22, 22, 22)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addGap(24, 24, 24)
						.addGroup(
								jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(Gladiator2).addComponent(Gladiator1).addComponent(Gladiator3)
										.addComponent(Gladiator4).addComponent(Gladiator5).addComponent(Gladiator6)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		TeamName.setFont(new java.awt.Font("High Tower Text", 1, 18)); // NOI18N
		TeamName.setForeground(new java.awt.Color(255, 255, 255));
		TeamName.setText("TEAM");

		jPanel2.setBackground(new java.awt.Color(255, 0, 51));
		jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jLabel4.setForeground(new java.awt.Color(255, 255, 255));
		jLabel4.setText("Strength");

		Gladiator_Name.setFont(new java.awt.Font("High Tower Text", 1, 14)); // NOI18N
		Gladiator_Name.setForeground(new java.awt.Color(255, 255, 255));
		Gladiator_Name.setText("Name");

		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("Mana");

		Strength.setForeground(new java.awt.Color(255, 255, 255));
		Strength.setText("00");

		Mana.setForeground(new java.awt.Color(255, 255, 255));

		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("Health");

		Health.setForeground(new java.awt.Color(255, 255, 255));
		Health.setText("00000");
		race.setForeground(Color.WHITE);
		actualrace.setForeground(Color.WHITE);
		upkeep.setForeground(Color.WHITE);
		upkeemam.setForeground(Color.WHITE);
		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						jPanel2Layout
								.createSequentialGroup()
								.addGap(27, 27, 27)
								.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(race).addComponent(jLabel3).addComponent(jLabel2))

								.addGap(18, 18, 18)
								.addGroup(
										jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(actualrace)
.addComponent(Health))

								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 70, 70)
								.addGroup(
										jPanel2Layout
												.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(
														jPanel2Layout.createSequentialGroup().addComponent(jLabel5)

														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
																.addComponent(Mana, GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
												.addGroup(
														jPanel2Layout.createSequentialGroup().addComponent(jLabel4)

														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
																.addComponent(Strength, GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))

												.addGroup(
														jPanel2Layout.createSequentialGroup().addComponent(upkeep).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
																.addComponent(upkeemam, GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
								// .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
								.addGap(25, 25, 25))
				.addGroup(
						jPanel2Layout.createSequentialGroup().addGap(113, 113, 113).addComponent(Gladiator_Name, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(175, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(Gladiator_Name).addGap(11, 11, 11)
						.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(Strength).addComponent(jLabel2).addComponent(Health))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel5).addComponent(Mana).addComponent(jLabel3))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(upkeep).addComponent(upkeemam).addComponent(actualrace).addComponent(race))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel3.setBackground(new java.awt.Color(255, 0, 51));
		jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jLabel11.setFont(new java.awt.Font("High Tower Text", 1, 14)); // NOI18N
		jLabel11.setForeground(new java.awt.Color(255, 255, 255));
		jLabel11.setText("Skills");

		jLabel12.setForeground(new java.awt.Color(255, 255, 255));
		jLabel12.setText("Spear");

		jLabel13.setForeground(new java.awt.Color(255, 255, 255));
		jLabel13.setText("Sword");

		jLabel14.setForeground(new java.awt.Color(255, 255, 255));
		jLabel14.setText("Axe");

		jLabel15.setForeground(new java.awt.Color(255, 255, 255));
		jLabel15.setText("Hammer");

		jLabel16.setForeground(new java.awt.Color(255, 255, 255));
		jLabel16.setText("Bow");

		jLabel17.setForeground(new java.awt.Color(255, 255, 255));
		jLabel17.setText("Crossbow");

		jLabel18.setForeground(new java.awt.Color(255, 255, 255));
		jLabel18.setText("Evasion");

		jLabel19.setForeground(new java.awt.Color(255, 255, 255));
		jLabel19.setText("Resistance");

		Spear.setForeground(new java.awt.Color(255, 255, 255));
		Spear.setText("00000");

		Sword.setForeground(new java.awt.Color(255, 255, 255));
		Sword.setText("00000");

		Axe.setForeground(new java.awt.Color(255, 255, 255));
		Axe.setText("00000");

		Hammer.setForeground(new java.awt.Color(255, 255, 255));
		Hammer.setText("00000");

		Bow.setForeground(new java.awt.Color(255, 255, 255));
		Bow.setText("00");

		Crossbow.setForeground(new java.awt.Color(255, 255, 255));
		Crossbow.setText("00");

		Evasion.setForeground(new java.awt.Color(255, 255, 255));
		Evasion.setText("00");

		Resistance.setForeground(new java.awt.Color(255, 255, 255));
		Resistance.setText("00");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel3Layout
						.createSequentialGroup()
						.addGroup(
								jPanel3Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel3Layout.createSequentialGroup().addGap(116, 116, 116).addComponent(jLabel11))
										.addGroup(
												jPanel3Layout
														.createSequentialGroup()
														.addGap(24, 24, 24)
														.addGroup(
																jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel12).addComponent(jLabel13)
																		.addComponent(jLabel14).addComponent(jLabel15))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addGroup(
																jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(Spear).addComponent(Sword).addComponent(Axe)
																		.addComponent(Hammer))
														.addGap(38, 38, 38)
														.addGroup(
																jPanel3Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(
																				jPanel3Layout.createSequentialGroup().addComponent(jLabel19)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 31, Short.MAX_VALUE)
																						.addComponent(Resistance))
																		.addGroup(
																				jPanel3Layout
																						.createSequentialGroup()
																						.addGroup(
																								jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																										.addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
																										.addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING))
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 36, Short.MAX_VALUE)
																						.addGroup(
																								jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(Bow)
																										.addComponent(Crossbow).addComponent(Evasion)))))).addContainerGap(120, 120)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel3Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabel11)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel12).addComponent(jLabel16).addComponent(Spear).addComponent(Bow))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel13).addComponent(jLabel17).addComponent(Sword).addComponent(Crossbow))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel14).addComponent(jLabel18).addComponent(Axe).addComponent(Evasion))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel15).addComponent(jLabel19).addComponent(Hammer)
										.addComponent(Resistance)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		Squirrels.setFont(new java.awt.Font("High Tower Text", 0, 18)); // NOI18N
		Squirrels.setForeground(new java.awt.Color(255, 255, 255));
		Squirrels.setText("0000 sq");

		jPanel4.setBackground(new java.awt.Color(255, 0, 51));
		jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jLabel20.setFont(new java.awt.Font("High Tower Text", 1, 14)); // NOI18N
		jLabel20.setForeground(new java.awt.Color(255, 255, 255));
		jLabel20.setText("Weapons");

		jLabel21.setForeground(new java.awt.Color(255, 255, 255));
		jLabel21.setText("Melee");

		MeleeName.setForeground(new java.awt.Color(255, 255, 255));
		MeleeName.setText("None.......");

		jLabel22.setForeground(new java.awt.Color(255, 255, 255));
		jLabel22.setText("Ranged");

		RangedName.setForeground(new java.awt.Color(255, 255, 255));
		RangedName.setText("None.......");

		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("Damage");

		jLabel6.setForeground(new java.awt.Color(255, 255, 255));
		jLabel6.setText("Damage");

		RangedDamage.setForeground(new java.awt.Color(255, 255, 255));
		RangedDamage.setText("00");

		MeleeDamage.setForeground(new java.awt.Color(255, 255, 255));
		MeleeDamage.setText("00");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel4Layout
						.createSequentialGroup()
						.addGroup(
								jPanel4Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel4Layout.createSequentialGroup().addGap(116, 116, 116).addComponent(jLabel20))
										.addGroup(
												jPanel4Layout
														.createSequentialGroup()
														.addGap(24, 24, 24)
														.addGroup(
																jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel21).addGap(27, 27, 27).addComponent(MeleeName))
																		.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel22).addGap(18, 18, 18).addComponent(RangedName)))
														.addGap(49, 49, 49)
														.addGroup(
																jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel6).addGap(18, 18, 18).addComponent(RangedDamage))
																		.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel1).addGap(18, 18, 18).addComponent(MeleeDamage)))))
						.addContainerGap(137, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel4Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabel20)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel21).addComponent(MeleeName)
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(MeleeDamage))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel22).addComponent(RangedName)
										.addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(RangedDamage))
						.addContainerGap(21, Short.MAX_VALUE)));

		jPanel5.setBackground(new java.awt.Color(255, 0, 51));
		jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jLabel23.setFont(new java.awt.Font("High Tower Text", 1, 14)); // NOI18N
		jLabel23.setForeground(new java.awt.Color(255, 255, 255));
		jLabel23.setText("Spells");

		jLabel24.setForeground(new java.awt.Color(255, 255, 255));
		jLabel24.setText("Spell 1");

		Spell1Name.setForeground(new java.awt.Color(255, 255, 255));
		Spell1Name.setText("None......");

		jLabel25.setForeground(new java.awt.Color(255, 255, 255));
		jLabel25.setText("Spell 2");

		Spell2Name.setForeground(new java.awt.Color(255, 255, 255));
		Spell2Name.setText("None......");

		Spell1Effect.setForeground(new java.awt.Color(255, 255, 255));
		Spell1Effect.setText("Damage");

		Spell2Effect.setForeground(new java.awt.Color(255, 255, 255));
		Spell2Effect.setText("Damage");

		Spell2Damage.setForeground(new java.awt.Color(255, 255, 255));
		Spell2Damage.setText("00");

		Spell1Damage.setForeground(new java.awt.Color(255, 255, 255));
		Spell1Damage.setText("00");

		jLabel7.setForeground(new java.awt.Color(255, 255, 255));
		jLabel7.setText("Cost");

		jLabel8.setForeground(new java.awt.Color(255, 255, 255));
		jLabel8.setText("Cost");

		Spell1Mana.setForeground(new java.awt.Color(255, 255, 255));
		Spell1Mana.setText("00");

		Spell2Mana.setForeground(new java.awt.Color(255, 255, 255));
		Spell2Mana.setText("00");

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel5Layout
						.createSequentialGroup()
						.addGroup(
								jPanel5Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel5Layout.createSequentialGroup().addGap(116, 116, 116).addComponent(jLabel23))
										.addGroup(
												jPanel5Layout
														.createSequentialGroup()
														.addGap(24, 24, 24)
														.addGroup(
																jPanel5Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
																		.addGroup(jPanel5Layout.createSequentialGroup().addComponent(jLabel24).addGap(27, 27, 27).addComponent(Spell1Name))
																		.addGroup(
																				jPanel5Layout
																						.createSequentialGroup()
																						.addComponent(jLabel25)
																						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE).addComponent(Spell2Name)))
														.addGap(49, 49, 49)
														.addGroup(
																jPanel5Layout
																		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(
																				jPanel5Layout.createSequentialGroup().addComponent(Spell2Effect).addGap(18, 18, 18).addComponent(Spell2Damage)
																						.addGap(18, 18, 18).addComponent(jLabel8).addGap(18, 18, 18).addComponent(Spell2Mana))
																		.addGroup(
																				jPanel5Layout.createSequentialGroup().addComponent(Spell1Effect).addGap(18, 18, 18).addComponent(Spell1Damage)
																						.addGap(18, 18, 18).addComponent(jLabel7).addGap(18, 18, 18).addComponent(Spell1Mana)))))
						.addContainerGap(68, Short.MAX_VALUE)));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel5Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabel23)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel24).addComponent(Spell1Name)
										.addComponent(Spell1Effect, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(Spell1Damage)
										.addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(Spell1Mana))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel25)
										.addComponent(Spell2Effect, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(Spell2Damage)
										.addComponent(Spell2Name).addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(Spell2Mana))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel6.setBackground(new java.awt.Color(255, 0, 51));
		jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		jPanel6.setForeground(new java.awt.Color(255, 255, 255));

		jLabel26.setFont(new java.awt.Font("High Tower Text", 1, 14)); // NOI18N
		jLabel26.setForeground(new java.awt.Color(255, 255, 255));
		jLabel26.setText("Armor");

		ArmorName.setForeground(new java.awt.Color(255, 255, 255));
		ArmorName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
		ArmorName.setText("None......");
		Labe.setForeground(new java.awt.Color(255, 255, 255));
		Labe.setText("Protection");

		ArmorProtection.setForeground(new java.awt.Color(255, 255, 255));
		ArmorProtection.setText("00");

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel6Layout
						.createSequentialGroup()
						.addGroup(
								jPanel6Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel6Layout.createSequentialGroup().addGap(116, 116, 116).addComponent(jLabel26))
										.addGroup(
												jPanel6Layout.createSequentialGroup().addGap(82, 82, 82).addComponent(ArmorName).addGap(49, 49, 49).addComponent(Labe).addGap(18, 18, 18)
														.addComponent(ArmorProtection))).addContainerGap(128, Short.MAX_VALUE)));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel6Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabel26)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(
								jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(ArmorName)
										.addComponent(Labe, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(ArmorProtection))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout GladiatorPanelLayout = new javax.swing.GroupLayout(GladiatorPanel);
		GladiatorPanel.setLayout(GladiatorPanelLayout);
		GladiatorPanelLayout.setHorizontalGroup(GladiatorPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						GladiatorPanelLayout
								.createSequentialGroup()
								.addGroup(
										GladiatorPanelLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														GladiatorPanelLayout.createSequentialGroup().addContainerGap()
																.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 390))
												.addGroup(GladiatorPanelLayout.createSequentialGroup().addGap(121, 121, 121).addComponent(TeamName))
												.addGroup(
														GladiatorPanelLayout
																.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		GladiatorPanelLayout
																				.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
																				.addComponent(Squirrels)
																				.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(
						GladiatorPanelLayout.createSequentialGroup().addContainerGap().addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 390)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(
						GladiatorPanelLayout.createSequentialGroup().addContainerGap().addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 390)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(
						javax.swing.GroupLayout.Alignment.LEADING,
						GladiatorPanelLayout.createSequentialGroup().addContainerGap().addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 390)
								.addContainerGap())
				.addGroup(
						GladiatorPanelLayout.createSequentialGroup().addContainerGap().addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 390)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		GladiatorPanelLayout.setVerticalGroup(GladiatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				GladiatorPanelLayout.createSequentialGroup().addGap(5, 5, 5)
						.addGroup(GladiatorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(TeamName).addComponent(Squirrels))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel1.setPreferredSize(new Dimension(390, 70));
		jPanel2.setPreferredSize(new Dimension(390, 130));
		jPanel3.setPreferredSize(new Dimension(390, 130));
		jPanel4.setPreferredSize(new Dimension(390, 100));
		jPanel5.setPreferredSize(new Dimension(390, 100));
		jPanel6.setPreferredSize(new Dimension(390, 60));
		InfoPanel.setBackground(new java.awt.Color(51, 51, 255));
		InfoPanel
				.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.black, java.awt.Color.darkGray));
		InfoPanel.setName("InfoPanel"); // NOI18N
		InfoPanel.setPreferredSize(new java.awt.Dimension(490, 279));

		MessageArea.setColumns(20);
		MessageArea.setEditable(false);
		MessageArea.setLineWrap(true);
		MessageArea.setWrapStyleWord(true);
		MessageArea.setRows(5);
		MessageArea.setAutoscrolls(true);
		jScrollPane1.setViewportView(MessageArea);

		javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
		InfoPanel.setLayout(InfoPanelLayout);
		InfoPanelLayout.setHorizontalGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 484,
				Short.MAX_VALUE));
		InfoPanelLayout.setVerticalGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262,
				Short.MAX_VALUE));

		javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
		Container.setLayout(ContainerLayout);
		ContainerLayout.setHorizontalGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				ContainerLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(ChangingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(GladiatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
		ContainerLayout.setVerticalGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				ContainerLayout
						.createSequentialGroup()
						.addGroup(
								ContainerLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(
												ContainerLayout
														.createSequentialGroup()
														.addComponent(ChangingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(GladiatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(23, Short.MAX_VALUE)));

		jMenuBar1.setBackground(new java.awt.Color(255, 0, 51));
		jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
		jMenu1.setForeground(Color.WHITE);
		jMenu1.setText("Game");
		jMenu1.setBackground(new java.awt.Color(255, 0, 51));
		NewGame.setText("New Game");
		NewGame.setForeground(Color.WHITE);
		jMenu1.add(NewGame);
		jMenu1.addSeparator();
		NewGame.setBackground(new java.awt.Color(255, 0, 51));
		LoadGame.setForeground(Color.WHITE);
		LoadGame.setText("Load Game");
		LoadGame.setBackground(new java.awt.Color(255, 0, 51));
		jMenu1.add(LoadGame);
		save.setForeground(Color.WHITE);
		save.setBackground(new java.awt.Color(255, 0, 51));
		jMenu1.add(save);
		jMenu1.add(jSeparator1);
		Exit.setForeground(Color.WHITE);
		Exit.setText("Exit");
		Exit.setBackground(new java.awt.Color(255, 0, 51));
		jMenu1.add(Exit);

		jMenuBar1.add(jMenu1);
		jMenu3.setForeground(Color.WHITE);
		jMenu3.setText("Team");
		jMenu3.setBackground(new java.awt.Color(255, 0, 51));
		resign.setForeground(Color.WHITE);
		resign.setText("Resign");
		resign.setEnabled(false);
		resign.setBackground(new java.awt.Color(255, 0, 51));
		MenuTeamName.setForeground(Color.WHITE);
		MenuTeamName.setText("Rename");
		MenuTeamName.setEnabled(false);
		MenuTeamName.setBackground(new java.awt.Color(255, 0, 51));
		kostats.setForeground(Color.WHITE);
		kostats.setEnabled(false);
		kostats.setBackground(new java.awt.Color(255, 0, 51));
		teamstats.setForeground(Color.WHITE);
		teamstats.setEnabled(false);
		teamstats.setBackground(new java.awt.Color(255, 0, 51));
		jMenu3.add(MenuTeamName);
		jMenu3.addSeparator();
		jMenu3.add(resign);

		jMenuBar1.add(jMenu3);
		jMenu5.setForeground(Color.WHITE);
		jMenu5.setText("Gladiator");
		jMenu5.setBackground(new java.awt.Color(255, 0, 51));
		MenuGladiatorName.setForeground(Color.WHITE);
		MenuGladiatorName.setText("Rename");
		MenuGladiatorName.setEnabled(false);
		MenuGladiatorName.setBackground(new java.awt.Color(255, 0, 51));
		jMenu5.add(MenuGladiatorName);
		jMenu5.addSeparator();
		jMenu5.add(fire);
		jMenuBar1.add(jMenu5);
		fire.setForeground(Color.WHITE);
		fire.setBackground(new Color(255, 0, 51));
		jMenu4.setForeground(Color.WHITE);
		jMenu4.setText("Statistics");
		jMenu4.add(teamstats);
		jMenu4.addSeparator();
		jMenu4.add(kostats);
		jMenuBar1.add(jMenu4);
		jMenu2.setForeground(Color.WHITE);
		jMenu2.setText("Help");
		jMenu2.add(helper);
		jMenu2.addSeparator();
		jMenu2.add(about);
		about.setForeground(Color.WHITE);
		about.setBackground(new java.awt.Color(255, 0, 51));
		helper.setForeground(Color.WHITE);
		helper.setBackground(new java.awt.Color(255, 0, 51));
		jMenuBar1.add(jMenu2);
		save.setEnabled(false);

		setJMenuBar(jMenuBar1);
		this.setLayout(new GridLayout(0, 1));
		this.add(Container);
		this.setIconImage(gladius.getImage());
		this.setPreferredSize(new Dimension(942, 725));
		/*
		 * javax.swing.GroupLayout layout = new
		 * javax.swing.GroupLayout(getContentPane());
		 * getContentPane().setLayout(layout); layout.setHorizontalGroup(
		 * layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		 * .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, 937,
		 * javax.swing.GroupLayout.PREFERRED_SIZE) ); layout.setVerticalGroup(
		 * layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		 * .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, 636,
		 * Short.MAX_VALUE) );
		 */
		// this.setBackground(Color.WHITE);
		this.getContentPane().setBackground(new Color(150, 0, 255));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	public void setCurSeason(int s) {
		mp.setSeason(s);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	// JFrame components for player name change view:
	private JFrame setName = new JFrame("Change Team Name");
	private JPanel namepanel = new JPanel() {
		private static final long serialVersionUID = -8510359596315741787L;

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Dimension d = this.getSize();
			g2d.setPaint(new GradientPaint(0, 0, new Color(100, 0, 0), 50, 50, new Color(255, 0, 0), true));
			g2d.fillRect(0, 0, d.width, d.height);
		}
	};
	private JLabel namelabel = new JLabel("Team Name: ");
	private JTextField namefield = new JTextField("Name", 15);
	private JButton confirmnamechanges = new RedButton("OK");
	private List<Gladiator> gladiators;
	private CardLayout cl = new CardLayout();
	private MainPanel mp;
	private TavernPanel tp;
	private ShopPanel sp;
	private BattlePanel bp;
	private SeasonPanel sep;
	private TeamPanel tep;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel ArmorName;
	private javax.swing.JLabel ArmorProtection;
	private javax.swing.JLabel Axe;
	private javax.swing.JLabel Bow;
	private javax.swing.JPanel ChangingPanel;
	private javax.swing.JPanel Container;
	private javax.swing.JLabel Crossbow;
	private javax.swing.JLabel Evasion;
	private javax.swing.JMenuItem Exit;
	private javax.swing.JLabel Gladiator1;
	private javax.swing.JLabel Gladiator2;
	private javax.swing.JLabel Gladiator3;
	private javax.swing.JLabel Gladiator4;
	private javax.swing.JLabel Gladiator5;
	private javax.swing.JLabel Gladiator6;
	private javax.swing.JPanel GladiatorPanel;
	private javax.swing.JLabel Gladiator_Name;
	private javax.swing.JLabel Hammer;
	private javax.swing.JLabel Health;
	private javax.swing.JPanel InfoPanel;
	private javax.swing.JLabel Labe;
	private javax.swing.JLabel Mana;
	private javax.swing.JLabel MeleeDamage;
	private javax.swing.JLabel MeleeName;
	private javax.swing.JMenuItem MenuGladiatorName;
	private javax.swing.JMenuItem MenuTeamName;
	private javax.swing.JTextArea MessageArea;
	private javax.swing.JMenuItem NewGame;
	private javax.swing.JLabel RangedDamage;
	private javax.swing.JLabel RangedName;
	private javax.swing.JLabel Resistance;
	private javax.swing.JLabel Spear;
	private javax.swing.JLabel Spell1Damage;
	private javax.swing.JLabel Spell1Effect;
	private javax.swing.JLabel Spell1Mana;
	private javax.swing.JLabel Spell1Name;
	private javax.swing.JLabel Spell2Damage;
	private javax.swing.JLabel Spell2Effect;
	private javax.swing.JLabel Spell2Mana;
	private javax.swing.JLabel Spell2Name;
	private javax.swing.JLabel Squirrels;
	private javax.swing.JLabel Strength;
	private javax.swing.JLabel Sword;
	private javax.swing.JLabel TeamName;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel21;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel23;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private JLabel race;
	private JLabel actualrace;
	private JLabel upkeep;
	private JLabel upkeemam;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu4;
	private javax.swing.JMenu jMenu5;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem LoadGame;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator1;
	private JMenuItem fire;
	private JMenuItem kostats;
	private JMenuItem teamstats;
	private JMenuItem about;
	private JMenuItem save;
	private JMenuItem resign;
	private JMenuItem helper;
	private boolean shouldscroll = false;
	private ImageIcon gladius = new ImageIcon("Gladius.png");
	// End of variables declaration//GEN-END:variables

}
