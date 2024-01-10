package basicmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SuppressWarnings("ALL")
public class BreachAction extends AbstractGameAction {

    private boolean chooseAny = false;
    private final AbstractPlayer p;

    @SuppressWarnings("unused")
    public BreachAction(AbstractPlayer p, boolean chooseAny, int amount) {
        this.p = p;
        this.chooseAny = chooseAny;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.CARD_MANIPULATION;

        this.amount = amount;
    }

    //TODO: Needs to show the card "going" to the discard pile.

    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_XFAST) {

            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard c : this.p.drawPile.group) {
                tmp.addToRandomSpot(c);
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            //start of action
            if (!this.chooseAny) {

                if (tmp.size() <= this.amount) {
                    //choose all cards
                    for (AbstractCard c : tmp.group) {
                        //TODO: Show this?
                        this.p.drawPile.moveToDiscardPile(c);
                    }
                    this.isDone = true;
                    return;

                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose cards to discard", false); //TODO: Localize this text
                    this.tickDuration();
                }

            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, true, "Choose cards to discard"); //TODO: Localize this text
                this.tickDuration();
            }


        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    this.p.drawPile.moveToDiscardPile(c);
                }
                this.isDone = true;
                return;
            }
        }

        tickDuration();

    }
}
