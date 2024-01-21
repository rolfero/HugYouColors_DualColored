package hugyoutwentydualcolors.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class FashionBladeAction extends AbstractGameAction {

    private final boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private final int energyOnUse;

    private final int accMult;

    
    public FashionBladeAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, int accuracyMultiplier) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.accMult = accuracyMultiplier;
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
            addToTop(new MakeTempCardInHandAction(new Shiv(), effect));
            addToTop(new ApplyPowerAction(p, p, new AccuracyPower(p, effect*accMult), effect*accMult));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
