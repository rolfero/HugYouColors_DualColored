package hugyoutwentydualcolors.cards.redpurple;

import hugyoutwentydualcolors.HugYouColors;
import hugyoutwentydualcolors.cards.DualCard;
import hugyoutwentydualcolors.util.CardInfo;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static hugyoutwentydualcolors.HugYouColors.makeID;


public class ReadInBlood extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ReadInBlood", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_RP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);
    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 2;
    private static final int HPLOSS = 1;

    
    public ReadInBlood() {
        super(cardInfo, CardColor.RED, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HPLOSS));
        addToBot(new ScryAction(magicNumber));
    }
}