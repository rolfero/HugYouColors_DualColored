package basicmod.actions;

import basicmod.BasicMod;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class LevitateAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(BasicMod.makeID("LevitateAction"));
    private static final String[] TEXT = uiStrings.TEXT;

    final AbstractPlayer p;

    
    public LevitateAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 10, true);
                this.tickDuration();
                return;
            }

        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            int cardsDiscarded = 0;

            for (AbstractCard abstractCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                cardsDiscarded++;
                this.p.hand.moveToDiscardPile(abstractCard);
                abstractCard.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

            addToTop(new TriggerPassiveAction(cardsDiscarded));
        }


        this.tickDuration();
    }
}
