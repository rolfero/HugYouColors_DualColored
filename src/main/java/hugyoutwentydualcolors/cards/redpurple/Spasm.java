package hugyoutwentydualcolors.cards.redpurple;

import hugyoutwentydualcolors.HugYouColors;
import hugyoutwentydualcolors.cards.DualCard;
import hugyoutwentydualcolors.util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

import static hugyoutwentydualcolors.HugYouColors.makeID;


public class Spasm extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Spasm", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_RP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);
    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 3;

    
    public Spasm() {
        super(cardInfo, CardColor.RED, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (p.stance.ID.equals(CalmStance.STANCE_ID)) {
                    addToTop(new ChangeStanceAction(NeutralStance.STANCE_ID));
                }
                this.isDone = true;
            }
        });
    }
}