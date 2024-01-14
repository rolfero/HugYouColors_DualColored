package basicmod.cards.redgreen;

import basicmod.HugYouColors;
import basicmod.cards.DualCard;
import basicmod.util.CardInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AccuracyPower;

import static basicmod.HugYouColors.makeID;


public class ShivStrike extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ShivStrike", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_RG_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);

    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 2;

    
    public ShivStrike() {
        super(cardInfo, CardColor.RED, CardColor.GREEN); //change this to super(cardInfo, true); for updating description
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        tags.add(CardTags.STRIKE);
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Accuracy")) {
            this.baseDamage += (AbstractDungeon.player.getPower("Accuracy")).amount;
            this.damage = this.baseDamage;
        }
    }

    @SpirePatch(
            clz = AccuracyPower.class,
            method = "updateExistingShivs"
    )
    public static class AccuracyPatch {
        
        public static void Prefix(AccuracyPower __instance) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof ShivStrike) {
                    c.baseDamage = DAMAGE + __instance.amount;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof ShivStrike) {
                    c.baseDamage = DAMAGE + __instance.amount;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof ShivStrike) {
                    c.baseDamage = DAMAGE + __instance.amount;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c instanceof ShivStrike) {
                    c.baseDamage = DAMAGE + __instance.amount;
                }
            }
        }
    }

    @SpirePatch(
            clz = AccuracyPower.class,
            method = "onDrawOrDiscard"
    )
    public static class AccuracyDrawPatch {
        
        public static void Prefix(AccuracyPower __instance) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof ShivStrike) {
                    c.baseDamage = DAMAGE + __instance.amount;
                }
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; ++i) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }
}