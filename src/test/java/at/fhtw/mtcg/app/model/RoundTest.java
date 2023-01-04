package at.fhtw.mtcg.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    void testKraken() throws Exception {
        Card cardKraken = new Card("123", CardNameEnum.Kraken, CardTypeEnum.monster, CardElementEnum.Regular, 45F);
        Card cardFireSpell = new Card("456", CardNameEnum.FireSpell, CardTypeEnum.spell, CardElementEnum.Fire, 90F);

        Round round = new Round("Lolek", "Bolek", cardKraken, cardFireSpell);
        round.calculateDamage();

        assertEquals(45, cardKraken.getDamage());
    }

    @Test
    void testWaterVSFireSpell() throws Exception {
        Card cardWaterSpell = new Card("123", CardNameEnum.WaterSpell, CardTypeEnum.spell, CardElementEnum.Water, 45F);
        Card cardFireSpell = new Card("456", CardNameEnum.FireSpell, CardTypeEnum.spell, CardElementEnum.Fire, 90F);

        Round round = new Round("Lolek", "Bolek", cardWaterSpell, cardFireSpell);
        round.calculateDamage();

        assertEquals(90, cardWaterSpell.getDamage());
        assertEquals(0, cardFireSpell.getDamage());
    }

    @Test
    void testWaterVSFireMonster() throws Exception {
        Card cardWaterSpell = new Card("123", CardNameEnum.WaterGoblin, CardTypeEnum.monster, CardElementEnum.Water, 45F);
        Card cardFireSpell = new Card("456", CardNameEnum.FireTroll, CardTypeEnum.monster, CardElementEnum.Fire, 90F);

        Round round = new Round("Lolek", "Bolek", cardWaterSpell, cardFireSpell);
        round.calculateDamage();

        assertEquals(90, cardWaterSpell.getDamage());
        assertEquals(0, cardFireSpell.getDamage());
    }

    @Test
    void testGoblinVSDragon() throws Exception {
        Card cardGoblin = new Card("123", CardNameEnum.FireGoblin, CardTypeEnum.monster, CardElementEnum.Fire, 100F);
        Card cardDragon = new Card("456", CardNameEnum.Dragon, CardTypeEnum.monster, CardElementEnum.Regular, 10F);

        Round round = new Round("Lolek", "Bolek", cardGoblin, cardDragon);
        round.fight();
//        round.calculateDamage();

        assertEquals(0, cardGoblin.getDamage());
        assertEquals(10, cardDragon.getDamage());
    }

    @Test
    void testKnightWater() throws Exception {
        Card cardKnight = new Card("123", CardNameEnum.Knight, CardTypeEnum.monster, CardElementEnum.Regular, 100F);
        Card cardWaterElf = new Card("456", CardNameEnum.WaterSpell, CardTypeEnum.spell, CardElementEnum.Water, 10F);

        Round round = new Round("Lolek", "Bolek", cardKnight, cardWaterElf);
        round.calculateDamage();

        assertEquals(0, cardKnight.getDamage());
        assertEquals(10, cardWaterElf.getDamage());
    }

    @Test
    void testPureMonsterFight() throws Exception {
        Card cardMonsterKnight = new Card("123", CardNameEnum.Knight, CardTypeEnum.monster, CardElementEnum.Regular, 100F);
        Card cardMonsterKraken = new Card("456", CardNameEnum.Kraken, CardTypeEnum.monster, CardElementEnum.Regular, 10F);

        Round round = new Round("Lolek", "Bolek", cardMonsterKnight, cardMonsterKraken);
        round.calculateDamage();

        assertEquals(100, cardMonsterKnight.getDamage());
        assertEquals(0, cardMonsterKraken.getDamage());
    }

    @Test
    void testPureSpellFight() throws Exception {
        Card cardWaterSpell = new Card("123", CardNameEnum.WaterSpell, CardTypeEnum.spell, CardElementEnum.Water, 80F);
        Card cardRegularSpell = new Card("456", CardNameEnum.RegularSpell, CardTypeEnum.spell, CardElementEnum.Regular, 150F);

        Round round = new Round("Lolek", "Bolek", cardWaterSpell, cardRegularSpell);
        round.calculateDamage();

        assertEquals(0, cardWaterSpell.getDamage());
        assertEquals(150, cardRegularSpell.getDamage());
    }
}