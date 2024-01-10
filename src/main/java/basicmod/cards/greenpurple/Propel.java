package basicmod.cards.greenpurple;

import basicmod.BasicMod;
import basicmod.cards.DualCard;
import basicmod.util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.BasicMod.makeID;

@SuppressWarnings("ALL")
public class Propel extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Propel", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            BasicMod.Enums.CARD_DUAL_GP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    @SuppressWarnings("unused")
    public static final String ID = makeID(cardInfo.baseId);
    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    @SuppressWarnings("unused")
    public Propel() {
        super(cardInfo, CardColor.GREEN, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setDamage(DAMAGE, UPG_DAMAGE);
        cardsToPreview = new Insight();
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new MakeTempCardInDrawPileAction(new Insight(), 1, true, true));
    }
}