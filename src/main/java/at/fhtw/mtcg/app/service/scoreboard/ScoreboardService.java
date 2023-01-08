package at.fhtw.mtcg.app.service.scoreboard;

import at.fhtw.mtcg.app.dal.repository.ScoreboardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class ScoreboardService implements Service {


    private final ScoreboardController scoreboardController;
    public ScoreboardService() {
        this.scoreboardController = new ScoreboardController(new ScoreboardRepository(), new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token = authorization.split(" ")[1];
            return this.scoreboardController.getScoreboard(token);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
