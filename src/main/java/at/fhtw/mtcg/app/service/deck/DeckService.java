package at.fhtw.mtcg.app.service.deck;

import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class DeckService implements Service {

    private final DeckController deckController;

    public DeckService() {
        this.deckController = new DeckController(new CardRepository(), new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) {

        String authorization = request.getHeaderMap().getHeader("Authorization");
        String token;

        if(authorization == null) {
            token = "";
        }
        else {
            token = authorization.split(" ")[1];
        }

        if (request.getMethod() == Method.GET) {
            return this.deckController.showDeck(token, request);
        }
        else if (request.getMethod() == Method.PUT) {
            return this.deckController.insertCardsToDeck(request, token);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}

