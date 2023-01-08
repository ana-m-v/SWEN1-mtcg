package at.fhtw.mtcg.app.service.transaction;

import at.fhtw.mtcg.app.controller.Controller;
import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.app.model.Card;
import at.fhtw.mtcg.app.model.CardElementEnum;
import at.fhtw.mtcg.app.model.CardNameEnum;
import at.fhtw.mtcg.app.model.CardTypeEnum;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;

public class TransactionController extends Controller {

    private UserRepository userRepository;
    private CardRepository cardRepository;

    public TransactionController(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public Response buyPackage(Request request) {
        try {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token = authorization.split(" ")[1];
            String username = this.userRepository.tokenToUsername(token);
            if(cardRepository.packageEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"No more packages\" }"
                );
            }
            if(username.isEmpty()) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ message: \"User does not exist\" }"
                );
            }
            else {
                Integer coins = this.userRepository.checkCoins(username);
                List<Card> cardsToAdd = new ArrayList<>();
                if (coins > 4) {
                    this.userRepository.decreaseCoins(username);
                    String cardPackage = this.cardRepository.extractPackage();
                    String[] split= cardPackage.split("/");
                    String card1string = split[0];
                    String card2string= split[1];
                    String card3string= split[2];
                    String card4string = split[3];
                    String card5string= split[4];

                    String[] card1stringSplit = card1string.split(", ");

                    String[] card1TypeElement = card1stringSplit[1].split("(?=\\p{Upper})",2);

                    String card1Element = null;
                    String card1Type = null;
                    if(card1TypeElement.length == 2) {
                        card1Element = card1TypeElement[0];
                        card1Type = card1TypeElement[1];

                        if(card1Type.equals("Goblin") || card1Type.equals("Wizard") || card1Type.equals("Ork") || card1Type.equals("Knight") || card1Type.equals("Kraken") || card1Type.equals("Elf") || card1Type.equals("Dragon")) {
                            card1Type = "monster";
                        }
                        else {
                            card1Type = "spell";
                        }
                    }
                    else {
                        card1Type = "monster";
                        card1Element = "Normal";
                    }

                    Card card1Card = new Card(card1stringSplit[0], CardNameEnum.valueOf(card1stringSplit[1]), CardTypeEnum.valueOf(card1Type), CardElementEnum.valueOf(card1Element), parseFloat(card1stringSplit[2]));

                    cardsToAdd.add(card1Card);

                    String[] card2stringSplit = card2string.split(", ");
                    String[] card2TypeElement = card2stringSplit[1].split("(?=\\p{Upper})",2);

                    String card2SpecialName = null;
                    String card2Element = null;
                    String card2Type = null;
                    if(card2TypeElement.length == 2) {
                        card2Element = card2TypeElement[0];
                        card2Type = card2TypeElement[1];

                        if(card2Type.equals("Goblin") || card2Type.equals("Wizard") || card2Type.equals("Ork") || card2Type.equals("Knight") || card2Type.equals("Kraken") || card2Type.equals("Elf") || card2Type.equals("Dragon")) {
                            card2Type = "monster";
                        }
                        else {
                            card2Type = "spell";
                        }
                    }
                    else {
                        card2Type = "monster";
                        card2Element = "Normal";
                    }
                    Card card2Card = new Card(card2stringSplit[0], CardNameEnum.valueOf(card2stringSplit[1]), CardTypeEnum.valueOf(card2Type), CardElementEnum.valueOf(card2Element), parseFloat(card2stringSplit[2]));

                    cardsToAdd.add(card2Card);

                    String[] card3stringSplit = card3string.split(", ");

                    String[] card3TypeElement = card3stringSplit[1].split("(?=\\p{Upper})",2);

                    String card3SpecialName = null;
                    String card3Element = null;
                    String card3Type = null;
                    if(card3TypeElement.length == 2) {
                        card3Element = card3TypeElement[0];
                        card3Type = card3TypeElement[1];

                        if(card3Type.equals("Goblin") || card3Type.equals("Wizard") || card3Type.equals("Ork") || card3Type.equals("Knight") || card3Type.equals("Kraken") || card3Type.equals("Elf") || card3Type.equals("Dragon")) {
                            card3Type = "monster";
                        }
                        else {
                            card3Type = "spell";
                        }
                    }
                    else {
                        card3Type = "monster";
                        card3Element = "Normal";
                    }

                    Card card3Card = new Card(card3stringSplit[0], CardNameEnum.valueOf(card3stringSplit[1]) , CardTypeEnum.valueOf(card3Type), CardElementEnum.valueOf(card3Element), parseFloat(card3stringSplit[2]));

                    cardsToAdd.add(card3Card);

                    String[] card4stringSplit = card4string.split(", ");

                    String[] card4TypeElement = card4stringSplit[1].split("(?=\\p{Upper})",2);

                    String card4SpecialName = null;
                    String card4Element = null;
                    String card4Type = null;
                    if(card4TypeElement.length == 2) {
                        card4Element = card4TypeElement[0];
                        card4Type = card4TypeElement[1];

                        if(card4Type.equals("Goblin") || card4Type.equals("Wizard") || card4Type.equals("Ork") || card4Type.equals("Knight") || card4Type.equals("Kraken") || card4Type.equals("Elf") || card4Type.equals("Dragon")) {
                            card4Type = "monster";
                        }
                        else {
                            card4Type = "spell";
                        }
                        card4SpecialName = card4Type;
                    }
                    else {
                        card4Type = "monster";
                        card4SpecialName = card4Type;
                        card4Element = "Normal";
                    }

                    Card card4Card = new Card(card4stringSplit[0], CardNameEnum.valueOf(card4stringSplit[1]), CardTypeEnum.valueOf(card4Type), CardElementEnum.valueOf(card4Element), parseFloat(card4stringSplit[2]));

                    cardsToAdd.add(card4Card);

                    String[] card5stringSplit = card5string.split(", ");

                    String[] card5TypeElement = card5stringSplit[1].split("(?=\\p{Upper})",2);

                    String card5Element = null;
                    String card5Type = null;
                    if(card5TypeElement.length == 2) {
                        card5Element = card5TypeElement[0];
                        card5Type = card5TypeElement[1];

                        if(card5Type.equals("Goblin") || card5Type.equals("Wizard") || card5Type.equals("Ork") || card5Type.equals("Knight") || card5Type.equals("Kraken") || card5Type.equals("Elf") || card5Type.equals("Dragon")) {
                            card5Type = "monster";
                        }
                        else {
                            card5Type = "spell";
                        }
                    }
                    else {
                        card5Type = "monster";
                        card5Element = "Normal";
                    }

                    Card card5Card = new Card(card5stringSplit[0], CardNameEnum.valueOf(card5stringSplit[1]), CardTypeEnum.valueOf(card5Type), CardElementEnum.valueOf(card5Element), parseFloat(card5stringSplit[2]));
                    cardsToAdd.add(card5Card);

                    ////////////
                    for(int i = 0; i < 5; i++) {
                        this.cardRepository.assignToPackage(username, cardsToAdd.get(i));
                    }
                    this.cardRepository.deletePackage(cardPackage);

                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            "{ message: Package bought }"
                    );
                } else {
                    return new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{ message: \"Not enough money\" }"
                    );
                }
            }
//            JsonProcessingException
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
