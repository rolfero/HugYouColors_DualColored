package hugyoutwentydualcolors.powers;

import hugyoutwentydualcolors.HugYouColors;
import hugyoutwentydualcolors.actions.SyncAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class SynchronizationPower extends BasePower {

    public static final String ID = HugYouColors.makeID("SynchronizationPower");

    
    public SynchronizationPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) return;
        for (int i = 0; i < amount; ++i) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (AbstractDungeon.player.hasEmptyOrb()) {
                        addToTop(new SyncAction());
                    }
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void updateDescription() {
        this.description = this.amount == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

}