package at.fhtw.mtcg.app.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Objects;
import java.util.Random;

public class Round {
    @JsonAlias({"player1"})
    private String player1;

    @JsonAlias({"player2"})
    private String player2;

    @JsonAlias({"cardPlayer1"})
    private Card cardPlayer1;

    @JsonAlias({"cardPlayer2"})
    private Card cardPlayer2;

    public Round(String player1, String player2, Card cardPlayer1, Card cardPlayer2) {
        this.player1 = player1;
        this.player2 = player2;
        this.cardPlayer1 = cardPlayer1;
        this.cardPlayer2 = cardPlayer2;
    }


    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Card getCardPlayer1() {
        return cardPlayer1;
    }

    public void setCardPlayer1(Card cardPlayer1) {
        this.cardPlayer1 = cardPlayer1;
    }

    public Card getCardPlayer2() {
        return cardPlayer2;
    }

    public void setCardPlayer2(Card cardPlayer2) {
        this.cardPlayer2 = cardPlayer2;
    }

    //
    public boolean isLucky() {
        Random random = new Random();
        int isLucky = random.nextInt(10);

        return isLucky < 3;
    }
    public String fight() {
        Random rand2 = new Random();
        int randomValue2 = rand2.nextInt(2);

        System.out.println("just ran nr is " + randomValue2);


        String log = "";
        String finalLog = "";

        log = log + "Player 1: " + this.player1 + " draws " + cardPlayer1.getName().name() + " (damage: " + cardPlayer1.getDamage() + ")\n" + "Player 2: " + this.player2 + " draws " + cardPlayer2.getName().name() + " (damage: " + cardPlayer2.getDamage() + ")\n";
//
        if(isLucky() && Objects.equals(cardPlayer1.getDamage(), cardPlayer2.getDamage())) {
            String[] whoIsLuckyPlayer = new String[2];
            Card[] whichIsLuckyCard = new Card[2];

            whoIsLuckyPlayer[0] = player1;
            whoIsLuckyPlayer[1] = player2;

            whichIsLuckyCard[0] = cardPlayer1;
            whichIsLuckyCard[1] = cardPlayer2;


            Random rand = new Random();
            int randomValue = rand.nextInt(2);

            System.out.println("random nr is " + randomValue);

            int otherValue = 0;
            if(randomValue == 0) {
                otherValue = 1;
            }

            log = log + "Player " + whoIsLuckyPlayer[randomValue] + " just got lucky!\n";
            log = log + whichIsLuckyCard[randomValue].getName().name() + " devours " + whichIsLuckyCard[otherValue].getName().name() + "\n";
            whichIsLuckyCard[otherValue].setDamage(whichIsLuckyCard[otherValue].getDamage() - whichIsLuckyCard[randomValue].getDamage());
            return log;
        }
        else {

            float card1Damage = cardPlayer1.recalibrateDamage(cardPlayer2);
            float card2Damage = cardPlayer2.recalibrateDamage(cardPlayer1);
            //
//        float card1 = cardPlayer1.recalibrateDamage(cardPlayer2);
//        float card2 = cardPlayer2.recalibrateDamage(cardPlayer1);

            if (cardPlayer1.getDamage() > cardPlayer2.getDamage()) {
                cardPlayer2.setDamage(cardPlayer2.getDamage() - cardPlayer1.getDamage());
                log = log + "Player " + player1 + " won this round.\n";

            } else if (cardPlayer2.getDamage() > cardPlayer1.getDamage()) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
                log = log + "Player " + player2 + " won this round.\n";
            }

            else {
                log = log + "This round is a tie.\n";
            }
        }
        return log;
//        if(card1 > card2) {
//            cardPlayer2.setDamage(cardPlayer2.getDamage() - cardPlayer1.getDamage());
//        }
//        else if(card2 > card1) {
//            cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
//        }

//        if(cardPlayer1.getDamage() > cardPlayer2.getDamage()) {
//            cardPlayer2.setDamage(cardPlayer2.getDamage() - cardPlayer1.getDamage());
//        }
//        else if(cardPlayer1.getDamage() < cardPlayer2.getDamage()) {
//            cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
//        }
//
//        else if(Objects.equals(cardPlayer1.getDamage(), cardPlayer2.getDamage())){
////            System.out.println("Equal damage");
//        }
//        else {
//            cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
//        }
    }
    public String calculateDamage() {
        String log = null;
        log = cardPlayer1.getName().name() + " (damage = " + cardPlayer1.getDamage() + ") vs " + cardPlayer2.getName().name() + " (damage = ) " + cardPlayer2.getDamage();

        // add battle log
        if(this.cardPlayer1.getType().name().equals("monster") && this.cardPlayer2.getType().name().equals("monster")) {
            log = log + "Monster fight!\n";
            System.out.println("Monster fight!\n");

            if(this.cardPlayer1.getName().name().contains("Goblin") && this.cardPlayer2.getName().name().contains("Dragon") ) {
                log = log + "\nGoblin are too afraid of Dragons to attack" + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
                System.out.println("Goblin are too afraid of Dragons to attack");
                return log;
            }

            else if(this.cardPlayer1.getName().name().contains("Dragon") && this.cardPlayer2.getName().name().contains("Goblin") ) {
                log = log + "\nGoblin are too afraid of Dragons to attack" + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
                System.out.println("Goblin are too afraid of Dragons to attack");
                return log;
            }

            else if(this.cardPlayer1.getName().name().contains("FireElf") && this.cardPlayer2.getName().name().contains("Dragon") ) {
                log = log + "\nThe FireElves know Dragons since they were little and can evade their attacks. " + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
                System.out.println("Goblin are too afraid of Dragons to attack");
                return log;
            }

            else if(this.cardPlayer1.getName().name().contains("Dragon") && this.cardPlayer2.getName().name().contains("FireElf") ) {
                log = log + "\nThe FireElves know Dragons since they were little and can evade their attacks." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
                System.out.println("Goblin are too afraid of Dragons to attack");
                return log;
            }

            if(this.cardPlayer1.getName().name().contains("Ork") && this.cardPlayer2.getElement().name().equals("Water")) {
//                log = log + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                this.cardPlayer1.setDamage(this.cardPlayer1.getDamage()/2);

                log = log + player1 + "'s " + cardPlayer1.getName().name() + " is drowning! The HP has dropped to " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Ork vs Water monster");
            }
            if(cardPlayer1.getName().name().contains("Water") && cardPlayer2.getElement().name().equals("Ork")) {
                cardPlayer2.setDamage(cardPlayer2.getDamage()/2);
                log = log + player2 + "'s " + cardPlayer2.getName().name() + " is drowning! The HP has dropped to " + cardPlayer2.getDamage() + " HP.\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n";
            }

            if(cardPlayer1.getElement().name().equals("Fire") && cardPlayer2.getElement().name().equals("Water")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);
                log = log + "\nWater is effective against fire, so damage is doubled." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Water is effective against fire, so damage is doubled.");
            }

            if(cardPlayer1.getElement().name().equals("Water") && cardPlayer2.getElement().name().equals("Fire")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nWater is effective against fire, so damage is doubled." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Water is effective against fire, so damage is doubled.");
            }

            if(cardPlayer1.getDamage() > cardPlayer2.getDamage()) {
                cardPlayer2.setDamage(cardPlayer2.getDamage() - cardPlayer1.getDamage());
            }
            else if(Objects.equals(cardPlayer1.getDamage(), cardPlayer2.getDamage())){
                System.out.println("Equal damage");
            }
            else {
                cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
            }
            log = log + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
        }

        if((cardPlayer1.getType().name().equals("spell") && cardPlayer2.getType().name().equals("monster")) || (cardPlayer1.getType().name().equals("monster") && cardPlayer2.getType().name().equals("spell"))) {

            System.out.println("Mixed fight!\n");

            if(cardPlayer1.getElement().name().equals("Water") && cardPlayer2.getName().name().contains("Knight")) {
                cardPlayer2.setDamage((float) 0);
                System.out.println("The armor of Knights is so heavy that WaterSpells make them drown them instantly.");
                log = log + "The armor of Knights is so heavy that WaterSpells make them drown them instantly.\n";
                log = log + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

            }
            else if(cardPlayer1.getName().name().contains("Knight") && cardPlayer2.getElement().name().equals("Water")) {
                cardPlayer1.setDamage((float) 0);
                System.out.println("The armor of Knights is so heavy that WaterSpells make them drown them instantly.");
                log = log + "The armor of Knights is so heavy that WaterSpells make them drown them instantly.\n";
                log = log + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

            }
            else if(cardPlayer1.getName().name().contains("Kraken")) {
                System.out.println("The Kraken is immune against spells.");
                log = log + "\nThe Kraken is immune against spells." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
                return log;
            }
            else if(cardPlayer2.getName().name().contains("Kraken")) {
                System.out.println("The Kraken is immune against spells.");
                log = log + "\nThe Kraken is immune against spells." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
                return log;
            }
            else if(cardPlayer1.getElement().name().equals("Fire") && cardPlayer2.getElement().name().equals("Water")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);
                log = log + "\nWater is effective against fire, so damage is doubled." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Water is effective against fire, so damage is doubled.");
            }

            else if(cardPlayer1.getElement().name().equals("Water") && cardPlayer2.getElement().name().equals("Fire")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nWater is effective against fire, so damage is doubled." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Water is effective against fire, so damage is doubled.");
            }

            else if(cardPlayer1.getElement().name().equals("Fire") && cardPlayer2.getElement().name().equals("Normal")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nFire spells burn like hell." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Fire spells burn like hell.");
            }

            else if(cardPlayer1.getElement().name().equals("Normal") && cardPlayer2.getElement().name().equals("Fire")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);
                log = log + "\nFire spells burn like hell." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Fire spells burn like hell.");

            }
            else if(cardPlayer1.getElement().name().equals("Fire") && cardPlayer2.getElement().name().equals("Regular")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nFire spells burn like hell." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Fire spells burn like hell.");

            }

            else if(cardPlayer1.getElement().name().equals("Regular") && cardPlayer2.getElement().name().equals("Fire")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);
                log = log + "\nFire spells burn like hell." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Fire spells burn like hell.");

            }
            else if(cardPlayer1.getElement().name().equals("Water") && cardPlayer2.getElement().name().equals("Normal")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nWater drowns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Water vs Normal");

            }

            else if(cardPlayer1.getElement().name().equals("Normal") && cardPlayer2.getElement().name().equals("Water")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);
                log = log + "\nWater drowns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Normal vs Water");

            }
            else if(cardPlayer1.getElement().name().equals("Water") && cardPlayer2.getElement().name().equals("Regular")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nWater drowns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Water vs Regular");

            }

            else if(cardPlayer1.getElement().name().equals("Regular") && cardPlayer2.getElement().name().equals("Water")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);
                log = log + "\nWater drowns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Regular vs Water");

            }
//            else {
//                cardPlayer1.setDamage(cardPlayer1.getDamage());
//                cardPlayer2.setDamage(cardPlayer2.getDamage());
//
//                log = log + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
//
//            }

            if(cardPlayer1.getDamage() > cardPlayer2.getDamage()) {
                cardPlayer2.setDamage(cardPlayer2.getDamage() - cardPlayer1.getDamage());
            }
            else if(Objects.equals(cardPlayer1.getDamage(), cardPlayer2.getDamage())){
                System.out.println("Equal damage");
            }
            else {
                cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
            }

            log = log + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

        }

        if(cardPlayer1.getType().name().equals("spell") && cardPlayer2.getType().name().equals("spell")) {
            System.out.println("Spell fight!\n");
            if(cardPlayer1.getElement().name().equals("Fire") && cardPlayer2.getElement().name().equals("Water")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);

                log = log + "\nWater beats fire." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Fire vs Water");
            }

            else if(cardPlayer1.getElement().name().equals("Water") && cardPlayer2.getElement().name().equals("Fire")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nWater beats fire." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Water vs Fire");

            }

            else if(cardPlayer1.getElement().name().equals("Fire") && cardPlayer2.getElement().name().equals("Normal")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);

                log = log + "\nFire burns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Fire vs Normal");

            }

            else if(cardPlayer1.getElement().name().equals("Normal") && cardPlayer2.getElement().name().equals("Fire")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nFire burns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Normal vs Fire");

            }
            else if(cardPlayer1.getElement().name().equals("Fire") && cardPlayer2.getElement().name().equals("Regular")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() * 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() / 2);
                log = log + "\nFire burns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Fire vs Regular");

            }

            else if(cardPlayer1.getElement().name().equals("Regular") && cardPlayer2.getElement().name().equals("Fire")) {
                cardPlayer1.setDamage(cardPlayer1.getDamage() / 2);
                cardPlayer2.setDamage(cardPlayer2.getDamage() * 2);
                log = log + "\nFire burns." + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

                System.out.println("Regular vs Fire");

            }
//            else {
//                cardPlayer1.setDamage(cardPlayer1.getDamage());
//                cardPlayer2.setDamage(cardPlayer2.getDamage());
//                log = log + "\nStats:\n" + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";
//
//            }

            if(cardPlayer1.getDamage() > cardPlayer2.getDamage()) {
                cardPlayer2.setDamage(cardPlayer2.getDamage() - cardPlayer1.getDamage());
            }
            else if(Objects.equals(cardPlayer1.getDamage(), cardPlayer2.getDamage())){
//                cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
                System.out.println("Equal damage");

            }
            else {
                cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
            }
            log = log + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

        }
        else {
            if(cardPlayer1.getDamage() > cardPlayer2.getDamage()) {
                cardPlayer2.setDamage(cardPlayer2.getDamage() - cardPlayer1.getDamage());
            }
            else if(Objects.equals(cardPlayer1.getDamage(), cardPlayer2.getDamage())){
                System.out.println("Equal damage");
            }
            else {
                cardPlayer1.setDamage(cardPlayer1.getDamage() - cardPlayer2.getDamage());
            }
            log = log + player1 + "'s " + cardPlayer1.getName().name() + " has " + cardPlayer1.getDamage() + " HP.\n" + player2 + "'s " + cardPlayer2.getName().name() + " has " + cardPlayer2.getDamage() + " HP.\n";

        }
        return log;
    }
}