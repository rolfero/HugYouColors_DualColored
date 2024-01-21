package hugyoutwentydualcolors.powers;

import hugyoutwentydualcolors.HugYouColors;
import hugyoutwentydualcolors.actions.SyncAction;
import com.megacrit.cardcrawl.core.AbstractCreature;


public class SynchronizationUpgradedPower extends BasePower {

    public static final String ID = HugYouColors.makeID("SynchronizationUpgradedPower");


    public SynchronizationUpgradedPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) return;
        for (int i = 0; i < amount; ++i) {
            addToBot(new SyncAction());
        }
    }

    @Override
    public void updateDescription() {
        this.description = this.amount == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

}