package at.fhtw.mtcg.app.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Trade {
    @JsonAlias({"Id"})
    private String tradingId;

    @JsonAlias({"CardToTrade"})
    private String cardId;

    @JsonAlias({"Type"})
    private String cardType;

    @JsonAlias({"MinimumDamage"})
    private Integer minDamage;

    public Trade() {};

    public Trade(String tradingId, String cardId, String cardType, Integer minDamage) {
        this.tradingId = tradingId;
        this.cardId = cardId;
        this.cardType = cardType;
        this.minDamage = minDamage;
    }

    public String getTradingId() {
        return tradingId;
    }

    public void setTradingId(String tradingId) {
        this.tradingId = tradingId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(Integer minDamage) {
        this.minDamage = minDamage;
    }
}