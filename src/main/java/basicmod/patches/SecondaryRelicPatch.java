package basicmod.patches;

import basemod.ReflectionHacks;
import basicmod.HugYouColors;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static basemod.BaseMod.getRelicsInCustomPool;


public class SecondaryRelicPatch {

    @SpirePatch(
            clz = RelicLibrary.class,
            method = "populateRelicPool"
    )
    public static class PopulateRelicPoolPatch {
        public static void Postfix(ArrayList<String> pool, AbstractRelic.RelicTier tier, AbstractPlayer.PlayerClass c) {
            if (HugYouColors.getActiveConfig() && HugYouColors.playerSecondary != null) {
                switch (HugYouColors.playerSecondary.chosenClass) {
                    case IRONCLAD:
                        for (Map.Entry<String, AbstractRelic> r : ((HashMap<String, AbstractRelic>)ReflectionHacks.getPrivateStatic(RelicLibrary.class, "redRelics")).entrySet()) {
                            if (r.getValue().tier == tier && (
                                    !UnlockTracker.isRelicLocked(r.getKey()) || Settings.treatEverythingAsUnlocked()))
                                pool.add(r.getKey());
                        }
                        break;
                    case THE_SILENT:
                        for (Map.Entry<String, AbstractRelic> r : ((HashMap<String, AbstractRelic>)ReflectionHacks.getPrivateStatic(RelicLibrary.class, "greenRelics")).entrySet()) {
                            if (r.getValue().tier == tier && (
                                    !UnlockTracker.isRelicLocked(r.getKey()) || Settings.treatEverythingAsUnlocked()))
                                pool.add(r.getKey());
                        }
                        break;
                    case WATCHER:
                        for (Map.Entry<String, AbstractRelic> r : ((HashMap<String, AbstractRelic>)ReflectionHacks.getPrivateStatic(RelicLibrary.class, "purpleRelics")).entrySet()) {
                            if (r.getValue().tier == tier && (
                                    !UnlockTracker.isRelicLocked(r.getKey()) || Settings.treatEverythingAsUnlocked()))
                                pool.add(r.getKey());
                        }
                        break;
                    case DEFECT:
                        for (Map.Entry<String, AbstractRelic> r : ((HashMap<String, AbstractRelic>)ReflectionHacks.getPrivateStatic(RelicLibrary.class, "blueRelics")).entrySet()) {
                            if (r.getValue().tier == tier && (
                                    !UnlockTracker.isRelicLocked(r.getKey()) || Settings.treatEverythingAsUnlocked()))
                                pool.add(r.getKey());
                        }
                        break;
                    default:
                        for (Map.Entry<String, AbstractRelic> r : getRelicsInCustomPool(HugYouColors.playerSecondary.getCardColor()).entrySet()) {
                            if (r.getValue().tier == tier && (
                                    !UnlockTracker.isRelicLocked(r.getKey()) || Settings.treatEverythingAsUnlocked()))
                                pool.add(r.getKey());
                        }
                        break;
                }
            }
        }
    }
}
