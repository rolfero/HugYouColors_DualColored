package basicmod.powers;

import basicmod.BasicMod;
import basicmod.actions.LevitateAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SuppressWarnings("ALL")
public class LevitatePower extends BasePower {

    public static final String ID = BasicMod.makeID("LevitatePower"); //SHOULD NOT BE ABLE TO STACK.

    @SuppressWarnings("unused")
    public LevitatePower(AbstractCreature owner) {
        super(ID, PowerType.BUFF, false, owner, null, -1);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new LevitateAction());
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }

}