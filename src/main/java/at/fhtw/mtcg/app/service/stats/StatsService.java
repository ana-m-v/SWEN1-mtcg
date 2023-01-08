package at.fhtw.mtcg.app.service.stats;

import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class StatsService implements Service {

    private final StatsController statsController;
    public StatsService() {
        this.statsController = new StatsController(new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token = authorization.split(" ")[1];
            return this.statsController.getStats(token);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
