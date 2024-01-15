package basicmod.powers;

import basicmod.HugYouColors;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.stances.WrathStance;


public class TorturePower extends BasePower implements BetterOnApplyPowerPower, OnReceivePowerPower {

    private static final String ID = HugYouColors.makeID("TorturePower");


    public TorturePower(AbstractCreature owner) {
        super(ID, PowerType.BUFF, false, owner, null, -1);
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.type == PowerType.DEBUFF && source.isPlayer && AbstractDungeon.player.stance instanceof WrathStance) {
            power.amount += power.amount;
            return stackAmount*2;
        }
        return stackAmount;
    }
    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power.type == PowerType.DEBUFF && target.isPlayer && AbstractDungeon.player.stance instanceof WrathStance) {
            power.amount += power.amount;
            return stackAmount*2;
        }
        return power.amount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}