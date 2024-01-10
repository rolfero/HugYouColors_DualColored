package basicmod.dynamicvariables;

import basemod.abstracts.DynamicVariable;
import basicmod.BasicMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SuppressWarnings({"ALL", "unused"})
public class HalfDamageVariable extends DynamicVariable {

    @Override
    public String key() {
        return BasicMod.makeID("HalfDamage");
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return value(abstractCard) != baseValue(abstractCard);
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return abstractCard.damage/2;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return abstractCard.baseDamage/2;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return abstractCard.upgradedDamage;
    }
}
