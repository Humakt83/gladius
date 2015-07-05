package com.ukkosnetti.gladius.ai;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.GladiatorGenerator;

public class AIEquipperTest {

	private AIEquipper equipper = new AIEquipper();

	private Team team;

	@Before
	public void initTeam() {
		team = new Team("test", 3, 1000, true);
		team.setMembers(GladiatorGenerator.getInstance().generateRandomGladiators(6));
	}

	@Test
	public void gladiatorsHaveArmor() {
		equipper.equipTeam(team);
		team.getGladiators().forEach(gladiator -> assertNotNull(gladiator.getArmor()));
	}

	@Test
	public void gladiatorsHaveWeapon() {
		equipper.equipTeam(team);
		team.getGladiators().forEach(gladiator -> assertNotNull(gladiator.getMelee()));
	}
}
