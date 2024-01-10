package basicmod.cards.bluepurple;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import basicmod.BasicMod;
import basicmod.cards.DualCard;
import basicmod.powers.StratagemPower;
import basicmod.powers.StratagemUpgradedPower;
import basicmod.util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.BasicMod.makeID;

@SuppressWarnings("ALL")
public class Stratagem extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Stratagem", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            2, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            BasicMod.Enums.CARD_DUAL_BP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    @SuppressWarnings("unused")
    public static final String ID = makeID(cardInfo.baseId);

    @SuppressWarnings("unused")
    public Stratagem() {
        super(cardInfo, CardColor.BLUE, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        MultiCardPreview.add(this, new Smite(), new Safety());
    }

    @Override
    public void upgrade() {
        super.upgrade();
        MultiCardPreview.add(this, new Insight());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new StratagemUpgradedPower(p, 1), 1));
        } else {
            addToBot(new ApplyPowerAction(p, p, new StratagemPower(p, 1), 1));
        }
    }
}