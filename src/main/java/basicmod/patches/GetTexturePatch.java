package basicmod.patches;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static basicmod.HugYouColors.*;
import static basicmod.HugYouColors.Enums.*;

@SuppressWarnings({"ALL", "unused"})
public class GetTexturePatch {
    @SpirePatch(
            clz = BaseMod.class,
            method = "getSkillBgTexture"
    )
    public static class SkillBgTexturePatch {
        
        public static SpireReturn<Texture> Prefix(AbstractCard.CardColor color) {
            //Check if its a dual color, return the dual color texture modified and all!
            if (color.equals(CARD_DUAL_RG_COLOR)) {
                return SpireReturn.Return(RED_GREEN_DUAL_TEXTURE_SKILL);
            } else if (color.equals(CARD_DUAL_RB_COLOR)) {
                return SpireReturn.Return(RED_BLUE_DUAL_TEXTURE_SKILL);
            } else if (color.equals(CARD_DUAL_RP_COLOR)) {
                return SpireReturn.Return(RED_PURPLE_DUAL_TEXTURE_SKILL);
            } else if (color.equals(CARD_DUAL_GB_COLOR)) {
                return SpireReturn.Return(GREEN_BLUE_DUAL_TEXTURE_SKILL);
            } else if (color.equals(CARD_DUAL_GP_COLOR)) {
                return SpireReturn.Return(GREEN_PURPLE_DUAL_TEXTURE_SKILL);
            } else if (color.equals(CARD_DUAL_BP_COLOR)) {
                return SpireReturn.Return(BLUE_PURPLE_DUAL_TEXTURE_SKILL);
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            clz = BaseMod.class,
            method = "getAttackBgTexture"
    )
    public static class AttackBgTexturePatch {
        
        public static SpireReturn<Texture> Prefix(AbstractCard.CardColor color) {
            //Check if its a dual color, return the dual color texture modified and all!
            if (color.equals(CARD_DUAL_RG_COLOR)) {
                return SpireReturn.Return(RED_GREEN_DUAL_TEXTURE_ATTACK);
            } else if (color.equals(CARD_DUAL_RB_COLOR)) {
                return SpireReturn.Return(RED_BLUE_DUAL_TEXTURE_ATTACK);
            } else if (color.equals(CARD_DUAL_RP_COLOR)) {
                return SpireReturn.Return(RED_PURPLE_DUAL_TEXTURE_ATTACK);
            } else if (color.equals(CARD_DUAL_GB_COLOR)) {
                return SpireReturn.Return(GREEN_BLUE_DUAL_TEXTURE_ATTACK);
            } else if (color.equals(CARD_DUAL_GP_COLOR)) {
                return SpireReturn.Return(GREEN_PURPLE_DUAL_TEXTURE_ATTACK);
            } else if (color.equals(CARD_DUAL_BP_COLOR)) {
                return SpireReturn.Return(BLUE_PURPLE_DUAL_TEXTURE_ATTACK);
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            clz = BaseMod.class,
            method = "getPowerBgTexture"
    )
    public static class PowerBgTexturePatch {
        
        public static SpireReturn<Texture> Prefix(AbstractCard.CardColor color) {
            //Check if its a dual color, return the dual color texture modified and all!
            if (color.equals(CARD_DUAL_RG_COLOR)) {
                return SpireReturn.Return(RED_GREEN_DUAL_TEXTURE_POWER);
            } else if (color.equals(CARD_DUAL_RB_COLOR)) {
                return SpireReturn.Return(RED_BLUE_DUAL_TEXTURE_POWER);
            } else if (color.equals(CARD_DUAL_RP_COLOR)) {
                return SpireReturn.Return(RED_PURPLE_DUAL_TEXTURE_POWER);
            } else if (color.equals(CARD_DUAL_GB_COLOR)) {
                return SpireReturn.Return(GREEN_BLUE_DUAL_TEXTURE_POWER);
            } else if (color.equals(CARD_DUAL_GP_COLOR)) {
                return SpireReturn.Return(GREEN_PURPLE_DUAL_TEXTURE_POWER);
            } else if (color.equals(CARD_DUAL_BP_COLOR)) {
                return SpireReturn.Return(BLUE_PURPLE_DUAL_TEXTURE_POWER);
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            clz = BaseMod.class,
            method = "getAttackBgPortraitTexture"
    )
    public static class AttackBgPortraitTexturePatch {
        
        public static SpireReturn<Texture> Prefix(AbstractCard.CardColor color) {
            //Check if its a dual color, return the dual color texture modified and all!
            if (color.equals(CARD_DUAL_RG_COLOR)) {
                return SpireReturn.Return(RED_GREEN_DUAL_TEXTURE_ATTACK_L);
            } else if (color.equals(CARD_DUAL_RB_COLOR)) {
                return SpireReturn.Return(RED_BLUE_DUAL_TEXTURE_ATTACK_L);
            } else if (color.equals(CARD_DUAL_RP_COLOR)) {
                return SpireReturn.Return(RED_PURPLE_DUAL_TEXTURE_ATTACK_L);
            } else if (color.equals(CARD_DUAL_GB_COLOR)) {
                return SpireReturn.Return(GREEN_BLUE_DUAL_TEXTURE_ATTACK_L);
            } else if (color.equals(CARD_DUAL_GP_COLOR)) {
                return SpireReturn.Return(GREEN_PURPLE_DUAL_TEXTURE_ATTACK_L);
            } else if (color.equals(CARD_DUAL_BP_COLOR)) {
                return SpireReturn.Return(BLUE_PURPLE_DUAL_TEXTURE_ATTACK_L);
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            clz = BaseMod.class,
            method = "getSkillBgPortraitTexture"
    )
    public static class SkillBgPortraitTexturePatch {
        
        public static SpireReturn<Texture> Prefix(AbstractCard.CardColor color) {
            //Check if its a dual color, return the dual color texture modified and all!
            if (color.equals(CARD_DUAL_RG_COLOR)) {
                return SpireReturn.Return(RED_GREEN_DUAL_TEXTURE_SKILL_L);
            } else if (color.equals(CARD_DUAL_RB_COLOR)) {
                return SpireReturn.Return(RED_BLUE_DUAL_TEXTURE_SKILL_L);
            } else if (color.equals(CARD_DUAL_RP_COLOR)) {
                return SpireReturn.Return(RED_PURPLE_DUAL_TEXTURE_SKILL_L);
            } else if (color.equals(CARD_DUAL_GB_COLOR)) {
                return SpireReturn.Return(GREEN_BLUE_DUAL_TEXTURE_SKILL_L);
            } else if (color.equals(CARD_DUAL_GP_COLOR)) {
                return SpireReturn.Return(GREEN_PURPLE_DUAL_TEXTURE_SKILL_L);
            } else if (color.equals(CARD_DUAL_BP_COLOR)) {
                return SpireReturn.Return(BLUE_PURPLE_DUAL_TEXTURE_SKILL_L);
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(
            clz = BaseMod.class,
            method = "getPowerBgPortraitTexture"
    )
    public static class PowerBgPortraitTexturePatch {
        
        public static SpireReturn<Texture> Prefix(AbstractCard.CardColor color) {
            //Check if its a dual color, return the dual color texture modified and all!
            if (color.equals(CARD_DUAL_RG_COLOR)) {
                return SpireReturn.Return(RED_GREEN_DUAL_TEXTURE_POWER_L);
            } else if (color.equals(CARD_DUAL_RB_COLOR)) {
                return SpireReturn.Return(RED_BLUE_DUAL_TEXTURE_POWER_L);
            } else if (color.equals(CARD_DUAL_RP_COLOR)) {
                return SpireReturn.Return(RED_PURPLE_DUAL_TEXTURE_POWER_L);
            } else if (color.equals(CARD_DUAL_GB_COLOR)) {
                return SpireReturn.Return(GREEN_BLUE_DUAL_TEXTURE_POWER_L);
            } else if (color.equals(CARD_DUAL_GP_COLOR)) {
                return SpireReturn.Return(GREEN_PURPLE_DUAL_TEXTURE_POWER_L);
            } else if (color.equals(CARD_DUAL_BP_COLOR)) {
                return SpireReturn.Return(BLUE_PURPLE_DUAL_TEXTURE_POWER_L);
            }
            return SpireReturn.Continue();
        }
    }
}
