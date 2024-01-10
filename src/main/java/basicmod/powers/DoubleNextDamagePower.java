package basicmod.powers;

import basicmod.BasicMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SuppressWarnings("ALL")
public class DoubleNextDamagePower extends BasePower {

    private static final String ID = BasicMod.makeID("DoubleNextDamagePower");

    @SuppressWarnings("unused")
    public DoubleNextDamagePower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.amount > 0 && card.type.equals(AbstractCard.CardType.ATTACK)) {
            this.flash();
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    amount = 0;
                    isDone = true;
                }
            });
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return type == DamageInfo.DamageType.NORMAL ? damage * (float)Math.pow(2.0F, this.amount) : damage;
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[1] + (int) Math.pow(2.0F, this.amount) + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0];
        }
    }
}