package hugyoutwentydualcolors.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class DisplayCardAction extends AbstractGameAction {

    AbstractCard showCard;
    float x;
    float y;
    boolean customPos;

    public DisplayCardAction(AbstractCard card) {
        this.showCard = card;
        this.customPos = false;
    }

    public DisplayCardAction(AbstractCard card, float x, float y) {
        this(card);
        this.x = x;
        this.y = y;
        this.customPos = true;
    }

    @Override
    public void update() {
        ShowCardBrieflyEffect showCardBrieflyEffect = new ShowCardBrieflyEffect(showCard.makeStatEquivalentCopy(), this.customPos ? this.x : Settings.WIDTH/2, this.customPos ? this.y : Settings.HEIGHT/2);
        showCardBrieflyEffect.duration = 1.5F;
        showCardBrieflyEffect.startingDuration = 1.5F;
        AbstractDungeon.effectsQueue.add(showCardBrieflyEffect);
        this.isDone = true;
    }
}
