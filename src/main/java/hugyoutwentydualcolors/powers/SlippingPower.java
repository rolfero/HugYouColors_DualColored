package hugyoutwentydualcolors.powers;

import hugyoutwentydualcolors.HugYouColors;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DexterityPower;


public class SlippingPower extends BasePower {

    public static final String ID = HugYouColors.makeID("SlippingPower");

    
    public SlippingPower(AbstractCreature owner, int amt) {
        super(ID, PowerType.BUFF, false, owner, null, amt);
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, this.amount), this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}