package hugyoucolors.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class CleverFollowUpAction extends AbstractGameAction {

    public CleverFollowUpAction(int reduction) {
        this.amount = reduction;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.25F));
        this.tickDuration();
        if (this.isDone) {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                addToTop(new ReduceCostForTurnAction(c, this.amount));
            }
        }
    }
}
