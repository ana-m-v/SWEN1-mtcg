package at.fhtw.mtcg.app.service.packagecard;

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
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.util.List;

public class PackageCardController extends Controller {

    private UserRepository userRepository;
    private CardRepository cardRepository;
    public PackageCardController(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public Response addCardToPackage(Request request) {
        try {
            List<Card> cards;
            cards = this.getObjectMapper().readValue(request.getBody(), new TypeReference<List<Card>>() {
            });
            this.getObjectMapper().enable(DeserializationFeature
                    .FAIL_ON_UNKNOWN_PROPERTIES);
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token = authorization.split(" ")[1];
            if (!token.equals("admin-mtcgToken")) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "{ message: \"Only admin can do this\" }"
                );
            } else {
                String cardsToAdd = "";
                for (Card card : cards) {
                    cardsToAdd = cardsToAdd + card.getCardId() + ", " + card.getName().name() + ", " + card.getDamage() + "/";
                }
                this.cardRepository.addCardToPackage(cardsToAdd);

                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ message: \"Success\" }"
                );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }
}