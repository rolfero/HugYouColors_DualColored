package basicmod.ui;

import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.options.DropdownMenu.DropdownColoring;
import basicmod.BasicMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class SubColorMenu {

    private static float DROPDOWN_X = 200.0f * Settings.scale;
    private static float DROPDOWN_Y = Settings.HEIGHT - 255.0f * Settings.scale;
    @SuppressWarnings("unused")
    private static final float CHECK_X = 200.0f * Settings.scale;
    @SuppressWarnings("unused")
    private static final float CHECK_Y = Settings.HEIGHT - 255.0f * Settings.scale;

    public DropdownMenu dropdown;
    public int currentChoice;
    public CharacterSelectScreen characterSelectScreen;

    @SuppressWarnings("unused")
    public SubColorMenu() {
        currentChoice = 0;
    }

    public ArrayList<CharacterOption> getCharacterOptionList(CharacterSelectScreen characterSelectScreen) {
        if (characterSelectScreen instanceof CustomCharacterSelectScreen) {
            return ReflectionHacks.getPrivate(characterSelectScreen, CustomCharacterSelectScreen.class, "allOptions");
        } else {
            return characterSelectScreen.options;
        }
    }

    public void initialize(CharacterSelectScreen charSelectScreen) {

        DROPDOWN_X = 200.0f * Settings.scale;
        DROPDOWN_Y = Settings.HEIGHT - 255.0f * Settings.scale;

        characterSelectScreen = charSelectScreen;
        //should be called after the options are created. Which is when, exactly?
        ArrayList<String> colorNames = new ArrayList<>();

        for (CharacterOption characterOption : getCharacterOptionList(characterSelectScreen)) {
            colorNames.add(characterOption.name);
        }

        dropdown = new DropdownMenu(((dropdownMenu, i, s) -> chooseColor(i)), colorNames, FontHelper.tipBodyFont, Settings.CREAM_COLOR);

        DropdownColoring.RowToColor.function.set(dropdown, this::getRowColor);

        //TODO: Toggle this on or off, dont play with sub color!

        chooseColor(0); //First choice, maybe save this as last played or whatever
    }

    public boolean isChosenCharacter(int i) {
        return getCharacterOptionList(characterSelectScreen).get(i).selected;
    }

    public Color getRowColor(int i) {

        if (isChosenCharacter(i)) {
            return Color.DARK_GRAY;
        }
        return null;
    }

    public void chooseColor(int i) {
        if (isChosenCharacter(i)) {
            if (dropdown.rows.size() > 1) {
                if (currentChoice == i && i == 0) {
                    dropdown.setSelectedIndex(1);
                } else if (currentChoice == i) {
                    dropdown.setSelectedIndex(0);
                } else {
                    dropdown.setSelectedIndex(currentChoice);
                }
                return;
            }
        }
        currentChoice = i;
        BasicMod.playerSecondary = getCharacterOptionList(characterSelectScreen).get(i).c;
    }



    public void update() {

        dropdown.update();

    }

    public void render(SpriteBatch sb) {
        //sb.draw(MENU_BG, BG_X, BG_Y, 0f, 0f, MENU_BG.getRegionWidth(), MENU_BG.getRegionHeight(), BG_X_SCALE, BG_Y_SCALE, 0f);
        dropdown.render(sb, DROPDOWN_X, DROPDOWN_Y);

        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, "Who else is influencing your actions?", DROPDOWN_X - 25.0F * Settings.xScale, DROPDOWN_Y + 40.0F * Settings.yScale, 99999.0F, 38.0F * Settings.scale, Settings.GOLD_COLOR, 0.75F);

    }

}
