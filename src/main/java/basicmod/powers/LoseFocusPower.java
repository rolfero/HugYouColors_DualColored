package basicmod.powers;

import basicmod.BasicMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.FocusPower;

@SuppressWarnings("ALL")
public class LoseFocusPower extends BasePower {

    private static final String ID = BasicMod.makeID("LoseFocusPower");

    @SuppressWarnings("unused")
    public LoseFocusPower(AbstractCreature owner, int amt) {
        super(ID, PowerType.DEBUFF, false, owner, null, amt);
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}