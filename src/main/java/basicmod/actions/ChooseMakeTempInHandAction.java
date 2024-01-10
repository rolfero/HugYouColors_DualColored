package basicmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ChooseMakeTempInHandAction extends AbstractGameAction {

    @SuppressWarnings("unused")
    final AbstractPlayer p;
    final ArrayList<AbstractCard> choices;

    @SuppressWarnings("unused")
    public ChooseMakeTempInHandAction(ArrayList<AbstractCard> cards) {
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        this.amount = 1;
        this.choices = cards;
    }

    @SuppressWarnings("unused")
    @Override
    public void update() {
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            CardGroup chooseGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            chooseGroup.group = choices;

            AbstractDungeon.cardRewardScreen.customCombatOpen(choices, "Add a card to your hand.", false); //TODO: Localize


        } else {
            addToTop( new MakeTempCardInHandAction(AbstractDungeon.cardRewardScreen.discoveryCard) );
            this.isDone = true;
        }

        this.tickDuration();
    }
}
