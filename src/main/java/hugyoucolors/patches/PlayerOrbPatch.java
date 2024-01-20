package hugyoucolors.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class PlayerOrbPatch {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class PlayerOrbFixPatch {
        public static SpireField<Boolean> didSetOrbs = new SpireField<>(() -> false);
    }
}
