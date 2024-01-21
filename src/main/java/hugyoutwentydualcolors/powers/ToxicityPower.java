package hugyoutwentydualcolors.powers;

import hugyoutwentydualcolors.HugYouColors;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;


public class ToxicityPower extends BasePower {

    private static final String ID = HugYouColors.makeID("ToxicityPower");

    
    public ToxicityPower(AbstractCreature owner, int amt) {
        super(ID, PowerType.BUFF, false, owner, null, amt);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type != DamageInfo.DamageType.NORMAL)
            return;
        if (target.isPlayer || damageAmount <= 0)
            return;
        int totalDamage = 0;
        for (AbstractPower p : target.powers) {
            if (p.type == PowerType.DEBUFF) {
                totalDamage += this.amount;
            }
        }
        if (totalDamage > 0) {
            addToBot(new DamageAction(target, new DamageInfo(info.owner, totalDamage, DamageInfo.DamageType.THORNS)));
            flash();
        }
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onInflictDamage(info, damageAmount, target);
    }
}