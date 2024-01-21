package hugyoutwentydualcolors.patches;

import hugyoutwentydualcolors.HugYouColors;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SuppressWarnings({"ALL", "unused"})
public class InitializeCardPoolPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeCardPools"
    )
    public static class SecondaryInitializeCardPool {
        
        public static void Postfix() {
            if (HugYouColors.currentRunActive) {
                HugYouColors.initializeSecondaryCardPools();
            }
            if (HugYouColors.getConfigDualSet() || HugYouColors.getConfigDualAll())
                HugYouColors.initializeDualCardPools();
        }
    }
}
