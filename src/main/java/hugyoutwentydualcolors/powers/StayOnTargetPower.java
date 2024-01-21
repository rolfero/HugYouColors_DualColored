package hugyoutwentydualcolors.powers;

import hugyoutwentydualcolors.HugYouColors;
import hugyoutwentydualcolors.patches.DiscardHookPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class StayOnTargetPower extends BasePower implements DiscardHookPatch.OnDiscardHook {

    private static final String ID = HugYouColors.makeID("StayOnTargetPower");

    
    public StayOnTargetPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    public void trigger() {
        flash();
        addToTop(new ApplyPowerAction(this.owner, this.owner, new LoseStrengthPower(this.owner, this.amount), this.amount));
        addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
    }

    @Override
    public void onExhaust(AbstractCard card) {
        trigger();
    }

    @Override
    public void onManualDiscard() {
        trigger();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}