package basicmod.patches;

import basemod.BaseMod;
import basemod.CustomCharacterSelectScreen;
import basicmod.BasicMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

public class MainMenuUIPatch {

    @SpirePatch(
            clz = CustomCharacterSelectScreen.class,
            method = "initialize"
    )
    public static class InitSelectScreen {
        public static void Postfix(CustomCharacterSelectScreen obj) {
            BasicMod.subColorMenu.initialize(obj);
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "initialize"
    )
    public static class InitSelectScreen2 {
        public static void Postfix(CharacterSelectScreen obj) {
            if (BaseMod.getModdedCharacters().isEmpty()) {
                BasicMod.subColorMenu.initialize(obj);
            }
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "justSelected"
    )
    public static class JustSelected {
        public static void Prefix(CharacterSelectScreen obj) {
            BasicMod.subColorMenu.chooseColor(BasicMod.subColorMenu.currentChoice);
        }
    }

    @SpirePatch(
            clz = CharacterOption.class,
            method = "renderRelics"
    )
    public static class RenderOptions {
        public static void Postfix(CharacterOption obj, SpriteBatch sb) {
            if (obj.selected)
                BasicMod.subColorMenu.render(sb);
        }
    }

    @SpirePatch(
            clz = CharacterOption.class,
            method = "updateHitbox"
    )
    public static class UpdateOptions {
        public static void Postfix(CharacterOption obj) {

            if (obj.selected)
                BasicMod.subColorMenu.update();

        }
    }



}
