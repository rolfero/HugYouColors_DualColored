package basicmod.cards.redblue;

import basicmod.HugYouColors;
import basicmod.cards.DualCard;
import basicmod.util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.HugYouColors.makeID;


public class EnergySword extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EnergySword", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            4, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_RB_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);
    private static final int DAMAGE = 17;
    private static final int UPG_DAMAGE = 4;


    
    public EnergySword() {
        super(cardInfo, CardColor.RED, CardColor.BLUE); //change this to super(cardInfo, true); for updating description
        setDamage(DAMAGE, UPG_DAMAGE);

        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.configureCostsOnNewCard();
        }
    }

    public void configureCostsOnNewCard() {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.POWER) {
                this.updateCost(-1);
            }
        }
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            this.updateCost(-1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
}