package hugyoucolors.powers;

import hugyoucolors.HugYouColors;
import hugyoucolors.actions.ChooseMakeTempInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.ArrayList;


public class StratagemUpgradedPower extends BasePower {

    public static final String ID = HugYouColors.makeID("StratagemUpgradedPower");


    public StratagemUpgradedPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < amount; ++i) {
            ArrayList<AbstractCard> choices = new ArrayList<>();
            choices.add(new Smite());
            choices.add(new Safety());
            choices.add(new Insight());
            addToBot(new ChooseMakeTempInHandAction(choices));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}