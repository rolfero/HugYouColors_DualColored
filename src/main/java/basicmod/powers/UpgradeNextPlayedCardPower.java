package basicmod.powers;

import basicmod.BasicMod;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class UpgradeNextPlayedCardPower extends BasePower {

    private static final String ID = BasicMod.makeID("UpgradeNextPlayedCardPower");


    public UpgradeNextPlayedCardPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.amount > 0 && card.canUpgrade()) {
            addToBot(new UpgradeSpecificCardAction(card));
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
}