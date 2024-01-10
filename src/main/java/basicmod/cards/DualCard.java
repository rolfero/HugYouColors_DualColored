package basicmod.cards;

import basicmod.util.CardInfo;

import java.util.ArrayList;

public abstract class DualCard extends BaseCard {

    private final ArrayList<CardColor> cardColors = new ArrayList<>();

    public DualCard(CardInfo cardInfo, CardColor color1, CardColor color2) {
        super(cardInfo);
        this.color = cardInfo.cardColor;
        cardColors.add(color1);
        cardColors.add(color2);
    }

    public DualCard(CardInfo cardInfo, boolean doesUpgradeDescription, CardColor color1, CardColor color2) {
        super(cardInfo, doesUpgradeDescription);
        this.color = cardInfo.cardColor;
        cardColors.add(color1);
        cardColors.add(color2);
    }

    public boolean belongsTo(CardColor color) {
        return (cardColors.contains(color));
    }

    public boolean belongsTo(CardColor color1, CardColor color2) {
        return (this.belongsTo(color1) && this.belongsTo(color2));
    }

}
