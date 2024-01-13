package basicmod.powers;

import basicmod.HugYouColors;
import basicmod.actions.ChooseMakeTempInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.ArrayList;


public class StratagemPower extends BasePower {

    public static final String ID = HugYouColors.makeID("StratagemPower");

    
    public StratagemPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < amount; ++i) {
            ArrayList<AbstractCard> choices = new ArrayList<>();
            choices.add(new Smite());
            choices.add(new Safety());
            addToBot(new ChooseMakeTempInHandAction(choices));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}