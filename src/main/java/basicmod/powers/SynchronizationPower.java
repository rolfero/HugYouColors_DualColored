package basicmod.powers;

import basicmod.BasicMod;
import basicmod.actions.SyncAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SuppressWarnings("ALL")
public class SynchronizationPower extends BasePower {

    public static final String ID = BasicMod.makeID("SynchronizationPower");

    @SuppressWarnings("unused")
    public SynchronizationPower(AbstractCreature owner, int amount) {
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
        this.description = DESCRIPTIONS[0];
    }

}