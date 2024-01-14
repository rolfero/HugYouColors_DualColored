package basicmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

//TODO: Remove
@Deprecated
public class ArcheologyAction extends AbstractGameAction {

    public ArcheologyAction(int amt) {
        this.amount = amt;
    }

    @Override
    public void update() {

        ArrayList<Claw> claws = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof Claw) {
                claws.add((Claw)c);
            }
        }

        if (claws.size() == 0) {
            for (int i = 0; i < amount; ++i) {
                addToTop(new MakeTempCardInDiscardAction(new Claw(), 1));
            }
        } else {
            for (Claw c : claws) {
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractDungeon.player.discardPile.moveToDeck(c, true);
                        this.isDone = true;
                    }
                });
            }
        }

        this.isDone = true;
    }
}
