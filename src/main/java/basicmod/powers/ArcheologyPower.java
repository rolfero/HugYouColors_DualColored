package basicmod.powers;

import basicmod.BasicMod;
import basicmod.actions.ArcheologyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArcheologyPower extends BasePower {

    public static final String ID = BasicMod.makeID("ArcheologyPower");


    public static final Logger logger = LogManager.getLogger(BasicMod.modID); //Used to output to the console.


    public ArcheologyPower(AbstractCreature owner, int amt) {
        super(ID, PowerType.BUFF, false, owner, null, amt);
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new ArcheologyAction(this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}