package basicmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;


@SpirePatch(
        clz = AbstractPlayer.class,
        method = "channelOrb"
)
public class ChannelHookPatch {
    public interface OnChannelHook {
        default void onChannel(AbstractOrb orb) {} //the weird name is mostly to avoid name conflicts
        default void triggerOnChannel(AbstractOrb orb) {} //the weird name is mostly to avoid name conflicts
    }

    
    @SpireInsertPatch(
        locator = Locator.class
    )
    public static void Insert(AbstractPlayer __instance, AbstractOrb orbToGet) {
        for (AbstractCard c : __instance.hand.group) {
            if (c instanceof OnChannelHook) {
                ((OnChannelHook) c).onChannel(orbToGet);
                ((OnChannelHook) c).triggerOnChannel(orbToGet);
            }
        }
        for (AbstractCard c : __instance.discardPile.group) {
            if (c instanceof OnChannelHook) {
                ((OnChannelHook) c).triggerOnChannel(orbToGet);
            }
        }
        for (AbstractCard c : __instance.drawPile.group) {
            if (c instanceof OnChannelHook) {
                ((OnChannelHook) c).triggerOnChannel(orbToGet);
            }
        }
        for (AbstractCard c : __instance.exhaustPile.group) {
            if (c instanceof OnChannelHook) {
                ((OnChannelHook) c).triggerOnChannel(orbToGet);
            }
        }
    }

    
    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "orbsChanneledThisCombat");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}