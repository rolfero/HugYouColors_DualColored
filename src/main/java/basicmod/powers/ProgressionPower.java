package basicmod.powers;

import basicmod.HugYouColors;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;


public class ProgressionPower extends BasePower {

    public static final String ID = HugYouColors.makeID("ProgressionPower");

    
    public ProgressionPower(AbstractCreature owner, int amt) {
        super(ID, PowerType.BUFF, false, owner, null, amt);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for (int i = 0; i<this.amount; ++i) {
            addToBot(new UpgradeRandomCardAction());
        }
    }

    @Override
    public void updateDescription() {
        this.description = this.amount == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

}