package basicmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;


@SpirePatch(
        clz = GameActionManager.class,
        method = "incrementDiscard"
)
public class DiscardHookPatch {
    public interface OnDiscardHook {
        void onManualDiscard(); //the weird name is mostly to avoid name conflicts
    }

    
    @SpirePostfixPatch
    public static void onManualDiscard(boolean endOfTurn) {
        if (!AbstractDungeon.actionManager.turnHasEnded && !endOfTurn) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OnDiscardHook) {
                    ((OnDiscardHook) p).onManualDiscard();
                }
            }
        }
    }
}