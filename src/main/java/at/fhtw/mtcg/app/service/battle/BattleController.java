package at.fhtw.mtcg.app.service.battle;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.BattleRepository;
import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.app.model.Card;
import at.fhtw.mtcg.app.model.Round;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Response;

import java.util.List;
import java.util.Objects;

public class BattleController extends Controller {

    private BattleRepository battleRepository;
    private CardRepository cardRepository;
    private UserRepository userRepository;

    private Integer playerCounter = 0;

    public BattleController(BattleRepository battleRepository, CardRepository cardRepository, UserRepository userRepository) {
        this.battleRepository = battleRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }


    public synchronized Response battle(String token) {
        try {
            if (token.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No user (token) found\" }"
                );
            } else {
                String player = this.userRepository.tokenToUsername(token);
                Integer battle_id = 0;
                if (this.battleRepository.getPlayer1() == null) {
                    this.battleRepository.enterBattlePlayer1(player);
                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            "{ \"message\" : \"Waiting for another player\" }"
                    );
                } else {
                    String player1 = this.battleRepository.getPlayer1();
                    battle_id = this.battleRepository.getBattleId(player1);
                    this.battleRepository.enterBattlePlayer2(player, battle_id);
                }

                String player1Ready = battleRepository.getPlayer1();
                String player2Ready = this.battleRepository.getPlayer2();

                String winner = null;
                String loser = null;

                StringBuilder log = new StringBuilder();
                log = new StringBuilder("\nPlayer 1: " + player1Ready + "\nPlayer 2: " + player2Ready);
                for (int i = 0; i < 100; i++) {

                    List<Card> player1Deck = this.cardRepository.showDeck(player1Ready);
                    List<Card> player2Deck = this.cardRepository.showDeck(player2Ready);

                    if (this.cardRepository.isDeckEmpty(player1Ready)) {
                        winner = player2Ready;
                        loser = player1Ready;
                        break;
                    } else if (this.cardRepository.isDeckEmpty(player2Ready)) {
                        winner = player1Ready;
                        loser = player2Ready;
                        break;
                    } else if ((player1Deck.size() == 1 && player2Deck.size() == 1) && Objects.equals(player1Deck.get(0).getDamage(), player2Deck.get(0).getDamage())) {
                        break;
                    }

                    log.append("\nRound: ").append(i + 1).append("\n");
                    Card player1Card = this.battleRepository.chooseCardToFight(player1Ready);
                    Card player2Card = this.battleRepository.chooseCardToFight(player2Ready);
                    Round round = new Round(player1Ready, player2Ready, player1Card, player2Card);

                    // if card is null
                    log.append(round.fight());

//                    String roundLog = round.calculateDamage();
//                    System.out.println("LOG " + roundLog);
                    // add battle log
                    if (player1Card.getDamage() == 0) {
                        Card card = this.battleRepository.extractWonCard(player1Ready, player1Card.getCardId());
                        this.battleRepository.deleteFromPlayerStack(player1Ready, player1Card.getCardId());
                        assert card != null;
                        this.battleRepository.insertIntoPlayerStack(player2Ready, card);
                    } else if (player2Card.getDamage() == 0) {
                        Card card = this.battleRepository.extractWonCard(player2Ready, player2Card.getCardId());
                        this.battleRepository.deleteFromPlayerStack(player2Ready, player2Card.getCardId());
                        assert card != null;
                        this.battleRepository.insertIntoPlayerStack(player1Ready, card);
                    }
                }
                if (winner == null && loser == null) {
                    log.insert(0, "\nIt's a tie!");
//                    return new Response(
//                            HttpStatus.OK,
//                            ContentType.JSON,
//                            log.toString()
////                            "{ \"message\" : \"It's a tie!\"}"
//                    );
                } else {
                    System.out.println("winner of the battle " + winner);
                    this.userRepository.updateStats(winner, true);
                    this.userRepository.updateStats(loser, false);
                    log.insert(0, "\nWinner of the battle: " + winner);

                }
                this.battleRepository.insertIntoBattleLog(battle_id, log.toString());

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        log.toString()
//                        "{ \"message\" : \"Player " + winner + " has won\" " + log + " }"
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