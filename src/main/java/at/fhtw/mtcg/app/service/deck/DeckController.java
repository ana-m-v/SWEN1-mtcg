package at.fhtw.mtcg.app.service.deck;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.app.model.Card;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class DeckController extends Controller {

    private CardRepository cardRepository;
    private UserRepository userRepository;

    public DeckController(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Response showDeck(String token, Request request) {
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
                List<Card> cardData = this.cardRepository.showDeck(username);
                String cardDataJSON = this.getObjectMapper().writeValueAsString(cardData);
                String cardDataPrettyJSON = this.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cardData);

                if(cardData.isEmpty()) {
                    return new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{ message: \"The deck is not (yet) configured.\" }"
                    );
                }
                if(request.getParams() == null) {
                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            cardDataPrettyJSON
                    );
                }
                else{
                    return new Response(
                            HttpStatus.OK,
                            ContentType.PLAIN_TEXT,
                            cardDataJSON
                    );
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }

    public Response insertCardsToDeck(Request request, String token) {
        try {
            String[] cardData;
            cardData = this.getObjectMapper().readValue(request.getBody(), new TypeReference<String[]>() {});

            if(token.isEmpty()) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "{ message: \"Not authorized\" }"
                );
            }
            String username = this.userRepository.tokenToUsername(token);
            Integer count = 0;
            Boolean cardBelongsToUser = false;
            for(int i = 0; i < cardData.length; i++) {
                if(this.cardRepository.cardBelongsToUser(cardData[i], username)) {
                    count++;
                }
            }
            if(count == cardData.length) {
                cardBelongsToUser = true;
            }

            if(username.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"User does not exist\" }"
                );
            }

            else if(cardData.length < 4) {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "{ message: \"Not enough cards for deck (need 4 to be precise)\" }"
                );
            }

            else if(!cardBelongsToUser) {
                return new Response(
                        HttpStatus.FORBIDDEN,
                        ContentType.JSON,
                        "{ message: \"Not your cards, mate\" }"
                );
            }
            else {
                for(int i = 0; i < cardData.length; i++) {
                    this.cardRepository.insertCardsToDeck(username, cardData[i]);
                }
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ message: \"Cards added to deck\" }"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"message\" : \"Internal Server Error\" }"
            );
        }
    }
}

