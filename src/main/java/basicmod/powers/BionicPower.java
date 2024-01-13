package basicmod.powers;

import basicmod.HugYouColors;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class BionicPower extends BasePower {

    public static final String ID = HugYouColors.makeID("BionicPower");


    public BionicPower(AbstractCreature owner) {
        super(ID, PowerType.BUFF, false, owner, null, -1);
    }

    // This increases damage by your Focus
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return (type == DamageInfo.DamageType.NORMAL && this.owner.hasPower(FocusPower.POWER_ID)) ? damage + this.owner.getPower(FocusPower.POWER_ID).amount : damage;
    }

    // This increases Block by your Focus
    @Override
    public float modifyBlock(float blockAmount) {
        if (!this.owner.hasPower(FocusPower.POWER_ID)) {
            return blockAmount;
        }
        return (blockAmount += (float)this.owner.getPower(FocusPower.POWER_ID).amount) < 0.0F ? 0.0F : blockAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "onModifyPower"
    )
    public static class OnModifyPowerPatch {

        public static void Postfix() {
            if (AbstractDungeon.player != null) {
                if (!AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
                    if (AbstractDungeon.player.hasPower(BionicPower.ID) && (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) || AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))) {
                        for (AbstractOrb o : AbstractDungeon.player.orbs)
                            o.updateDescription();
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractOrb.class,
            method = "applyFocus"
    )
    public static class ApplyFocusPatch {

        public static void Postfix(AbstractOrb __instance) {
            if (AbstractDungeon.player == null || !AbstractDungeon.player.hasPower(BionicPower.ID)) {
                return;
            }

            if (__instance instanceof Lightning) {
                AbstractPower str = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);

                if (str != null) {
                    __instance.passiveAmount = Math.max(0, __instance.passiveAmount + str.amount);
                    __instance.evokeAmount = Math.max(0, __instance.evokeAmount + str.amount);
                }
                //do strength here, passive and evoke
            } else if (__instance instanceof Dark) {
                AbstractPower str = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);

                if (str != null) {
                    __instance.evokeAmount = Math.max(0, __instance.evokeAmount + str.amount);
                }
                //do strength here, evoke only
            } else if (__instance instanceof Frost) {
                AbstractPower dex = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);

                if (dex != null) {
                    __instance.passiveAmount = Math.max(0, __instance.passiveAmount + dex.amount);
                    __instance.evokeAmount = Math.max(0, __instance.evokeAmount + dex.amount);
                }
                //do dexterity here, passive and evoke
            }

        }
    }
}