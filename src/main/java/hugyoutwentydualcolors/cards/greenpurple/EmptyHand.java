package hugyoutwentydualcolors.cards.greenpurple;

import hugyoutwentydualcolors.HugYouColors;
import hugyoutwentydualcolors.actions.EmptyHandAction;
import hugyoutwentydualcolors.cards.DualCard;
import hugyoutwentydualcolors.util.CardInfo;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;

import static hugyoutwentydualcolors.HugYouColors.makeID;

public class EmptyHand extends DualCard {

    // Discard up to one (any number of) card(s). If you do, draw a (that many) card(s). Exit your stance.
    private final static CardInfo cardInfo = new CardInfo(
            "EmptyHand", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_GP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);

    public static final int MAGIC = 1;
    public static final int UPG_MAGIC = 1;

    
    public EmptyHand() {
        super(cardInfo, CardColor.GREEN, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EmptyHandAction(p, magicNumber));
        addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
    }
}