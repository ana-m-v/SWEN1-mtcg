package at.fhtw.mtcg.app.service.battle;

import at.fhtw.mtcg.app.dal.repository.BattleRepository;
import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

import java.util.ArrayList;
import java.util.List;

public class BattleService implements Service {

    private final BattleController battleController;

    private List<String> tokens = new ArrayList<>();

    public BattleService() {
        this.battleController = new BattleController(new BattleRepository(), new CardRepository(), new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token;

            if(authorization == null) {
                token = "";
            }
            else {
                token = authorization.split(" ")[1];
            }
            return this.battleController.battle(token);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}