package hugyoutwentydualcolors.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class InventoryAction extends AbstractGameAction {

    private final boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private final int energyOnUse;

    
    public InventoryAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;

        this.amount = 1;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            this.p.getRelic(ChemicalX.ID).flash();
        }

        if (effect > 0) {
            addToTop(new ApplyPowerAction(this.p, this.p, new DrawCardNextTurnPower(this.p, effect), effect));
            addToTop(new IncreaseMaxOrbAction(effect));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
