package basicmod.powers;

import basicmod.HugYouColors;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;


public class ToxicityPower extends BasePower {

    private static final String ID = HugYouColors.makeID("ToxicityPower");

    
    public ToxicityPower(AbstractCreature owner, int amt) {
        super(ID, PowerType.BUFF, false, owner, null, amt);
        this.amount2 = AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter((x) -> x instanceof Slimed).toArray().length*this.amount;
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card instanceof Slimed) {
            this.amount2 += this.amount;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!m.isDeadOrEscaped()) {
                            addToTop(new ApplyPowerAction(m, source, new PoisonPower(m, source, amount2), amount2));
                        }
                    }
                    this.isDone = true;
                }
            });
            updateDescription();
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new MakeTempCardInDrawPileAction(new Slimed(), this.amount, true, true));
        this.flash();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3];
    }
}