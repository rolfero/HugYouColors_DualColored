package hugyoucolors.patches;

import basemod.ReflectionHacks;
import hugyoucolors.HugYouColors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class TopPanelTitlePatch {
    @SpirePatch(
            clz = TopPanel.class,
            method = "renderName"
    )
    public static class RenderNamePatch {
        @SpireInsertPatch(
            locator = Locator.class
        )
        public static SpireReturn<Void> Insert(TopPanel __instance, SpriteBatch sb) {
            if (HugYouColors.getActiveConfig() && HugYouColors.playerSecondary != null) {

                if (Settings.isMobile) return SpireReturn.Return();

                String title = ReflectionHacks.getPrivate(__instance, TopPanel.class, "title");
                float titleX = ReflectionHacks.getPrivate(__instance, TopPanel.class, "titleX");
                float titleY = ReflectionHacks.getPrivate(__instance, TopPanel.class, "titleY");

                FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, title, titleX, titleY+7.0f*Settings.yScale, Color.LIGHT_GRAY);
                FontHelper.tipBodyFont.getData().setScale(0.8f);
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, "aided by " + HugYouColors.playerSecondary.title, titleX-2.0f*Settings.xScale, titleY-18.0f*Settings.yScale, Color.LIGHT_GRAY);
                FontHelper.tipBodyFont.getData().setScale(1.0f);

                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }


        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(Settings.class, "isMobile");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }
}
