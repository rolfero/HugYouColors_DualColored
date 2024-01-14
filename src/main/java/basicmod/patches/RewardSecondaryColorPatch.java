package basicmod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;

import static basicmod.HugYouColors.*;


public class RewardSecondaryColorPatch {

    private static final int PRIMARY_CHANCE = 75; //chance in % to get the primary color. Otherwise, its secondary color.

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getCard",
            paramtypez = {AbstractCard.CardRarity.class, Random.class}
    )
    public static class RewardCards1 {

        public static SpireReturn<AbstractCard> Prefix(AbstractCard.CardRarity rarity, Random rng) {
            if (!currentRunActive) return SpireReturn.Continue();
            if (rng.random(99) >= (100-PRIMARY_CHANCE)) return SpireReturn.Continue();

            switch (rarity) {
                case COMMON:
                    return SpireReturn.Return(secondaryCommonCardPool.getRandomCard(rng));
                case UNCOMMON:
                    return SpireReturn.Return(secondaryUncommonCardPool.getRandomCard(rng));
                case RARE:
                    return SpireReturn.Return(secondaryRareCardPool.getRandomCard(rng));
                case CURSE:
                    return SpireReturn.Continue(); //we dont do a secondary curse pool
                default:
                    logger.info("No rarity on getCard in Abstract Dungeon (patch rolled Secondary card pool)");
                    return null;
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getCard",
            paramtypez = {AbstractCard.CardRarity.class}
    )
    public static class RewardCards2 {

        public static SpireReturn<AbstractCard> Prefix(AbstractCard.CardRarity rarity) {
            if (!currentRunActive) return SpireReturn.Continue();
            if (AbstractDungeon.cardRng.random(99) >= (100-PRIMARY_CHANCE)) return SpireReturn.Continue();

            switch (rarity) {
                case COMMON:
                    return SpireReturn.Return(secondaryCommonCardPool.getRandomCard(true));
                case UNCOMMON:
                    return SpireReturn.Return(secondaryUncommonCardPool.getRandomCard(true));
                case RARE:
                    return SpireReturn.Return(secondaryRareCardPool.getRandomCard(true));
                case CURSE:
                    return SpireReturn.Continue(); //we dont do a secondary curse pool
                default:
                    logger.info("No rarity on getCard in Abstract Dungeon (patch rolled Secondary card pool)");
                    return null;
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getCardWithoutRng"
    )
    public static class RewardCards3 {

        public static SpireReturn<AbstractCard> Prefix(AbstractCard.CardRarity rarity) {
            if (!currentRunActive) return SpireReturn.Continue();
            if (MathUtils.random(99) >= (100-PRIMARY_CHANCE)) return SpireReturn.Continue();

            switch (rarity) {
                case COMMON:
                    return SpireReturn.Return(secondaryCommonCardPool.getRandomCard(false));
                case UNCOMMON:
                    return SpireReturn.Return(secondaryUncommonCardPool.getRandomCard(false));
                case RARE:
                    return SpireReturn.Return(secondaryRareCardPool.getRandomCard(false));
                case CURSE:
                    return SpireReturn.Continue(); //we dont do a secondary curse pool
                default:
                    logger.info("No rarity on getCardWithoutRng in Abstract Dungeon (patch rolled Secondary card pool)");
                    return null;
            }
        }
    }

    private static AbstractCard getCardFromPool(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng) {
        AbstractCard retVal;
        switch (rarity) {
            case RARE:
                retVal = secondaryRareCardPool.getRandomCard(type, useRng);
                if (retVal != null) {
                    return retVal;
                } else {
                    logger.info("ERROR: Could not find Rare card of type: " + type.name());
                }
            case UNCOMMON:
                retVal = secondaryUncommonCardPool.getRandomCard(type, useRng);
                if (retVal != null) {
                    return retVal;
                } else if (type == AbstractCard.CardType.POWER) {
                    return getCardFromPool(AbstractCard.CardRarity.RARE, type, useRng);
                } else {
                    logger.info("ERROR: Could not find Uncommon card of type: " + type.name());
                }
            case COMMON:
                retVal = secondaryCommonCardPool.getRandomCard(type, useRng);
                if (retVal != null) {
                    return retVal;
                } else if (type == AbstractCard.CardType.POWER) {
                    return getCardFromPool(AbstractCard.CardRarity.UNCOMMON, type, useRng);
                } else {
                    logger.info("ERROR: Could not find Common card of type: " + type.name());
                }
            default:
                logger.info("ERROR: Default in getCardFromPool");
                return null;
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getCardFromPool"
    )
    public static class RewardCards4 {

        public static SpireReturn<AbstractCard> Prefix(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng) {
            if (!currentRunActive) return SpireReturn.Continue();

            if (rarity == AbstractCard.CardRarity.CURSE) return SpireReturn.Continue();

            if ((useRng ? AbstractDungeon.cardRng.random(99) : MathUtils.random(99)) >= (100-PRIMARY_CHANCE)) return SpireReturn.Continue();

            return SpireReturn.Return(getCardFromPool(rarity, type, useRng));
        }
    }
}
