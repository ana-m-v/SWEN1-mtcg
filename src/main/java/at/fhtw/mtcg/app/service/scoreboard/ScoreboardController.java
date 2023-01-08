package at.fhtw.mtcg.app.service.scoreboard;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.ScoreboardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.app.model.Scoreboard;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Response;

import java.util.List;

public class ScoreboardController extends Controller {

    private ScoreboardRepository scoreboardRepository;
    private UserRepository userRepository;
    public ScoreboardController(ScoreboardRepository scoreboardRepository, UserRepository userRepository) {
        this.scoreboardRepository = scoreboardRepository;
        this.userRepository = userRepository;
    }

    public Response getScoreboard(String token) {

        try {
            if(token.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No user (token) found\" }"
                );
            }
            else {
                String username = this.userRepository.tokenToUsername(token);
                List<Scoreboard> scoreboardData = this.scoreboardRepository.getScoreboard();
                String scoreboardDataJSON = this.getObjectMapper().writeValueAsString(scoreboardData);

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        scoreboardDataJSON
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }
}
