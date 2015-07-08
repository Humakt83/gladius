package com.ukkosnetti.gladius.concept;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TeamTest {

	@Test
	public void generates16Teams() {
		assertEquals(16, Team.getTeams().size());
	}

	@Test
	public void AITeamsHaveAmountOfMembersAccordingToTheirLeague() {
		Team.getTeams().forEach(this::assertAmountOfTeamMembers);
	}

	private void assertAmountOfTeamMembers(Team team) {
		switch (team.getLeague()) {
		case 1:
			assertEquals("League one should have six members", 6, team.getGladiators().size());
			break;
		case 2:
			assertEquals("League two should have five members", 5, team.getGladiators().size());
			break;
		case 3:
		case 4:
			assertEquals("League four and three should have four members", 4, team.getGladiators().size());
			break;
		default:
			fail("Unknown league");
		}
	}

}
