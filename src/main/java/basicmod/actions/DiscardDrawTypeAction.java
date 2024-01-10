package basicmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SuppressWarnings("ALL")
public class DiscardDrawTypeAction extends AbstractGameAction {

    final AbstractPlayer p;

    @SuppressWarnings("unused")
    public DiscardDrawTypeAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        this.amount = 1;
    }

    @Override
    public void update() {
        AbstractCard c = null;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.size() == 1) {
                c = AbstractDungeon.player.hand.getTopCard();
                this.p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                this.tickDuration();
                return;
            } else if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open("discard.", 1, false);//TODO: Localize the text
                this.tickDuration();
                return;
            }

        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard abstractCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c = abstractCard;
                this.p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        if (c != null) {
            addToTop(new DrawPileToHandAction(1, c.type));
        }

        this.tickDuration();
    }
}
