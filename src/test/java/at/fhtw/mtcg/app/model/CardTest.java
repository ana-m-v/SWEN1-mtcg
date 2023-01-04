package at.fhtw.mtcg.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void testCreateMonsterCard() throws Exception {
        Card card = new Card("123abc", CardNameEnum.valueOf("Kraken"), CardTypeEnum.valueOf("monster"), CardElementEnum.valueOf("Regular"), 25.0F);
        assertEquals("monster", card.getType().name());
    }

    @Test
    void testCreateSpellCard() throws Exception {
        Card card = new Card("123abc", CardNameEnum.valueOf("WaterSpell"), CardTypeEnum.valueOf("spell"), CardElementEnum.valueOf("Water"), 25.0F);
        assertEquals("spell", card.getType().name());
    }

    @Test
    void testCreateMonsterMixCard() throws Exception {
        Card card = new Card("123abc", CardNameEnum.valueOf("FireElf"), CardTypeEnum.valueOf("monster"), CardElementEnum.valueOf("Fire"), 55.0F);
        assertEquals("monster", card.getType().name());
        assertEquals("Fire", card.getElement().name());
    }
}