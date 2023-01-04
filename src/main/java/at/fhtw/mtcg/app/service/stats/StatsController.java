package at.fhtw.mtcg.app.service.stats;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Response;

public class StatsController extends Controller {
    UserRepository userRepository = new UserRepository();
    public StatsController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response getStats(String token) {

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
                String statsData = this.userRepository.getStats(username);

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        statsData
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
