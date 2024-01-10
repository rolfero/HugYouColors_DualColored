package basicmod.actions;

import basicmod.BasicMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

@SuppressWarnings("unused")
public class BreachAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(BasicMod.makeID("BreachAction"));
    private static final String[] TEXT = uiStrings.TEXT;

    private final boolean chooseAny;
    private final AbstractPlayer p;

    public BreachAction(AbstractPlayer p, boolean chooseAny, int amount) {
        this.p = p;
        this.chooseAny = chooseAny;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.CARD_MANIPULATION;

        this.amount = amount;
    }

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
                        this.p.drawPile.moveToDiscardPile(c);
                    }
                    this.isDone = true;
                    return;

                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
                    this.tickDuration();
                }

            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, true, TEXT[0]);
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
