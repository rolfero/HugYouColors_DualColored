package basicmod.patches;

import basicmod.HugYouColors;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class RelicObtainPatch {

    @SpirePatch(
            clz = BossRelicSelectScreen.class,
            method = "relicObtainLogic"
    )
    public static class RelicObtainLogicPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> Insert(BossRelicSelectScreen __instance, AbstractRelic r) {
            if (!HugYouColors.getActiveConfig()) return SpireReturn.Continue();

            switch (r.relicId) {
                case BlackBlood.ID:
                    r.instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.indexOf(AbstractDungeon.player.getRelic(BurningBlood.ID)), true);
                    break;
                case RingOfTheSerpent.ID:
                    r.instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.indexOf(AbstractDungeon.player.getRelic(SnakeRing.ID)), true);
                    break;
                case FrozenCore.ID:
                    r.instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.indexOf(AbstractDungeon.player.getRelic(CrackedCore.ID)), true);
                    break;
                case HolyWater.ID:
                    r.instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.indexOf(AbstractDungeon.player.getRelic(PureWater.ID)), true);
                    break;
                default:
                    return SpireReturn.Continue();
            }

            (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;

            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(String.class, "equals");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }
}
