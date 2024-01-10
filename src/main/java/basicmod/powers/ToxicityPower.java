package basicmod.powers;

import basicmod.BasicMod;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;


public class ToxicityPower extends BasePower {

    private static final String ID = BasicMod.makeID("ToxicityPower");

    
    public ToxicityPower(AbstractCreature owner) {
        super(ID, PowerType.BUFF, false, owner, null, -1);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type != DamageInfo.DamageType.NORMAL)
            return;
        if (!info.owner.isPlayer && damageAmount <= 0)
            return;
        int totalDamage = 0;
        if (target.hasPower(PoisonPower.POWER_ID)) totalDamage += target.getPower(PoisonPower.POWER_ID).amount;
        if (target.hasPower(WeakPower.POWER_ID)) totalDamage += target.getPower(WeakPower.POWER_ID).amount;
        if (target.hasPower(VulnerablePower.POWER_ID)) totalDamage += target.getPower(VulnerablePower.POWER_ID).amount;
        if (totalDamage > 0) {
            addToBot(new DamageAction(target, new DamageInfo(info.owner, totalDamage, DamageInfo.DamageType.THORNS)));
            flash();
        }
    }
}