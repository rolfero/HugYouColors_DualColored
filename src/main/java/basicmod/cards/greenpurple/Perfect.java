package basicmod.cards.greenpurple;

import basicmod.BasicMod;
import basicmod.cards.DualCard;
import basicmod.util.CardInfo;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.DivinityStance;

import static basicmod.BasicMod.makeID;

@SuppressWarnings("ALL")
public class Perfect extends DualCard {

    // Retain. Enter Divinity. Exhaust.
    private final static CardInfo cardInfo = new CardInfo(
            "Perfect", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            5, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            BasicMod.Enums.CARD_DUAL_GP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    @SuppressWarnings("unused")
    public static final String ID = makeID(cardInfo.baseId);

    @SuppressWarnings("unused")
    public Perfect() {
        super(cardInfo, CardColor.GREEN, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setCostUpgrade(4);
        setSelfRetain(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(DivinityStance.STANCE_ID));
    }
}