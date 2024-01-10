package basicmod.damagemodifiers;

import basicmod.BasicMod;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PoisonPower;

@SuppressWarnings({"ALL", "unused"})
public class HalfAgainDamage extends AbstractDamageModifier {

    @SuppressWarnings("unused")
    public static final String ID = BasicMod.makeID("HalfAgainDamage");

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature targetHit) {
        if (lastDamageTaken > 0) {
            this.addToBot(new ApplyPowerAction(targetHit, info.owner, new PoisonPower(targetHit, info.owner, lastDamageTaken), lastDamageTaken));
        }
    }

    //Overriding this to true tells us that this damage mod is considered part of the card and not just something added on to the card later.
    //If you ever add a damage modifier during the initialization of a card, it should be inherent.
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new HalfAgainDamage();
    }
}
