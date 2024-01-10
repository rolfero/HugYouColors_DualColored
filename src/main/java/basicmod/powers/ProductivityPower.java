package basicmod.powers;

import basicmod.BasicMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

@SuppressWarnings("ALL")
public class ProductivityPower extends BasePower {

    public static final String ID = BasicMod.makeID("ProductivityPower");

    private boolean playedPowerThisTurn = false;

    @SuppressWarnings("unused")
    public ProductivityPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        playedPowerThisTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.POWER && !playedPowerThisTurn) {
            addToBot(new ApplyPowerAction(owner, owner, new FocusPower(owner, amount), amount));
            playedPowerThisTurn = true;
            flash();
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}