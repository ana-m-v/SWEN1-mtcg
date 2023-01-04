package at.fhtw.mtcg.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeTest {
    @Test
    void testTradeNewTradeSpellCard() throws Exception {
        Trade trade = new Trade();
        trade.setTradingId("123xyz-555");
        trade.setCardId("123-456-789");
        trade.setCardType("spell");
        trade.setMinDamage(55);
        assertEquals("123xyz-555", trade.getTradingId());
        assertEquals("123-456-789", trade.getCardId());
        assertEquals("spell", trade.getCardType());
        assertEquals(55, trade.getMinDamage());
    }

    @Test
    void testTradeNewTradeMonsterCard() throws Exception {
        Trade trade = new Trade();
        trade.setTradingId("lkjh-3456");
        trade.setCardId("0987-7654");
        trade.setCardType("monster");
        trade.setMinDamage(77);
        assertEquals("lkjh-3456", trade.getTradingId());
        assertEquals("0987-7654", trade.getCardId());
        assertEquals("monster", trade.getCardType());
        assertEquals(77, trade.getMinDamage());
    }
}