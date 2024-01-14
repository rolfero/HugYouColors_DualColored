package basicmod.patches;

import basemod.BaseMod;
import basemod.CustomCharacterSelectScreen;
import basicmod.HugYouColors;
import basicmod.ui.SubColorMenu;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;

public class MainMenuUIPatch {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(HugYouColors.makeID("MainMenuUIPatch")).TEXT;
    private static float getCheckboxX() {
        return 180.0f * Settings.scale;
    }
    private static float getTextWidth(String string) {
        return FontHelper.getSmartWidth(FontHelper.buttonLabelFont, string, 9999.0F, 0.0F, 0.7f);
    }
    @SpirePatch(
            clz = CustomCharacterSelectScreen.class,
            method = "initialize"
    )
    public static class InitSelectScreen {
        public static void Postfix(CustomCharacterSelectScreen obj) {
            HugYouColors.subColorMenu.initialize(obj, TEXT[6]);
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "initialize"
    )
    public static class InitSelectScreen2 {
        public static void Postfix(CharacterSelectScreen obj) {
            if (BaseMod.getModdedCharacters().isEmpty()) {
                HugYouColors.subColorMenu.initialize(obj, TEXT[6]);
            }

            float textWidth = getTextWidth(TEXT[0]);
            float checkBoxX = getCheckboxX();
            float hitboxX = checkBoxX + 30.0F * Settings.scale / 2.0F + textWidth / 2.0F;

            Hitbox hitbox = new Hitbox(textWidth + 70.0F * Settings.scale, 35.0F * Settings.scale);
            hitbox.move(hitboxX, Settings.HEIGHT - 280.0f * Settings.scale);
            MainMenuUIPatch.HitboxField.hitbox.set(obj, hitbox);
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = SpirePatch.CLASS)
    public static class HitboxField {
        public static final SpireField<Hitbox> hitbox = new SpireField<>(() -> null);
    }


    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "renderAscensionMode"
    )
    public static class RenderCheckboxPatch {
        @SpirePostfixPatch
        public static void renderCheckbox(CharacterSelectScreen __instance, SpriteBatch sb, boolean ___anySelected) {
            if (!___anySelected) return;

            if (getChosenPlayer(__instance) != null && HugYouColors.playerSecondary != null) {
                if (!BaseMod.isBaseGameCharacter(getChosenPlayer(__instance)) || !BaseMod.isBaseGameCharacter(HugYouColors.playerSecondary))
                    return;
            }

            Hitbox hb = HitboxField.hitbox.get(__instance);
            float checkBoxX = getCheckboxX();

            sb.draw(ImageMaster.OPTION_TOGGLE, checkBoxX, hb.cY - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);

            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, HugYouColors.getActiveConfig() ? TEXT[0] : TEXT[1], checkBoxX + 30.0F * Settings.scale + getTextWidth(TEXT[0]) / 2.0F + 16.0F, hb.cY, hb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.GOLD_COLOR, 0.75f);

            if (hb.hovered && !HugYouColors.subColorMenu.dropdown.isOpen) {
                TipHelper.renderGenericTip((float) InputHelper.mX + 64.0F * Settings.scale, (float) InputHelper.mY + 24.0F * Settings.scale, TEXT[2], HugYouColors.getActiveConfig() ? TEXT[3] : TEXT[4]);
            }

            if ((HugYouColors.getActiveConfig() && HugYouColors.getConfigDualSet()) || (!HugYouColors.getActiveConfig() && HugYouColors.getConfigDualAll())) {
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.OPTION_TOGGLE_ON, checkBoxX, hb.cY - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
            }

            hb.render(sb);
        }
    }

    public static AbstractPlayer getChosenPlayer(CharacterSelectScreen characterSelectScreen) {
        for (CharacterOption characterOption : characterSelectScreen.options) {
            if (characterOption.selected) return characterOption.c;
        }
        return null;
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "updateAscensionToggle")
    public static class UpdateCheckboxPatch {
        @SpirePostfixPatch
        public static void updateCheckbox(CharacterSelectScreen __instance, SeedPanel ___seedPanel, boolean ___anySelected) {
            if (!___anySelected) return;

            if (getChosenPlayer(__instance) != null && HugYouColors.playerSecondary != null) {
                if (!BaseMod.isBaseGameCharacter(getChosenPlayer(__instance)) || !BaseMod.isBaseGameCharacter(HugYouColors.playerSecondary))
                    return;
            }

            Hitbox hb = HitboxField.hitbox.get(__instance);
            hb.update();

            if (InputHelper.justClickedLeft) {
                if (hb.hovered && !HugYouColors.subColorMenu.dropdown.isOpen) {
                    hb.clickStarted = true;
                }
            }

            if (hb.clicked || CInputActionSet.proceed.isJustPressed()) {
                hb.clicked = false;
                if (HugYouColors.getActiveConfig()) {
                    HugYouColors.setConfigDualSet(!HugYouColors.getConfigDualSet());
                } else {
                    HugYouColors.setConfigDualAll(!HugYouColors.getConfigDualAll());
                }
            }
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "generateSeeds")
    public static class InitializeCurrentRunActivePatch {
        @SpirePostfixPatch
        public static void initializeCurrentRunActivePatch() {
            HugYouColors.currentRunActive = HugYouColors.getActiveConfig();
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "justSelected"
    )
    public static class JustSelected {
        public static void Prefix(CharacterSelectScreen obj) {
            HugYouColors.subColorMenu.chooseColor(HugYouColors.subColorMenu.currentChoice);
        }
    }

    @SpirePatch(
            clz = CharacterOption.class,
            method = "renderRelics"
    )
    public static class RenderOptions {
        public static void Postfix(CharacterOption obj, SpriteBatch sb) {
            if (obj.selected) {
                HugYouColors.subColorMenu.dropdown.render(sb, SubColorMenu.DROPDOWN_X, SubColorMenu.DROPDOWN_Y);
                FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, TEXT[5], SubColorMenu.DROPDOWN_X - 25.0F * Settings.xScale, SubColorMenu.DROPDOWN_Y + 40.0F * Settings.yScale, 99999.0F, 38.0F * Settings.scale, Settings.GOLD_COLOR, 0.8F);
            }
        }
    }

    @SpirePatch(
            clz = CharacterOption.class,
            method = "updateHitbox"
    )
    public static class UpdateOptions {
        public static void Postfix(CharacterOption obj) {

            if (obj.selected)
                HugYouColors.subColorMenu.update();

        }
    }



}
