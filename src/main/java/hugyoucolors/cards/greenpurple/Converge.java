package hugyoucolors.cards.greenpurple;

import hugyoucolors.HugYouColors;
import hugyoucolors.cards.DualCard;
import hugyoucolors.util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static hugyoucolors.HugYouColors.makeID;

public class Converge extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Converge", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            HugYouColors.Enums.CARD_DUAL_GP_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    
    public Converge() {
        super(cardInfo, CardColor.GREEN, CardColor.PURPLE); //change this to super(cardInfo, true); for updating description
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m2.isDeadOrEscaped()) {
                        addToTop(new GainBlockAction(p, p, block));
                    }
                }
                this.isDone = true;
            }
        });
    }
}