package hugyoutwentydualcolors.powers;

import hugyoutwentydualcolors.HugYouColors;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import hugyoutwentydualcolors.actions.DisplayCardAction;


public class UpgradeNextPlayedCardPower extends BasePower {

    private static final String ID = HugYouColors.makeID("UpgradeNextPlayedCardPower");


    public UpgradeNextPlayedCardPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (this.amount > 0 && usedCard.canUpgrade()) {
            addToBot(new WaitAction(Settings.FAST_MODE ? 0.25f : 0.5f));
            addToBot(new UpgradeSpecificCardAction(usedCard));
            addToBot(new DisplayCardAction(usedCard));
            this.amount--;
            this.flash();
            if (this.amount <= 0) {
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) return;
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = this.amount == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}