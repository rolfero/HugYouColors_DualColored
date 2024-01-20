package hugyoucolors.powers;

import hugyoucolors.HugYouColors;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FreeCardPower extends BasePower {
    private static final String ID = HugYouColors.makeID("FreeCardPower");
    private static final PowerStrings powerStrings;

    public FreeCardPower(AbstractCreature owner, int amount) {
        super(ID, PowerType.BUFF, false, owner, amount);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = powerStrings.DESCRIPTIONS[0];
        } else {
            this.description = powerStrings.DESCRIPTIONS[1] + this.amount + powerStrings.DESCRIPTIONS[2];
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount > 0) {
            this.flash();
            --this.amount;
            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "freeToPlay"
    )
    public static class FreeToPlayPatch {
        public static SpireReturn<Boolean> Prefix() {
            if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                    (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                    AbstractDungeon.player.hasPower(ID))
                return SpireReturn.Return(true);
            return SpireReturn.Continue();
        }
    }
}
