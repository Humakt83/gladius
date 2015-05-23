package com.ukkosnetti.gladius;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Random;

import com.ukkosnetti.gladius.concept.Season;
import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.gui.BattlePanel;
import com.ukkosnetti.gladius.gui.View;
import com.ukkosnetti.gladius.item.MeleeWeaponType;
import com.ukkosnetti.gladius.item.RangedWeaponType;
import com.ukkosnetti.gladius.item.Spell;

/*
 * Class that handles the battle logics of the game. Visual part is handled by BattlePanel of which's mouse listener this class is. 
 */
public class Battle implements MouseListener, KeyListener, Runnable, Serializable {
	private static final long serialVersionUID = 555762054740711627L;
	private Random r = new Random(); // Used for various purposes including
										// decision whether attack is hit or
										// miss.
	public final int maxRounds = 25; // Number of rounds in battle.
	public Team team1; // Team on the left side of battle.
	public Team team2; // Team on the right side of battle.
	private Season season; // Used to send battle outcome to and for starting
							// next battle.
	private View v; // Used to display messages and gladiators to players.
	private Gladiator activeGladiator; // Current gladiator.
	private int battletable[][] = new int[10][8]; // Important table that
													// maintains information of
													// position of gladiators in
													// battle.
	private int movetable[][] = new int[10][8]; // Table used to display players
												// possible places to move or
												// attack with current
												// gladiators.
	private BattlePanel drawingarea; // Visual component of battle.
	private boolean team1turn = true; // Which teams turn it is.
	private int round = 0; // Current round of battle (displayed as value + 1).
	private int team1size = 0, team2size = 0; // How many gladiators teams have.
	private boolean pause = false, stop = false; // Boolean flags for pausing or
													// stopping thread.
	private Thread t = null;

	public Battle(Team t1, Team t2, Season s, BattlePanel bp, View v) {
		// System.out.println(t1.getName() +", " + t2.getName());
		if (t1.getComputer() && t2.getComputer()) { // Checking if both teams
													// are controlled by
													// computer.
			int t1ko = 0, t2ko = 0; // Number of knockouts for both teams in
									// current battle.
			int a = 0, b = 0; // Used to indicate gladiators.
			while (t1ko < t2.getMembers() && t2ko < t1.getMembers()) { // If
																		// both
																		// teams
																		// have
																		// gladiators
																		// left
																		// that
																		// are
																		// still
																		// in
																		// battle.
				Gladiator aglad = t1.getGladiators().get(a), bglad = t2.getGladiators().get(b); // Gladiator
																											// from
																											// team1
																											// and
																											// team2.
				// Adding values to integers from Gladiators' attributes and
				// their weapon attributes.
				int ra = aglad.getStrength() + aglad.getNaturalarmor() + aglad.getMaxhealth(), rb = bglad.getStrength() + bglad.getNaturalarmor() + bglad.getMaxhealth();
				if (aglad.getArmor() != null)
					ra += aglad.getArmor().getArmor();
				if (aglad.getMelee() != null)
					ra += aglad.getMelee().battleDamage();
				if (aglad.getRanged() != null)
					ra += aglad.getRanged().battleDamage();
				if (aglad.getSpell1() != null)
					ra += aglad.getSpell1().battleDamage();
				if (aglad.getSpell2() != null)
					ra += aglad.getSpell2().battleDamage();
				if (bglad.getArmor() != null)
					rb += bglad.getArmor().getArmor();
				if (bglad.getMelee() != null)
					rb += bglad.getMelee().battleDamage();
				if (bglad.getRanged() != null)
					rb += bglad.getRanged().battleDamage();
				if (bglad.getSpell1() != null)
					rb += bglad.getSpell1().battleDamage();
				if (bglad.getSpell2() != null)
					rb += bglad.getSpell2().battleDamage();
				int ran = r.nextInt(ra) - r.nextInt(rb); // Random value used to
															// decide which
															// gladiator won.
				if (ran >= 0) { // Lightly favors team 1 but who cares they are
								// both computer controlled, right?
					aglad.setKnockdowns(1); // Increases knockdowns for
											// gladiator.
					// Increasing skills of gladiator.
					aglad.increaseMeleeAttackSkill();
					aglad.increaseMeleeDefendSkill();
					aglad.increaseRangedSkill();
					t1ko++; // increases number of KO's made by team 1.
				} else {
					bglad.setKnockdowns(1); // Increases knockdowns for
											// gladiator.
					// Increasing skills of gladiator.
					bglad.increaseMeleeAttackSkill();
					bglad.increaseMeleeDefendSkill();
					bglad.increaseRangedSkill();
					t2ko++; // increases number of KO's made by team 2.
				}
				if (a < t1.getMembers() - 1)
					a++; // If team 1 contains more members increase a, if not
							// reset a.
				else
					a = 0;
				if (b < t2.getMembers() - 1)
					b++; // If team 2 contains more members increase b, if not
							// reset b.
				else
					b = 0;
			}
			if (t1ko == t2.getMembers()) { // Increases wins for team 1 or team
											// 2, while adding defeat for
											// opposing team.
				t1.increaseMatchWins();
				t1.setResult(1);
				t2.setResult(2);
			} else {
				t2.increaseMatchWins();
				t1.setResult(2);
				t2.setResult(1);
			}
			s.nextBattle(bp, v); // Starts next battle.
		} else { // If either team is player controlled we get here.
			this.v = v;
			team1 = t1;
			team2 = t2;
			v.addText("Arena battle between " + team1.getName() + " and " + team2.getName() + " begins!");
			season = s;
			drawingarea = bp;
			drawingarea.setController(this);
			drawingarea.setSceneryItems();
			// Filling battletable with 0's.
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 8; y++) {
					battletable[x][y] = 0;
				}
			}
			// Placing team 1 gladiators to table, number greater than zero
			// stands for member of team 1.
			for (int i = team1.getGladiators().size(), j = 1; i > 0; i--, j++) {
				battletable[1][j] = i;
				team1.getGladiators().get(i - 1).setLocation(new Point(1, j));
			}
			// Placing team 2 gladiators to table, number lesser than zero
			// stands for member of team 2.
			for (int i = team2.getGladiators().size(), j = 1; i > 0; i--, j++) {
				battletable[8][j] = -i;
				team2.getGladiators().get(i - 1).setLocation(new Point(8, j));
			}
			// Reseting values of moveTable, which is done often so using
			// function for it is practical.
			this.resetMoveTable();
			drawingarea.paintBattle(); // Tells BattlePanel to draw stuff.
			v.setBattleController(this);
			// Starting new thread.
			if (t == null) {
				t = new Thread(this);
				stop = false;
				t.start();
			}
		}
	}

	// Function used to reset values of movetable to 0.
	public void resetMoveTable() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 8; y++) {
				movetable[x][y] = 0;
			}
		}
	}

	// Main loop of battle.
	public void run() {
		while (!stop) { // This loops once for each round of battle until battle
						// ends.
			v.addText("Start of round " + (round + 1) + " of " + maxRounds + ".");
			// Getting amount of conscious (in other words, not knocked out)
			// members of both teams
			team1size = team1.getGladiators().size();
			for (int j = team1size; j > 0; j--)
				if (team1.getGladiators().get(j - 1).getHealth() <= 0)
					team1size--;
			team2size = team2.getGladiators().size();
			for (int j = team2size; j > 0; j--)
				if (team2.getGladiators().get(j - 1).getHealth() <= 0)
					team2size--;
			int i = 0, counter = 0, team1gladturn = 0, team2gladturn = 0;
			team1turn = true;
			// Battle ends if there are no healthy members left in either team
			// battle.
			if (team1size <= 0) {
				stop = true;
				this.battleEnds(2);
			}
			if (team2size <= 0) {
				stop = true;
				this.battleEnds(1);
			}
			while ((!stop && (i < team1size || i < team2size) && !(team1size <= 0 || team2size <= 0))) { // Stop
																											// is
																											// checked
																											// here
																											// just
																											// for
																											// precaution.
				while (!stop && (!pause && (i < team1size || i < team2size) && !(team1size <= 0 || team2size <= 0))) { // Stop
																														// is
																														// checked
																														// here
																														// just
																														// for
																														// precaution.
					if (counter == 2) { // If both teams have had their turn,
										// increase i and reset counter.
						i++;
						counter = 0;
					}
					if (team1turn && i < team1size) {
						counter++;
						while (team1.getGladiators().get(team1gladturn).getHealth() <= 0)
							team1gladturn++; // Gets the nearest conscious
												// gladiator in team.
						battleTurn(team1.getGladiators().get(team1gladturn));
						team1turn = false;
						if (team1.getComputer())
							this.moveAI(team1.getGladiators().get(team1gladturn)); // If
																							// team
																							// 1
																							// is
																							// computer
																							// controlled
																							// call
																							// AI
																							// function.
						else {
							v.clearGladiatorPanels();
							v.addGladiatorstoPanels(team1.getGladiators());
							pause = true; // Otherwise loop waits.
						}
						team1gladturn++;
					} else {
						if (team1turn)
							counter++; // Increases counter because team 1 had
										// less gladiators than team 2.
						if (i < team2size) {
							counter++;
							while (team2.getGladiators().get(team2gladturn).getHealth() <= 0)
								team2gladturn++; // Gets the nearest conscious
													// gladiator in team.
							battleTurn(team2.getGladiators().get(team2gladturn));
							team1turn = true;
							if (team2.getComputer())
								this.moveAI(team2.getGladiators().get(team2gladturn)); // If
																								// team
																								// 2
																								// is
																								// computer
																								// controlled
																								// call
																								// AI
																								// function.
							else {
								v.clearGladiatorPanels();
								v.addGladiatorstoPanels(team2.getGladiators());
								pause = true; // Otherwise loop waits.
							}
							team2gladturn++;
						} else {
							counter++; // Increases counter because team 2 had
										// less gladiators than team 1.
							team1turn = true;
						}
					}
				} // pause ends here
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
			}
			if (!stop) {
				if (round < maxRounds - 1) { // If battle isn't over yet
												// increase round.
					round++;
				} else { // Otherwise:
					if (team1size <= 0) { // Checks whether team 1 has conscious
											// members.
						stop = true;
						this.battleEnds(2); // Team 2 wins.
					}
					if (team2size <= 0) { // Checks whether team 1 has conscious
											// members.
						stop = true;
						this.battleEnds(1); // Team 1 wins.
					} else {
						stop = true;
						this.battleEnds(0); // Battle is draw.
					}
				}
			}
		}
	}

	/*
	 * Function used to show the available positions where gladiator can move or
	 * attack.
	 */
	public void battleTurn(Gladiator gl) {
		activeGladiator = gl;
		v.showGladiator(activeGladiator); // Shows gladiator to player.
		Point loc = activeGladiator.getLocation();
		if (loc.getX() > 0) {
			movetable[(int) loc.getX() - 1][(int) loc.getY()] = 20;
		}
		if (loc.getX() < 9) {
			movetable[(int) loc.getX() + 1][(int) loc.getY()] = 20;
		}
		if (loc.getY() > 0) {
			movetable[(int) loc.getX()][(int) loc.getY() - 1] = 20;
		}
		if (loc.getY() < 7) {
			movetable[(int) loc.getX()][(int) loc.getY() + 1] = 20;
		}
		if (loc.getX() > 0 && loc.getY() > 0) {
			movetable[(int) loc.getX() - 1][(int) loc.getY() - 1] = 20;
		}
		if (loc.getX() < 9 && loc.getY() < 7) {
			movetable[(int) loc.getX() + 1][(int) loc.getY() + 1] = 20;
		}
		if (loc.getX() < 9 && loc.getY() > 0) {
			movetable[(int) loc.getX() + 1][(int) loc.getY() - 1] = 20;
		}
		if (loc.getX() > 0 && loc.getY() < 7) {
			movetable[(int) loc.getX() - 1][(int) loc.getY() + 1] = 20;
		}
		drawingarea.paintBattle();
	}

	/*
	 * Two getters that are used by BattlePanel.
	 */
	public int getBattleTableValue(int x, int y) {
		return battletable[x][y];
	}

	public int getMoveTableValue(int x, int y) {
		return movetable[x][y];
	}

	// Function which is called upon ending the battle.
	public void battleEnds(int winner) {
		drawingarea.stop(); // Stops loop in drawingarea.
		v.addText("Battle has ended.");
		// Gives winner and loser squirrels, amount depending on the league.
		int losesquirs = 1000 - team1.getLeague() * 200;
		int winsquirs = 1200 - team1.getLeague() * 200;
		switch (winner) {
		case 0:
			v.addText("Draw: Neither side was victorious.");
			v.addText("Both teams earn " + losesquirs + " squirrels.");
			team1.setSquirrels(team1.getSquirrels() + losesquirs);
			team2.setSquirrels(team2.getSquirrels() + losesquirs);
			team1.setResult(3);
			team2.setResult(3);
			break;
		case 1:
			v.addText(team1.getName() + " is victorious!");
			v.addText(team1.getName() + " earns " + winsquirs + " squirrels while " + team2.getName() + " being losers earn only " + losesquirs + " squirrels.");
			team1.setSquirrels(team1.getSquirrels() + winsquirs);
			team2.setSquirrels(team2.getSquirrels() + losesquirs);
			team1.increaseMatchWins();
			team1.setResult(1);
			team2.setResult(2);
			break;
		case 2:
			v.addText(team2.getName() + " is victorious!");
			v.addText(team2.getName() + " earns " + winsquirs + " squirrels while " + team1.getName() + " being losers earn only " + losesquirs + " squirrels.");
			team1.setSquirrels(team1.getSquirrels() + losesquirs);
			team2.setSquirrels(team2.getSquirrels() + winsquirs);
			team2.increaseMatchWins();
			team1.setResult(2);
			team2.setResult(1);
			break;
		}
		if (!team1.getComputer())
			v.addText(team1.getName() + " paid " + team1.payGladiators() + " squirrels for the upkeep of the gladiators.");
		if (!team2.getComputer())
			v.addText(team2.getName() + " paid " + team2.payGladiators() + " squirrels for the upkeep of the gladiators.");
		team1.resetGladiatorsHealth();
		team2.resetGladiatorsHealth();
		t = null;
		v.removeBattleController(this);
		season.nextBattle(drawingarea, v);
	}

	/*
	 * Function for deciding the outcome of melee attack.
	 */
	public void duelMelee(Gladiator a, Gladiator b, int x, int y) {
		String weapa, weapb;
		MeleeWeaponType weaptypea = null, weaptypeb = null;
		if (a.getMelee() != null) {
			weapa = a.getMelee().getName();
			weaptypea = (MeleeWeaponType) a.getMelee().getWeaponType();
		} else
			weapa = "fist";
		if (b.getMelee() != null) {
			weapb = b.getMelee().getName();
			weaptypeb = (MeleeWeaponType) b.getMelee().getWeaponType();
		} else
			weapb = "fist";
		if (a.getRace().equals("Beholder") || a.getRace().equals("Beholder_Hero"))
			v.addText(a.getName() + " attacks with evil eye while " + b.getName() + " defends with " + weapb + ".");
		else
			v.addText(a.getName() + " attacks with " + weapa + " while " + b.getName() + " defends with " + weapb + ".");
		int attack = 0, defend = 0;
		if (weapa.equals("fist"))
			attack = a.getAttack();
		else {
			if (weaptypea.equals(MeleeWeaponType.HAMMER))
				attack = a.getHammerskillAtt();
			if (weaptypea.equals(MeleeWeaponType.SWORD))
				attack = a.getSwordskillAtt();
			if (weaptypea.equals(MeleeWeaponType.AXE))
				attack = a.getAxeskillAtt();
			if (weaptypea.equals(MeleeWeaponType.SPEAR))
				attack = a.getSpearskillAtt();
		}
		if (weapb.equals("fist"))
			defend = b.getDefense();
		else {
			if (weaptypeb.equals(MeleeWeaponType.HAMMER))
				defend = b.getHammerskillDef();
			if (weaptypeb.equals(MeleeWeaponType.SWORD))
				defend = b.getSwordskillDef();
			if (weaptypeb.equals(MeleeWeaponType.AXE))
				defend = b.getAxeskillDef();
			if (weaptypeb.equals(MeleeWeaponType.SPEAR))
				defend = b.getSpearskillDef();
		}
		int hit = r.nextInt(attack + 1) - r.nextInt(defend + 1);
		if (hit < 0) {
			v.addText(a.getName() + " didn't hit.");
			if (r.nextInt(4) == 1) {
				boolean skillincrease = b.increaseMeleeDefendSkill();
				if (skillincrease)
					v.addText(b.getName() + " defense skill developed.");
			}
			drawingarea.hitmiss(x, y, true);
		} else {
			int damage = r.nextInt(a.getStrength());
			if (!weapa.equals("fist")) {
				damage = damage + a.getMelee().battleDamage();
			}
			if (b.getArmor() != null)
				damage = damage - b.getArmor().getArmor();
			damage = damage - b.getNaturalarmor();
			if (damage < 0)
				damage = 0;
			if (damage == 0)
				drawingarea.hitmiss(x, y, true);
			else
				drawingarea.hitmiss(x, y, false);
			b.setHealth(b.getHealth() - damage);
			boolean ko = false;
			if (b.getHealth() <= 0)
				ko = true;
			v.addText(a.getName() + " hit doing " + damage + " to " + b.getName() + ".");
			if (ko) {
				v.addText(b.getName() + " is knocked out.");
				a.setKnockdowns(1);
				if (battletable[(int) b.getLocation().getX()][(int) b.getLocation().getY()] < 0)
					team2size--;
				else
					team1size--;
			}
			if (r.nextInt(3) == 1) {
				boolean skillincrease = a.increaseMeleeAttackSkill();
				if (skillincrease)
					v.addText(a.getName() + " attack skill developed.");
			}
		}
	}

	/*
	 * Function for deciding outcome of ranged attack.
	 */
	public void duelRanged(Gladiator a, Gladiator b, int x, int y) {
		String weapa = "";
		RangedWeaponType weaptypea = null;
		if (a.getRanged() != null) {
			weapa = a.getRanged().getName();
			weaptypea = (RangedWeaponType) a.getRanged().getWeaponType();
		}
		if (a.getRanged() != null)
			v.addText(a.getName() + " shoots " + b.getName() + " with " + weapa + ".");
		else
			v.addText(a.getName() + " shoots beholder ray at " + b.getName() + ".");
		int attack = 0, defend = 0;
		if (weaptypea.equals(RangedWeaponType.BOW))
			attack = a.getBowskill();
		if (weaptypea.equals(RangedWeaponType.CROSSBOW))
			attack = a.getCrossbowskill();
		if (a.getRace().equals("Beholder") || a.getRace().equals("Beholder_Hero"))
			attack = 10;
		defend = b.getEvasion();
		int hit = r.nextInt(attack + 1) - r.nextInt(defend + 1);
		if (hit < 0) {
			v.addText(a.getName() + " misses.");
			drawingarea.hitmiss(x, y, true);
			if (r.nextInt(4) == 1 && b.getEvasion() < 99) {
				b.setEvasion(b.getEvasion() + 1);
				v.addText(b.getName() + " evasion skill developed.");
			}
		} else {
			int damage = 0;
			if (a.getRanged() == null)
				damage = (int) (r.nextInt(a.getStrength()) / 2);
			else
				damage = a.getRanged().battleDamage();
			if (b.getArmor() != null)
				damage = damage - b.getArmor().getArmor();
			damage = damage - b.getNaturalarmor();
			if (damage < 0)
				damage = 0;
			if (damage == 0)
				drawingarea.hitmiss(x, y, true);
			else
				drawingarea.hitmiss(x, y, false);
			b.setHealth(b.getHealth() - damage);
			boolean ko = false;
			if (b.getHealth() <= 0)
				ko = true;
			if (a.getRace().equals("Beholder") || a.getRace().equals("Beholder_Hero"))
				v.addText(a.getName() + " rays doing " + damage + " to " + b.getName() + ".");
			else
				v.addText(a.getName() + " hit doing " + damage + " to " + b.getName() + ".");
			if (ko) {
				v.addText(b.getName() + " is knocked out.");
				a.setKnockdowns(1);
				if (battletable[(int) b.getLocation().getX()][(int) b.getLocation().getY()] < 0)
					team2size--;
				else
					team1size--;
			}
			if (r.nextInt(3) == 1) {
				boolean skillincrease = a.increaseRangedSkill();
				if (skillincrease)
					v.addText(a.getName() + " ranged skill developed.");
			}
		}
	}

	/*
	 * Function for deciding outcome of healing spell.
	 */
	public void healSpell(Gladiator a, Gladiator b, int x, int y) {
		Spell sp = null;
		if (a.getSpell1() != null)
			if (!a.getSpell1().getDamageSpell() && a.getMana() >= a.getSpell1().getManaCost())
				sp = a.getSpell1();
		if (a.getSpell2() != null)
			if (!a.getSpell2().getDamageSpell() && a.getMana() >= a.getSpell1().getManaCost()) {
				if (sp == null)
					sp = a.getSpell2();
				else if (a.getSpell2().getManaCost() > a.getSpell1().getManaCost())
					sp = a.getSpell2();
			}
		String spell = sp.getName();
		v.addText(a.getName() + " casts " + spell + " on " + b.getName() + ".");
		int success = a.getMaxmana();
		int hit = r.nextInt(success);
		drawingarea.hitmiss(x, y, true);
		if (hit < 5)
			v.addText(a.getName() + " fails.");
		else {
			int heal = sp.battleDamage();
			b.setHealth(b.getHealth() + heal);
			if (b.getHealth() >= b.getMaxhealth())
				b.setHealth(b.getMaxhealth());
			v.addText(a.getName() + " heals " + heal + ".");
		}
		a.setMana(a.getMana() - sp.getManaCost());
	}

	/*
	 * Function for deciding outcome of spell.
	 */
	public void duelSpell(Gladiator a, Gladiator b, int x, int y) {
		Spell sp = null;
		if (a.getSpell1() != null)
			if (a.getSpell1().getDamageSpell() && a.getMana() >= a.getSpell1().getManaCost())
				sp = a.getSpell1();
		if (a.getSpell2() != null)
			if (a.getSpell2().getDamageSpell() && a.getMana() >= a.getSpell1().getManaCost()) {
				if (sp == null)
					sp = a.getSpell2();
				else if (a.getSpell2().getManaCost() > a.getSpell1().getManaCost())
					sp = a.getSpell2();
			}
		String spell = sp.getName();
		v.addText(a.getName() + " casts " + spell + " on " + b.getName() + ".");
		int attack = a.getMaxmana() + 10, defend = b.getResistance();
		int hit = r.nextInt(attack + 1) - r.nextInt(defend + 1);
		if (hit < 0) {
			v.addText(b.getName() + " resists the spell.");
			drawingarea.hitmiss(x, y, true);
			if (r.nextInt(4) == 1 && b.getResistance() < 99) {
				b.setResistance(b.getResistance() + 1);
				v.addText(b.getName() + " resistance skill developed.");
			}
		} else {
			int damage = sp.battleDamage();
			b.setHealth(b.getHealth() - damage);
			boolean ko = false;
			if (b.getHealth() <= 0)
				ko = true;
			drawingarea.hitmiss(x, y, false);
			v.addText(a.getName() + " hit doing " + damage + " to " + b.getName() + ".");
			if (ko) {
				a.setKnockdowns(1);
				v.addText(b.getName() + " is knocked out.");
				if (battletable[(int) b.getLocation().getX()][(int) b.getLocation().getY()] < 0)
					team2size--;
				else
					team1size--;
			}
		}
		a.setMana(a.getMana() - sp.getManaCost());
	}

	/*
	 * Function for AI.
	 */
	public void moveAI(Gladiator gl) {
		boolean done = false;
		activeGladiator = gl;
		int i = (int) gl.getLocation().getX(), j = (int) gl.getLocation().getY();
		for (int x = 0; x < 10 && !done; x++) {
			for (int y = 0; y < 8 && !done; y++) {
				if (movetable[x][y] == 20) {
					if ((battletable[x][y] > 0 && battletable[i][j] < 0) || (battletable[x][y] < 0 && battletable[i][j] > 0)) {
						boolean ko = false;
						int other = battletable[x][y];
						if (other > 0) {
							if (team1.getGladiators().get(other - 1).getHealth() <= 0)
								ko = true;
						} else if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
							ko = true;
						if (!ko) {
							if (other > 0)
								this.duelMelee(gl, team1.getGladiators().get(other - 1), x, y);
							else
								this.duelMelee(gl, team2.getGladiators().get(-other - 1), x, y);
							done = true;
						}
					}
				}
			}
		}
		if (!done && (gl.getRanged() != null) || (gl.getRace().equals("Beholder") || gl.getRace().equals("Beholder_Hero"))) {
			for (int x = 0; x < 10 && !done; x++) {
				for (int y = 0; y < 8 && !done; y++) {
					if ((battletable[x][y] > 0 && battletable[i][j] < 0) || (battletable[x][y] < 0 && battletable[i][j] > 0)) {
						boolean ko = false;
						int other = battletable[x][y];
						if (other > 0) {
							if (team1.getGladiators().get(other - 1).getHealth() <= 0)
								ko = true;
						} else if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
							ko = true;
						if (!ko) {
							if (other > 0)
								this.duelRanged(gl, team1.getGladiators().get(other - 1), x, y);
							else
								this.duelRanged(gl, team2.getGladiators().get(-other - 1), x, y);
							done = true;
						}
					}
				}
			}
		}
		if (!done && (gl.getSpell1() != null || gl.getSpell2() != null)) {
			for (int x = 0; x < 10 && !done; x++) {
				for (int y = 0; y < 8 && !done; y++) {
					if ((battletable[x][y] > 0 || battletable[x][y] < 0)) {
						done = this.checkSpells(x, y, gl.getLocation());
					}
				}
			}
		}
		if (!done) {// find closest healthy enemy gladiator and move towards it.
			int closest[] = { 12, 12 };
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 8; y++) {
					if ((battletable[x][y] > 0 && battletable[i][j] < 0) || (battletable[x][y] < 0 && battletable[i][j] > 0)) {
						boolean ko = false;
						int other = battletable[x][y];
						if (other > 0) {
							if (team1.getGladiators().get(other - 1).getHealth() <= 0)
								ko = true;
						} else if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
							ko = true;
						if (!ko) {
							int howclosex, howclosey;
							howclosex = x - i;
							howclosey = y - j;
							if ((Math.abs(howclosex) + Math.abs(howclosey)) <= (Math.abs(closest[0]) + Math.abs(closest[1]))) {
								closest[0] = howclosex;
								closest[1] = howclosey;
							}
						}
					}
				}
			}
			if (closest[0] < 0)
				closest[0] = closest[0] - closest[0] - 1;
			else if (closest[0] > 0)
				closest[0] = closest[0] - closest[0] + 1;
			if (closest[1] < 0)
				closest[1] = closest[1] - closest[1] - 1;
			else if (closest[1] > 0)
				closest[1] = closest[1] - closest[1] + 1;
			// making the move:

			if (battletable[i + closest[0]][j + closest[1]] == 0
					|| ((battletable[i + closest[0]][j + closest[1]] < 0 && team2.getGladiators().get(-battletable[i + closest[0]][j + closest[1]] - 1).getHealth() <= 0) || (battletable[i
							+ closest[0]][j + closest[1]] > 0 && team1.getGladiators().get(battletable[i + closest[0]][j + closest[1]] - 1).getHealth() <= 0))) {
				int other = battletable[i + closest[0]][j + closest[1]];
				battletable[i + closest[0]][j + closest[1]] = battletable[i][j];
				battletable[i][j] = other;
				if (other < 0)
					team2.getGladiators().get(-other - 1).setLocation(new Point(i, j));
				else if (other > 0)
					team1.getGladiators().get(other - 1).setLocation(new Point(i, j));
				gl.setLocation(new Point(i + closest[0], j + closest[1]));
			} else {
				if (battletable[i + closest[0]][j] == 0
						|| ((battletable[i + closest[0]][j] < 0 && team2.getGladiators().get(-battletable[i + closest[0]][j] - 1).getHealth() <= 0) || (battletable[i + closest[0]][j] > 0 && team1
								.getGladiators().get(battletable[i + closest[0]][j] - 1).getHealth() <= 0))) {
					int other = battletable[i + closest[0]][j];
					battletable[i + closest[0]][j] = battletable[i][j];
					battletable[i][j] = other;
					if (other < 0)
						team2.getGladiators().get(-other - 1).setLocation(new Point(i, j));
					else if (other > 0)
						team1.getGladiators().get(other - 1).setLocation(new Point(i, j));
					gl.setLocation(new Point(i + closest[0], j));
				} else {
					if (closest[0] != 0) {
						boolean notdone = true;
						if (j > 0) {
							if (battletable[i + closest[0]][j - 1] == 0
									|| ((battletable[i + closest[0]][j - 1] < 0 && team2.getGladiators().get(-battletable[i + closest[0]][j - 1] - 1).getHealth() <= 0) || (battletable[i + closest[0]][j - 1] > 0 && team1
											.getGladiators().get(battletable[i + closest[0]][j - 1] - 1).getHealth() <= 0))) {
								int other = battletable[i + closest[0]][j - 1];
								battletable[i + closest[0]][j - 1] = battletable[i][j];
								battletable[i][j] = other;
								if (other < 0)
									team2.getGladiators().get(-other - 1).setLocation(new Point(i, j));
								else if (other > 0)
									team1.getGladiators().get(other - 1).setLocation(new Point(i, j));
								gl.setLocation(new Point(i + closest[0], j - 1));
								notdone = false;
							}
						}
						if (j < 7 && notdone) {
							if (battletable[i + closest[0]][j + 1] == 0
									|| ((battletable[i + closest[0]][j + 1] < 0 && team2.getGladiators().get(-battletable[i + closest[0]][j + 1] - 1).getHealth() <= 0) || (battletable[i + closest[0]][j + 1] > 0 && team1
											.getGladiators().get(battletable[i + closest[0]][j + 1] - 1).getHealth() <= 0))) {
								int other = battletable[i + closest[0]][j + 1];
								battletable[i + closest[0]][j + 1] = battletable[i][j];
								battletable[i][j] = other;
								if (other < 0)
									team2.getGladiators().get(-other - 1).setLocation(new Point(i, j));
								else if (other > 0)
									team1.getGladiators().get(other - 1).setLocation(new Point(i, j));
								gl.setLocation(new Point(i + closest[0], j + 1));
							}
						}
					} else {
						boolean notdone = true;
						if (i > 0) {
							if (battletable[i - 1][j + closest[1]] == 0
									|| ((battletable[i - 1][j + closest[1]] < 0 && team2.getGladiators().get(-battletable[i - 1][j + closest[1]] - 1).getHealth() <= 0) || (battletable[i - 1][j
											+ closest[1]] > 0 && team1.getGladiators().get(battletable[i - 1][j + closest[1]] - 1).getHealth() <= 0))) {
								int other = battletable[i - 1][j + closest[1]];
								battletable[i - 1][j + closest[1]] = battletable[i][j];
								battletable[i][j] = other;
								if (other < 0)
									team2.getGladiators().get(-other - 1).setLocation(new Point(i, j));
								else if (other > 0)
									team1.getGladiators().get(other - 1).setLocation(new Point(i, j));
								gl.setLocation(new Point(i - 1, j + closest[1]));
								notdone = false;
							}
						}
						if (i < 9 && notdone) {
							if (battletable[i + 1][j + closest[1]] == 0
									|| ((battletable[i + 1][j + closest[1]] < 0 && team2.getGladiators().get(-battletable[i + 1][j + closest[1]] - 1).getHealth() <= 0) || (battletable[i + 1][j
											+ closest[1]] > 0 && team1.getGladiators().get(battletable[i + 1][j + closest[1]] - 1).getHealth() <= 0))) {
								int other = battletable[i + 1][j + closest[1]];
								battletable[i + 1][j + closest[1]] = battletable[i][j];
								battletable[i][j] = other;
								if (other < 0)
									team2.getGladiators().get(-other - 1).setLocation(new Point(i, j));
								else if (other > 0)
									team1.getGladiators().get(other - 1).setLocation(new Point(i, j));
								gl.setLocation(new Point(i + 1, j + closest[1]));
								notdone = false;
							}
						}
						if (notdone) {
							v.addText(activeGladiator.getName() + " waits for a better opportunity.");
							/*
							 * System.out.println("Here darn."); int other =
							 * battletable[i+closest[0]][j];
							 * battletable[i+closest[0]][j]=battletable[i][j];
							 * battletable[i][j] = other;
							 * if(other<0)team2.getGladiators
							 * ().elementAt(-other-1).setLocation(new Point(i,
							 * j)); else
							 * if(other>0)team1.getGladiators().elementAt
							 * (other-1).setLocation(new Point(i, j));
							 * gl.setLocation(new Point(i+closest[0], j));
							 */
						}
					}
				}
			}
		}
		this.resetMoveTable();
		pause = false;
	}

	/*
	 * Implemented function for listening mouse-events from BattlePanel.
	 */
	public void mousePressed(MouseEvent evt) {
		Point p = evt.getPoint();
		int x = (int) Math.floor(p.getX() / 40);
		int y = (int) (p.getY() / 40);
		Point loc = activeGladiator.getLocation();
		if (!(x == (int) loc.getX() && y == (int) loc.getY())) {
			if (x < 10 && y < 8) {
				if (evt.getButton() == 3) {
					if (battletable[x][y] != 0) {
						int val = battletable[x][y];
						if (val < 0)
							v.showGladiator(team2.getGladiators().get(-val - 1));
						else
							v.showGladiator(team1.getGladiators().get(val - 1));
					}
				} else {
					if (movetable[x][y] == 20) {
						if (battletable[x][y] == 0) {
							battletable[x][y] = battletable[(int) loc.getX()][(int) loc.getY()];
							battletable[(int) loc.getX()][(int) loc.getY()] = 0;
							activeGladiator.setLocation(new Point(x, y));
						} else {
							if ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0)) {
								int other = battletable[x][y];
								battletable[x][y] = battletable[(int) loc.getX()][(int) loc.getY()];
								battletable[(int) loc.getX()][(int) loc.getY()] = other;
								if (other < 0)
									team2.getGladiators().get(-other - 1).setLocation(loc);
								else
									team1.getGladiators().get(other - 1).setLocation(loc);
								activeGladiator.setLocation(new Point(x, y));
							} else {
								if ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0)) {
									int other = battletable[x][y];
									boolean justmove = false;
									if (other < 0) {
										if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
											justmove = true;
									} else if (team1.getGladiators().get(other - 1).getHealth() <= 0)
										justmove = true;
									if (justmove) {
										battletable[x][y] = battletable[(int) loc.getX()][(int) loc.getY()];
										battletable[(int) loc.getX()][(int) loc.getY()] = other;
										if (other < 0)
											team2.getGladiators().get(-other - 1).setLocation(loc);
										else
											team1.getGladiators().get(other - 1).setLocation(loc);
										activeGladiator.setLocation(new Point(x, y));
									} else {
										if (other < 0)
											this.duelMelee(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
										else
											this.duelMelee(activeGladiator, team1.getGladiators().get(other - 1), x, y);
									}
								}
							}
						}
						this.resetMoveTable();
						pause = false;
					} else if (!(evt.getButton() == 2)
							&& (((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0)) && (activeGladiator
									.getRanged() != null || activeGladiator.getRace().equals("Beholder") || activeGladiator.getRace().equals("Beholder_Hero")))) {
						int other = battletable[x][y];
						boolean donothing = false;
						if (other < 0) {
							if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
								donothing = true;
						} else if (team1.getGladiators().get(other - 1).getHealth() <= 0)
							donothing = true;
						if (!donothing) {
							if (other < 0)
								this.duelRanged(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
							else
								this.duelRanged(activeGladiator, team1.getGladiators().get(other - 1), x, y);
							this.resetMoveTable();
							pause = false;
						}
					} else {
						this.checkSpells(x, y, loc);
					}
				}
			}
		} else {
			if (evt.getButton() == 3) {
				v.showGladiator(activeGladiator);
			} else {
				v.addText(activeGladiator.getName() + " waits for a better opportunity.");
				this.resetMoveTable();
				pause = false;
			}

		}
	}

	public void mouseExited(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
	}

	/*
	 * Function for checking possible spells and the validity of player
	 * initiated action.
	 */
	public boolean checkSpells(int x, int y, Point loc) {
		if (activeGladiator.getSpell1() != null && activeGladiator.getSpell2() != null) {
			if (((activeGladiator.getSpell1().getDamageSpell() && activeGladiator.getSpell1().getManaCost() <= activeGladiator.getMana()) || (activeGladiator.getSpell2().getDamageSpell() && activeGladiator
					.getSpell2().getManaCost() <= activeGladiator.getMana()))
					&& ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0))) {
				int other = battletable[x][y];
				boolean donothing = false;
				if (other < 0) {
					if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
						donothing = true;
				} else if (team1.getGladiators().get(other - 1).getHealth() <= 0)
					donothing = true;
				if (!donothing) {
					if (other < 0)
						this.duelSpell(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
					else
						this.duelSpell(activeGladiator, team1.getGladiators().get(other - 1), x, y);
					this.resetMoveTable();
					pause = false;
					return true;
				}
			} else {
				if (((!activeGladiator.getSpell1().getDamageSpell() && activeGladiator.getSpell1().getManaCost() <= activeGladiator.getMana()) || (!activeGladiator.getSpell2().getDamageSpell() && activeGladiator
						.getSpell2().getManaCost() <= activeGladiator.getMana()))
						&& ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0))) {
					int other = battletable[x][y];
					boolean donothing = false;
					if (other < 0) {
						if (team2.getGladiators().get(-other - 1).getHealth() <= 0 || team2.getGladiators().get(-other - 1).getHealth() == team2.getGladiators().get(-other - 1).getMaxhealth())
							donothing = true;
					} else if (team1.getGladiators().get(other - 1).getHealth() <= 0 || team1.getGladiators().get(-other - 1).getHealth() == team1.getGladiators().get(-other - 1).getMaxhealth())
						donothing = true;
					if (!donothing) {
						if (other < 0)
							this.healSpell(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
						else
							this.healSpell(activeGladiator, team1.getGladiators().get(other - 1), x, y);
						this.resetMoveTable();
						pause = false;
						return true;
					}
				}
			}
		} else if (activeGladiator.getSpell1() != null) {
			if (((activeGladiator.getSpell1().getDamageSpell() && activeGladiator.getSpell1().getManaCost() <= activeGladiator.getMana()))
					&& ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0))) {
				int other = battletable[x][y];
				boolean donothing = false;
				if (other < 0) {
					if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
						donothing = true;
				} else if (team1.getGladiators().get(other - 1).getHealth() <= 0)
					donothing = true;
				if (!donothing) {
					if (other < 0)
						this.duelSpell(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
					else
						this.duelSpell(activeGladiator, team1.getGladiators().get(other - 1), x, y);
					this.resetMoveTable();
					pause = false;
					return true;
				}
			} else {
				if (((!activeGladiator.getSpell1().getDamageSpell() && activeGladiator.getSpell1().getManaCost() <= activeGladiator.getMana()))
						&& ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0))) {
					int other = battletable[x][y];
					boolean donothing = false;
					if (other < 0) {
						if (team2.getGladiators().get(-other - 1).getHealth() <= 0 || team2.getGladiators().get(-other - 1).getHealth() == team2.getGladiators().get(-other - 1).getMaxhealth())
							donothing = true;
					} else if (team1.getGladiators().get(other - 1).getHealth() <= 0 || team1.getGladiators().get(other - 1).getHealth() == team1.getGladiators().get(other - 1).getMaxhealth())
						donothing = true;
					if (!donothing) {
						if (other < 0)
							this.healSpell(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
						else
							this.healSpell(activeGladiator, team1.getGladiators().get(other - 1), x, y);
						this.resetMoveTable();
						pause = false;
						return true;
					}
				}
			}
		} else if (activeGladiator.getSpell2() != null) {
			if (((activeGladiator.getSpell1().getDamageSpell() && activeGladiator.getSpell1().getManaCost() <= activeGladiator.getMana()) || (activeGladiator.getSpell2().getDamageSpell() && activeGladiator
					.getSpell2().getManaCost() <= activeGladiator.getMana()))
					&& ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0))) {
				int other = battletable[x][y];
				boolean donothing = false;
				if (other < 0) {
					if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
						donothing = true;
				} else if (team1.getGladiators().get(other - 1).getHealth() <= 0)
					donothing = true;
				if (!donothing) {
					if (other < 0)
						this.duelSpell(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
					else
						this.duelSpell(activeGladiator, team1.getGladiators().get(other - 1), x, y);
					this.resetMoveTable();
					pause = false;
					return true;
				}
			} else {
				if ((!activeGladiator.getSpell2().getDamageSpell() && activeGladiator.getSpell2().getManaCost() <= activeGladiator.getMana())
						&& ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0))) {
					int other = battletable[x][y];
					boolean donothing = false;
					if (other < 0) {
						if (team2.getGladiators().get(-other - 1).getHealth() <= 0 || team2.getGladiators().get(-other - 1).getHealth() == team2.getGladiators().get(-other - 1).getMaxhealth())
							donothing = true;
					} else if (team1.getGladiators().get(other - 1).getHealth() <= 0 || team1.getGladiators().get(-other - 1).getHealth() == team1.getGladiators().get(-other - 1).getMaxhealth())
						donothing = true;
					if (!donothing) {
						if (other < 0)
							this.healSpell(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
						else
							this.healSpell(activeGladiator, team1.getGladiators().get(other - 1), x, y);
						this.resetMoveTable();
						pause = false;
						return true;
					}
				}
			}

		}
		return false;
	}

	public void keyPressed(KeyEvent ke) {
		Point loc = activeGladiator.getLocation();
		int x = (int) loc.getX();
		int y = (int) loc.getY();
		int keycode = ke.getKeyCode();
		if (keycode == KeyEvent.VK_NUMPAD1) {
			x--;
			y++;
		}
		if (keycode == KeyEvent.VK_NUMPAD2) {
			y++;
		}
		if (keycode == KeyEvent.VK_NUMPAD3) {
			x++;
			y++;
		}
		if (keycode == KeyEvent.VK_NUMPAD4) {
			x--;
		}
		if (keycode == KeyEvent.VK_NUMPAD6) {
			x++;
		}
		if (keycode == KeyEvent.VK_NUMPAD7) {
			x--;
			y--;
		}
		if (keycode == KeyEvent.VK_NUMPAD8) {
			y--;
		}
		if (keycode == KeyEvent.VK_NUMPAD9) {
			x++;
			y--;
		}
		if (!(x == (int) loc.getX() && y == (int) loc.getY())) {
			if (x < 10 && y < 8 && x >= 0 && y >= 0) {
				if (movetable[x][y] == 20) {
					if (battletable[x][y] == 0) {
						battletable[x][y] = battletable[(int) loc.getX()][(int) loc.getY()];
						battletable[(int) loc.getX()][(int) loc.getY()] = 0;
						activeGladiator.setLocation(new Point(x, y));
					} else {
						if ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0)) {
							int other = battletable[x][y];
							battletable[x][y] = battletable[(int) loc.getX()][(int) loc.getY()];
							battletable[(int) loc.getX()][(int) loc.getY()] = other;
							if (other < 0)
								team2.getGladiators().get(-other - 1).setLocation(loc);
							else
								team1.getGladiators().get(other - 1).setLocation(loc);
							activeGladiator.setLocation(new Point(x, y));
						} else {
							if ((battletable[x][y] > 0 && battletable[(int) loc.getX()][(int) loc.getY()] < 0) || (battletable[x][y] < 0 && battletable[(int) loc.getX()][(int) loc.getY()] > 0)) {
								int other = battletable[x][y];
								boolean justmove = false;
								if (other < 0) {
									if (team2.getGladiators().get(-other - 1).getHealth() <= 0)
										justmove = true;
								} else if (team1.getGladiators().get(other - 1).getHealth() <= 0)
									justmove = true;
								if (justmove) {
									battletable[x][y] = battletable[(int) loc.getX()][(int) loc.getY()];
									battletable[(int) loc.getX()][(int) loc.getY()] = other;
									if (other < 0)
										team2.getGladiators().get(-other - 1).setLocation(loc);
									else
										team1.getGladiators().get(other - 1).setLocation(loc);
									activeGladiator.setLocation(new Point(x, y));
								} else {
									if (other < 0)
										this.duelMelee(activeGladiator, team2.getGladiators().get(-other - 1), x, y);
									else
										this.duelMelee(activeGladiator, team1.getGladiators().get(other - 1), x, y);
								}
							}
						}
					}
					this.resetMoveTable();
					pause = false;
				}
			}
		} else {
			if (keycode == KeyEvent.VK_NUMPAD5) {
				v.addText(activeGladiator.getName() + " waits for a better opportunity.");
				this.resetMoveTable();
				pause = false;
			}
		}
	}

	public void keyTyped(KeyEvent ke) {
		ke.consume();
	}

	public void keyReleased(KeyEvent ke) {
		ke.consume();
	}
}
