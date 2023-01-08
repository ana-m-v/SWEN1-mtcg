package at.fhtw.mtcg.app.service.card;

import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class CardService implements Service {

    private final CardController cardController;

    public CardService() {
        this.cardController = new CardController(new CardRepository(), new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) {

        if(request.getMethod() == Method.GET && request.getPathname().equals("/tradings")) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token;

            if(authorization == null) {
                token = "";
            }
            else {
                token = authorization.split(" ")[1];
            }
            return this.cardController.checkTradingDeals(token);
        }
        else if (request.getMethod() == Method.GET) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token;

            if(authorization == null) {
                token = "";
            }
            else {
                token = authorization.split(" ")[1];
            }
            return this.cardController.showAllCards(token);
        }

        else if(request.getMethod() == Method.POST && request.getPathParts().size() > 1) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token;

            if(authorization == null) {
                token = "";
            }
            else {
                token = authorization.split(" ")[1];
            }
            return this.cardController.offerCardToTrade(token, request.getPathParts().get(1), request.getBody());
        }

        else if(request.getMethod() == Method.POST && request.getPathname().equals("/tradings")) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token;

            if(authorization == null) {
                token = "";
            }
            else {
                token = authorization.split(" ")[1];
            }
            return this.cardController.createNewTradingDeal(token, request);
        }
//        else if (request.getMethod() == Method.POST) {
//            return this.cardController.createCard(request);
//        }
        else if(request.getMethod() == Method.DELETE) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token;

            if(authorization == null) {
                token = "";
            }
            else {
                token = authorization.split(" ")[1];
            }
            return this.cardController.deleteTradingDeal(token, request.getPathParts().get(1));
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}