package at.fhtw.mtcg.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    @Test
    void testScoreboardEntry() throws Exception {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.setUsername("ana");
        scoreboard.setWins(4);
        scoreboard.setLosses(1);
        scoreboard.setElo(155.0F);

        assertEquals("ana", scoreboard.getUsername());
        assertEquals(4, scoreboard.getWins());
        assertEquals(1, scoreboard.getLosses());
        assertEquals(155.0F, scoreboard.getElo());
    }
}