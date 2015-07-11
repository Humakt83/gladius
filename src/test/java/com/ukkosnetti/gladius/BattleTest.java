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
		battle(team1, team2);
		assertTrue(team1.getMatchWins() > 0 || team2.getMatchWins() > 0);
	}

	@Test
	public void teamFromLeagueOneShouldWinMoreAgainstTeamFromLeagueFour() throws Exception {
		Team leagueOneTeam = getTeamFromLeague(1);
		Team leagueFourTeam = getTeamFromLeague(4);
		battle(leagueOneTeam, leagueFourTeam);
		assertEquals(1, leagueOneTeam.getMatchWins().intValue());
	}

	@Test
	public void teamFromLeagueOneShouldBeMoreLikelyToWinAgainstTeamFromLeagueTwo() throws Exception {
		Team leagueOneTeam = getTeamFromLeague(1);
		Team leagueTwoTeam = getTeamFromLeague(2);
		for (int i = 0; i < 10; i++) {
			battle(leagueOneTeam, leagueTwoTeam);
		}
		assertTrue(leagueOneTeam.getMatchWins() > leagueTwoTeam.getMatchWins());
	}

	@Test
	public void teamFromLeagueTwoShouldBeMoreLikelyToWinAgainstTeamFromLeagueThree() throws Exception {
		Team leagueThreeTeam = getTeamFromLeague(3);
		Team leagueTwoTeam = getTeamFromLeague(2);
		for (int i = 0; i < 10; i++) {
			battle(leagueThreeTeam, leagueTwoTeam);
		}
		assertTrue(leagueThreeTeam.getMatchWins() < leagueTwoTeam.getMatchWins());
	}

	@Test
	public void teamFromLeagueThreeShouldBeMoreLikelyToWinAgainstTeamFromLeagueFour() throws Exception {
		Team leagueThreeTeam = getTeamFromLeague(3);
		Team leagueFourTeam = getTeamFromLeague(4);
		for (int i = 0; i < 10; i++) {
			battle(leagueThreeTeam, leagueFourTeam);
		}
		assertTrue(leagueThreeTeam.getMatchWins() > leagueFourTeam.getMatchWins());
	}

	private void battle(Team team1, Team team2) {
		new Battle(team1, team2, season, panel, view);
		verify(view, timeout(1000)).removeBattleController(any(Battle.class));
	}

	private Team getTeamFromLeague(final int league) {
		return Iterables.find(teams, new Predicate<Team>() {

			@Override
			public boolean apply(Team input) {
				return input.getLeague() == league;
			}

		});
	}

}
