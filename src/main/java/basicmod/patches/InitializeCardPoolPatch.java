package basicmod.patches;

import basicmod.BasicMod;
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
            BasicMod.initializeSecondaryCardPools();
            BasicMod.initializeDualCardPools();
            int newOrbs = (AbstractDungeon.player.masterMaxOrbs + BasicMod.playerSecondary.masterMaxOrbs + 1)/2;
            AbstractDungeon.player.masterMaxOrbs = Math.max(newOrbs, AbstractDungeon.player.masterMaxOrbs);
        }
    }
}
