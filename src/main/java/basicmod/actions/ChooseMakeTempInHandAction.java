package basicmod.actions;

import basicmod.BasicMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;


public class ChooseMakeTempInHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(BasicMod.makeID("ChooseMakeTempInHandAction"));
    private static final String[] TEXT = uiStrings.TEXT;

    final ArrayList<AbstractCard> choices;

    public ChooseMakeTempInHandAction(ArrayList<AbstractCard> cards) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
        this.choices = cards;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.cardRewardScreen.customCombatOpen(choices, TEXT[0], false);

        } else {
            addToTop( new MakeTempCardInHandAction(AbstractDungeon.cardRewardScreen.discoveryCard) );
            this.isDone = true;
        }

        this.tickDuration();
    }
}
