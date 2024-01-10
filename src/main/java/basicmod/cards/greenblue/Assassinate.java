package basicmod.cards.greenblue;

import basicmod.BasicMod;
import basicmod.cards.DualCard;
import basicmod.util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.random.Random;

import static basicmod.BasicMod.makeID;

public class Assassinate extends DualCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Assassinate", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            BasicMod.Enums.CARD_DUAL_GB_COLOR //BasicMod.Enums.CARD_DUAL_GR_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    
    public static final String ID = makeID(cardInfo.baseId);
    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    
    public Assassinate() {
        super(cardInfo, CardColor.GREEN, CardColor.BLUE); //change this to super(cardInfo, true); for updating description
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i<magicNumber; ++i) {
            addToBot(new ChannelAction(new Dark()));
        }
        addToBot(new AbstractGameAction() {
                     @Override
                     public void update() {
                         for (AbstractOrb o : p.orbs) {
                             if (o instanceof Dark) {
                                 addToTop(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), getRandomSlash()));
                             }
                         }
                         this.isDone = true;
                     }
                 }
        );
    }

    public AbstractGameAction.AttackEffect getRandomSlash() {
        Random rng = (AbstractDungeon.miscRng != null) ? AbstractDungeon.miscRng : new Random();
        int i = rng.random(3);
        if (i == 0) {
            return AbstractGameAction.AttackEffect.SLASH_VERTICAL;
        } else if (i == 1) {
            return AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
        } else if (i == 2) {
            return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
        } else if (i == 3) {
            return AbstractGameAction.AttackEffect.SLASH_HEAVY;
        }
        return AbstractGameAction.AttackEffect.NONE;
    }
}