package at.fhtw.mtcg.app.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Card {

    @JsonAlias({"Id"})
    private String cardId;

    @JsonAlias({"Name"})
    private CardNameEnum name;

    @JsonAlias({"Type"})
    private CardTypeEnum type;

    @JsonAlias({"Element"})
    private CardElementEnum element;

    @JsonAlias({"Damage"})
    private Float damage;

    public Card() {};

    public Card(String card_id, CardNameEnum name, Float damage) {
        this.cardId = card_id;
        this.name = name;
//        this.type = type;
//        this.element = element;
        this.damage = damage;
    }

    public  Card(String card_id, CardNameEnum name, CardTypeEnum type, CardElementEnum element, Float damage) {
        this.cardId = card_id;
        this.name = name;
        this.type = type;
        this.element = element;
        this.damage = damage;
    }


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public CardNameEnum getName() {
        return name;
    }

    public void setName(CardNameEnum name) {
        this.name = name;
    }
    public CardTypeEnum getType() {
        return type;
    }

    public void setType(CardTypeEnum type) {
        this.type = type;
    }

    public CardElementEnum getElement() {
        return element;
    }

    public void setElement(CardElementEnum element) {
        this.element = element;
    }

    public Float getDamage() {
        return damage;
    }

    public void setDamage(Float damage) {
        this.damage = damage;
        if(this.damage < 0) {
            this.damage = (float) 0;
        }
    }
    public float recalibrateDamage(Card opponentCard) {

        String log = "";
        // monster vs monster
        if(this.type == CardTypeEnum.monster && opponentCard.getType().name().equals("monster")) {

            System.out.println("mVm");

            log = log + "Monster card VS Monster card\n";
            if(this.name.name().contains("Goblin")) {
                    if(opponentCard.getName().name().contains("Dragon")) {
                        System.out.println("Goblin vs Dragon");
                        this.setDamage(0F);
                    }
            }
            else if(this.name.name().contains("Dragon")) {
                    if(opponentCard.getName().name().contains("FireElf")) {
                        System.out.println("Dragon vs Fireelf");
                        this.setDamage(0F);
                    }
            }
        }

        // spell vs spell

        if(this.type == CardTypeEnum.spell && opponentCard.getType().name().equals("spell")) {
            System.out.println("sVs");
            if(this.element == CardElementEnum.Fire) {

                if(opponentCard.getElement().name().equals("Water")) {
                    System.out.println("Fire vs Water");
                    this.setDamage(this.getDamage() / 2);
                }

                if(opponentCard.getElement().name().equals("Regular")) {
                    System.out.println("Fire vs Regular");
                    this.setDamage(this.getDamage() * 2);
                }
            }
            if(this.element == CardElementEnum.Water) {
                System.out.println("Water vs Everything");

                if(opponentCard.getElement().name().equals("Fire") || opponentCard.getElement().name().equals("Regular") || opponentCard.getElement().name().equals("Normal")) {
                    System.out.println("Water vs Fire");
                    this.setDamage(this.getDamage() * 2);
                }
            }
        }

        // mixed

        if((this.type == CardTypeEnum.spell && opponentCard.getType().name().equals("monster") || (this.type == CardTypeEnum.monster && opponentCard.getType().name().equals("spell")))) {
            if(opponentCard.getType().name().equals("Kraken")) {
                this.setDamage(0F);
            }

            if(this.element == CardElementEnum.Fire) {
                if(opponentCard.getElement().name().equals("Water") || opponentCard.getElement().name().equals("Regular") || opponentCard.getElement().name().equals("Normal")) {
                    this.setDamage(this.getDamage() / 2);
                }
            }

            if(this.element == CardElementEnum.Water) {
                if(opponentCard.getElement().name().equals("Fire") || opponentCard.getElement().name().equals("Regular") || opponentCard.getElement().name().equals("Normal")) {
                    this.setDamage(this.getDamage() * 2);
                }
            }

            if(this.element == CardElementEnum.Fire || this.element == CardElementEnum.Water) {
                if(opponentCard.getElement().name().equals("Regular")) {
                    this.setDamage(this.getDamage() * 2);
                }
            }

            if(this.element == CardElementEnum.Regular) {
                if(opponentCard.getElement().name().equals("Fire") || opponentCard.getElement().name().equals("Water")) {
                    this.setDamage(this.getDamage() / 2);
                }
            }
        }
        return this.damage;
    }
}