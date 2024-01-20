package hugyoucolors.actions;

import hugyoucolors.HugYouColors;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class GlobalityAction extends AbstractGameAction {

    //Exhaust any number of cards in your hand. Draw that many cards and gain energy equal to their cost.
    private final AbstractPlayer p;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(HugYouColors.makeID("GlobalityAction"));
    private static final String[] TEXT = uiStrings.TEXT;


    
    public GlobalityAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                int retrievedCards = 0;
                for(Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToExhaustPile(c)) {
                    c = var1.next();
                    if (c.costForTurn == -1) {
                        this.addToTop(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));
                    } else if (c.costForTurn > 0) {
                        this.addToTop(new GainEnergyAction(c.costForTurn));
                    }
                    retrievedCards++;
                }

                this.addToTop(new FastDrawCardAction(this.p, retrievedCards));

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }

            this.tickDuration();
        }
    }
}
