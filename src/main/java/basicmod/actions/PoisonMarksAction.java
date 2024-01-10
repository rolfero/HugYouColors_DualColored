package basicmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;

public class PoisonMarksAction extends AbstractGameAction {


    public PoisonMarksAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.hasPower(MarkPower.POWER_ID)) {
                addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new PoisonPower(mo, AbstractDungeon.player, mo.getPower(MarkPower.POWER_ID).amount), mo.getPower(MarkPower.POWER_ID).amount, AttackEffect.POISON));
            }
        }

        this.isDone = true;
    }
}
