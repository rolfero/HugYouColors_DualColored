package hugyoutwentydualcolors.actions;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.stances.*;

import java.lang.reflect.InvocationTargetException;

public class SyncAction extends AbstractGameAction {

    //Exhaust any number of cards in your hand. Draw that many cards and gain energy equal to their cost.
    private final AbstractPlayer p;


    
    public SyncAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (StanceField.syncOrb.get(p.stance) != null) {
            try {
                addToTop(new ChannelAction((AbstractOrb) StanceField.syncOrb.get(p.stance).getDeclaredConstructor().newInstance()));
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        this.isDone = true;
    }

    
    @SpirePatch(
            clz = AbstractStance.class,
            method = SpirePatch.CLASS
    )
    public static class StanceField {
        public static final SpireField<Class<?>> syncOrb = new SpireField<>(() -> null);
    }

    @SpirePatch(
            clz = WrathStance.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class WrathInit {
        
        public static void Prefix(WrathStance __instance) {
            StanceField.syncOrb.set(__instance, Lightning.class);
        }
    }

    @SpirePatch(
            clz = CalmStance.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class CalmInit {
        
        public static void Prefix(CalmStance __instance) {
            StanceField.syncOrb.set(__instance, Frost.class);
        }
    }

    @SpirePatch(
            clz = DivinityStance.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DivinityInit {
        
        public static void Prefix(DivinityStance __instance) {
            StanceField.syncOrb.set(__instance, Plasma.class);
        }
    }

    @SpirePatch(
            clz = NeutralStance.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class NeutralInit {
        
        public static void Prefix(NeutralStance __instance) {
            StanceField.syncOrb.set(__instance, Dark.class);
        }
    }
}
