package basicmod.cards.greenpurple;

import basicmod.HugYouColors;
import basicmod.cards.DualCard;
import basicmod.util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;

import static basicmod.HugYouColors.makeID;

public class DoNotWorry extends DualCard {

    // Gain 21 (27) Block. Enter Calm. Discard your hand.
    private final static CardInfo cardInfo = new CardInfo(
            "DoNotWorry", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            3, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_GP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);

    public static final int BLOCK = 21;
    public static final int UPG_BLOCK = 6;

    
    public DoNotWorry() {
        super(cardInfo, CardColor.GREEN, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, p.hand.size(), true));
        addToBot(new GainBlockAction(p, block));
        addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
    }
}