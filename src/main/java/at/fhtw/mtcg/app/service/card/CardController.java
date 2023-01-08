package at.fhtw.mtcg.app.service.card;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.app.model.Card;
import at.fhtw.mtcg.app.model.Trade;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class CardController extends Controller {

    private CardRepository cardRepository;
    private UserRepository userRepository;

    public CardController(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Response showAllCards(String token) {

        try {
            if (token.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No user (token) found\" }"
                );
            } else {
                String username = this.userRepository.tokenToUsername(token);
                List<Card> cardData = this.cardRepository.showAllCards(username);
                String cardDataJSON = this.getObjectMapper().writeValueAsString(cardData);
                String cardDataPrettyJSON = this.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cardData);

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        cardDataPrettyJSON
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

    public Response createNewTradingDeal(String token, Request request) {
        try {
            if (token.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No user (token) found\" }"
                );
            } else {
//                String tradeDataString;
                String username = this.userRepository.tokenToUsername(token);
                Trade trade = this.getObjectMapper().readValue(request.getBody(), Trade.class);

                String[] tradeData = new String[4];
                tradeData[0] = trade.getTradingId();
                tradeData[1] = trade.getCardId();
                tradeData[2] = trade.getCardType();
                tradeData[3] = String.valueOf(trade.getMinDamage());

                if (this.cardRepository.tradeWithOneself(tradeData[0], username)) {
                    return new Response(
                            HttpStatus.CONFLICT,
                            ContentType.JSON,
                            "{ \"message\" : \"Can't trade with yourself, mate.\" }"
                    );
                }
                if (this.cardRepository.tradeOfferExists(tradeData[0])) {
                    return new Response(
                            HttpStatus.FORBIDDEN,
                            ContentType.JSON,
                            "{ \"message\" : \"There is already such a trading (id) deal.\" }"
                    );
                }
                if (!this.cardRepository.cardBelongsToUser(tradeData[1], username) || this.cardRepository.cardInDeck(tradeData[1], username)) {
                    return new Response(
                            HttpStatus.FORBIDDEN,
                            ContentType.JSON,
                            "{ \"message\" : \"Either not your card OR it's yours, but in deck as well.\" }"
                    );
                }
                this.cardRepository.insertIntoTradings(tradeData, username);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"message\" : \"Card successfully added to trading.\" }"
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

    public Response checkTradingDeals(String token) {
        try {
            if (token.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No user (token) found\" }"
                );
            } else {
                List<Trade> tradeData = this.cardRepository.checkTradingDeals();
                if (tradeData.isEmpty()) {
                    return new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{ message: \"No trading deals found.\" }"
                    );
                }
                String tradeDataJSON = this.getObjectMapper().writeValueAsString(tradeData);
                String tradeDataPrettyJSON = this.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(tradeData);

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        tradeDataPrettyJSON
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

    public Response offerCardToTrade(String token, String tradeId, String cardToTradeFromRequest) {
        try {
            if (token.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No user (token) found\" }"
                );
            }
            String[] cardToTradeString = cardToTradeFromRequest.split("\"");
            String cardToTrade = cardToTradeString[1];

            String username = this.userRepository.tokenToUsername(token);
            String tradeDealUserUsername = this.cardRepository.getTradeUsername(tradeId);
            String tradeDealUserCardId = this.cardRepository.getTradeCard(tradeId);

            if (this.cardRepository.tradeWithOneself(tradeId, username)) {
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ \"message\" : \"Can't trade with yourself, mate.\" }"
                );
            }

            if (!(this.cardRepository.cardBelongsToUser(cardToTrade, username)) || this.cardRepository.cardInDeck(cardToTrade, username)) {
                return new Response(
                        HttpStatus.FORBIDDEN,
                        ContentType.JSON,
                        "{ \"message\" : \"Either not your card or it's yours, but in deck as well.\" }"
                );
            }

            Card offeredCard = this.cardRepository.viewOfferedCard(username, cardToTrade);
            Card tradeDealUserCard = this.cardRepository.viewOfferedCard(tradeDealUserUsername, tradeDealUserCardId);

            if (this.cardRepository.checkOfferedCard(tradeId, offeredCard)) {
                this.cardRepository.deleteFromPlayerStack(tradeDealUserUsername, tradeDealUserCardId);
                this.cardRepository.insertIntoPlayerStack(username, tradeDealUserCard);
                this.cardRepository.deleteFromPlayerStack(username, cardToTrade);
                this.cardRepository.insertIntoPlayerStack(tradeDealUserUsername, offeredCard);
                this.cardRepository.deleteTradeDeal(tradeId);

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"message\" : \"Trade successful.\" }"
                );
            } else {
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ \"message\" : \"Trade not successful.\" }"
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

    public Response deleteTradingDeal(String token, String tradeId) {
        try {
            if (token.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No user (token) found\" }"
                );
            } else {
                String username = this.userRepository.tokenToUsername(token);
                if (this.cardRepository.dealNotUsers(username, tradeId)) {
                    return new Response(
                            HttpStatus.FORBIDDEN,
                            ContentType.JSON,
                            "{ \"message\" : \"Not your deal, mate\" }"
                    );
                } else {
                    this.cardRepository.deleteTradeDeal(tradeId);
                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            "{ \"message\" : \"Trade removed.\" }"
                    );
                }
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
