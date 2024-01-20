package hugyoucolors.cards.redgreen;

import hugyoucolors.HugYouColors;
import hugyoucolors.cards.DualCard;
import hugyoucolors.util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static hugyoucolors.HugYouColors.makeID;


public class BreakFingers extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BreakFingers", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_RG_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    
    public BreakFingers() {
        super(cardInfo, CardColor.RED, CardColor.GREEN); //change this to super(cardInfo, true); for updating description
        setMagic(MAGIC, UPG_MAGIC);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (m.hasPower(WeakPower.POWER_ID) && m.getPower(WeakPower.POWER_ID).amount > 0) {
                    addToTop(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
                }
                this.isDone = true;
            }
        });
    }
}