package basicmod.cards;

import basicmod.util.CardInfo;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public abstract class DualCard extends BaseCard {

    private final ArrayList<CardColor> cardColors = new ArrayList<>();

    @SuppressWarnings("unused")
    public DualCard(CardInfo cardInfo, CardColor color1, CardColor color2) {
        super(cardInfo);
        this.color = cardInfo.cardColor;
        cardColors.add(color1);
        cardColors.add(color2);
    }

    @SuppressWarnings("unused")
    public DualCard(CardInfo cardInfo, boolean doesUpgradeDescription, CardColor color1, CardColor color2) {
        super(cardInfo, doesUpgradeDescription);
        this.color = cardInfo.cardColor;
        cardColors.add(color1);
        cardColors.add(color2);
    }

    @SuppressWarnings("unused")
    public boolean belongsTo(CardColor color) {
        return (cardColors.contains(color));
    }

    public boolean belongsTo(CardColor color1, CardColor color2) {
        return (cardColors.contains(color1) && cardColors.contains(color2));
    }

}
