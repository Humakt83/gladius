package com.ukkosnetti.gladius;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.ukkosnetti.gladius.concept.Season;
import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gui.BattlePanel;
import com.ukkosnetti.gladius.gui.View;

@RunWith(MockitoJUnitRunner.class)
public class BattleTest {

	private List<Team> teams;

	@Mock
	private BattlePanel panel;

	@Mock
	private Season season;

	@Mock
	private View view;

	@Before
	public void init() {
		teams = Team.getTeams();
	}

	@Test
	public void teamsBattleAgainstEachOther() throws Exception {
		Team team1 = teams.get(0);
		Team team2 = teams.get(1);
		new Battle(team1, team2, season, panel, view);
		verify(view, timeout(1000)).removeBattleController(any(Battle.class));

		assertTrue(team1.getMatchWins() > 0 || team2.getMatchWins() > 0);
	}

	@Test
	public void teamFromLeagueOneShouldWinMoreAgainstTeamFromLeagueFour() throws Exception {
		Team leagueOneTeam = Iterables.find(teams, new Predicate<Team>() {

			@Override
			public boolean apply(Team input) {
				return input.getLeague() == 1;
			}

		});
		Team leagueFourTeam = Iterables.find(teams, new Predicate<Team>() {

			@Override
			public boolean apply(Team input) {
				return input.getLeague() == 4;
			}
			
		});
		new Battle(leagueOneTeam, leagueFourTeam, season, panel, view);
		verify(view, timeout(1000)).removeBattleController(any(Battle.class));
		assertEquals(1, leagueOneTeam.getMatchWins().intValue());
	}

}
