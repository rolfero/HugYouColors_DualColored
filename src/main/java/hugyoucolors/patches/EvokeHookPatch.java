package hugyoucolors.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;


public class EvokeHookPatch {
    public interface OnEvokeHook {
        default void onEvoke(AbstractOrb orb) {} //the weird name is mostly to avoid name conflicts

        default void triggerOnEvoke(AbstractOrb orb) {} //the weird name is mostly to avoid name conflicts
    }

    public static void CallEvokeHook(AbstractPlayer player, AbstractOrb orb) {
        for (AbstractCard c : player.hand.group) {
            if (c instanceof OnEvokeHook) {
                ((OnEvokeHook) c).onEvoke(orb);
                ((OnEvokeHook) c).triggerOnEvoke(orb);
            }
        }
        for (AbstractCard c : player.discardPile.group) {
            if (c instanceof OnEvokeHook) {
                ((OnEvokeHook) c).triggerOnEvoke(orb);
            }
        }
        for (AbstractCard c : player.drawPile.group) {
            if (c instanceof OnEvokeHook) {
                ((OnEvokeHook) c).triggerOnEvoke(orb);
            }
        }
        for (AbstractCard c : player.exhaustPile.group) {
            if (c instanceof OnEvokeHook) {
                ((OnEvokeHook) c).triggerOnEvoke(orb);
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "evokeOrb"
    )
    public static class EvokeOrbPatch {

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractPlayer __instance) {
            CallEvokeHook(__instance, __instance.orbs.get(0));
        }
    }


    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.NewExprMatcher(EmptyOrbSlot.class);
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "evokeNewestOrb"
    )
    public static class EvokeNewestOrbPatch {

        public static void Postfix(AbstractPlayer __instance) {
            if (!__instance.orbs.isEmpty() && !(__instance.orbs.get(__instance.orbs.size() - 1) instanceof EmptyOrbSlot))
                CallEvokeHook(__instance, __instance.orbs.get(__instance.orbs.size() - 1));
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "evokeWithoutLosingOrb"
    )
    public static class EvokeWithoutLosingOrbPatch {

        public static void Postfix(AbstractPlayer __instance) {
            if (!__instance.orbs.isEmpty() && !(__instance.orbs.get(0) instanceof EmptyOrbSlot))
                CallEvokeHook(__instance, __instance.orbs.get(0));
        }
    }

}