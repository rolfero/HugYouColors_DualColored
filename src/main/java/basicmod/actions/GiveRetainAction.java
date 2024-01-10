package basicmod.actions;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GiveRetainAction extends AbstractGameAction {

    final AbstractPlayer p;
    private final ArrayList<AbstractCard> cannotRetain = new ArrayList<>();

    @SuppressWarnings("unused")
    public GiveRetainAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        this.amount = 1;
    }

    @Override
    public void update() {
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }



            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            } else {



                for (AbstractCard handCard : this.p.hand.group) {
                    if (handCard.selfRetain) {
                        cannotRetain.add(handCard);
                    }
                }

                if (cannotRetain.size() == this.p.hand.size()) {
                    this.isDone = true;
                    return;
                }

                this.p.hand.group.removeAll(this.cannotRetain);

                if (this.p.hand.size() <= 0) {
                    this.returnCards();
                    this.isDone = true;
                    return;
                }

                if (this.p.hand.size() == 1) {
                    c = this.p.hand.getTopCard();
                    //give it retain here
                    CardModifierManager.addModifier(c, new RetainMod());
                    this.returnCards();
                    this.tickDuration();
                    this.isDone = true;
                    return;
                }

                AbstractDungeon.handCardSelectScreen.open("retain this combat.", 1, false); //TODO: Localize the text
                this.tickDuration();
                return;
            }

        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard abstractCard : AbstractDungeon.handCardSelectScreen.selectedCards.group) {

                CardModifierManager.addModifier(abstractCard, new RetainMod());
                this.p.hand.addToTop(abstractCard);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

        }

        this.tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotRetain) {
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }
}