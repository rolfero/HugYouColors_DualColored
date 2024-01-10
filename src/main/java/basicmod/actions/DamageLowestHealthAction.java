package basicmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SuppressWarnings("unused")
public class DamageLowestHealthAction extends AbstractGameAction {

    private final DamageInfo damageInfo;
    private final AttackEffect attackEffect;

    public DamageLowestHealthAction(DamageInfo info, AttackEffect effect) {

        damageInfo = info;
        attackEffect = effect;

        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.CARD_MANIPULATION;

    }

    @Override
    public void update() {

        AbstractMonster target = null;

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                if (target == null) {
                    target = m;
                } else if (m.currentHealth < target.currentHealth) {
                    target = m;
                }
            }
        }

        addToTop(new DamageAction(target, damageInfo, attackEffect));

        this.isDone = true;
    }
}
