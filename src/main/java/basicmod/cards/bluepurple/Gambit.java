package basicmod.cards.bluepurple;

import basemod.BaseMod;
import basicmod.BasicMod;
import basicmod.cards.DualCard;
import basicmod.util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static basicmod.BasicMod.makeID;

@SuppressWarnings("ALL")
public class Gambit extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Gambit", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            BasicMod.Enums.CARD_DUAL_BP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    @SuppressWarnings("unused")
    public static final String ID = makeID(cardInfo.baseId);

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 2;

    @SuppressWarnings("unused")
    public Gambit() {
        super(cardInfo, CardColor.BLUE, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int max = BaseMod.MAX_HAND_SIZE - p.hand.size();
                int dmg = 0;
                ArrayList<AbstractCard> cardsToMove = new ArrayList<>();

                for (AbstractCard card : p.drawPile.group) {
                    if (max >= dmg && card.costForTurn == 0) {
                        cardsToMove.add(card);
                        dmg++;
                    }
                }

                for (AbstractCard card : cardsToMove) {
                    p.drawPile.moveToHand(card);
                }

                for (int i = 0; i<dmg; ++i) {
                    addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
                }

                this.isDone = true;
            }
        });
    }
}