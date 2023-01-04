package at.fhtw.mtcg.app.dal.repository;

import at.fhtw.mtcg.app.model.Scoreboard;
import at.fhtw.mtcg.app.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardRepositoryTest {
    @Test
    void testScoreboardSortedByElo() throws Exception {
        User ana = new User();
        ana.setUsername("ana");
        ana.setPassword("123");

        User betty = new User();
        betty.setUsername("betty");
        betty.setPassword("123");

        User joe = new User();
        joe.setUsername("joe");
        joe.setPassword("123");

        UserRepository userRepository = new UserRepository();
        userRepository.registerUser(ana);
        userRepository.registerUser(betty);
        userRepository.registerUser(joe);


        userRepository.updateStats("ana", true);
        userRepository.updateStats("ana", true);
        userRepository.updateStats("joe", true);
        userRepository.updateStats("betty", false);

        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        List<Scoreboard> scoreboardList = scoreboardRepository.getScoreboard();

        assertEquals(scoreboardList.get(0).getUsername(), "ana");
    }
}