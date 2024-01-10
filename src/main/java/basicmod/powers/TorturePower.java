package basicmod.powers;

import basicmod.BasicMod;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.WrathStance;


public class TorturePower extends BasePower implements BetterOnApplyPowerPower {

    private static final String ID = BasicMod.makeID("TorturePower");


    public TorturePower(AbstractCreature owner) {
        super(ID, PowerType.BUFF, false, owner, null, -1);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.type == PowerType.DEBUFF && source.isPlayer && ((AbstractPlayer)source).stance instanceof WrathStance) {
            power.amount += power.amount;
            return stackAmount*2;
        }
        return stackAmount;
    }
}