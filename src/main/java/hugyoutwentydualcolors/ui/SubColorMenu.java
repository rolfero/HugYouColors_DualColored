package hugyoutwentydualcolors.ui;

import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.options.DropdownMenu.DropdownColoring;
import hugyoutwentydualcolors.HugYouColors;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;

import java.util.ArrayList;


public class SubColorMenu {
    private static UIStrings uiStrings;
    private static String[] TEXT;


    public static float DROPDOWN_X;
    public static float DROPDOWN_Y;
    
    private static final float CHECK_X = 200.0f * Settings.scale;
    
    private static final float CHECK_Y = Settings.HEIGHT - 255.0f * Settings.scale;

    public DropdownMenu dropdown;
    public int currentChoice;
    public CharacterSelectScreen characterSelectScreen;

    
    public SubColorMenu() {
        currentChoice = 1;
    }

    public ArrayList<CharacterOption> getCharacterOptionList(CharacterSelectScreen characterSelectScreen) {
        if (characterSelectScreen instanceof CustomCharacterSelectScreen) {
            return ReflectionHacks.getPrivate(characterSelectScreen, CustomCharacterSelectScreen.class, "allOptions");
        } else {
            return characterSelectScreen.options;
        }
    }

    public void initialize(CharacterSelectScreen charSelectScreen, String colorName) {

        DROPDOWN_X = 200.0f * Settings.scale;
        DROPDOWN_Y = Settings.HEIGHT - 220.0f * Settings.scale;

        characterSelectScreen = charSelectScreen;
        //should be called after the options are created. Which is when, exactly?
        ArrayList<String> colorNames = new ArrayList<>();

        colorNames.add(colorName);

        for (CharacterOption characterOption : getCharacterOptionList(characterSelectScreen)) {
            colorNames.add(characterOption.name);
        }

        dropdown = new DropdownMenu(((dropdownMenu, i, s) -> chooseColor(i)), colorNames, FontHelper.tipBodyFont, Settings.CREAM_COLOR);

        DropdownColoring.RowToColor.function.set(dropdown, this::getRowColor);

        dropdown.setSelectedIndex(1);

        //chooseColor(1); //First choice, maybe save this as last played or whatever
    }

    public boolean isMainChosenCharacter(int i) {
        if (i == 0) return false;

        if (this.characterSelectScreen instanceof CustomCharacterSelectScreen) {
            int optionsPerIndex = ReflectionHacks.getPrivate(this.characterSelectScreen, CustomCharacterSelectScreen.class, "optionsPerIndex");
            int optionsIndex = ReflectionHacks.getPrivate(this.characterSelectScreen, CustomCharacterSelectScreen.class, "optionsIndex");

            if (i < optionsIndex || i > optionsIndex+optionsPerIndex) return false;
        }

        return getCharacterOptionList(characterSelectScreen).get(i-1).selected;
    }

    public Color getRowColor(int i) {
        if (isMainChosenCharacter(i)) {
            return Color.DARK_GRAY;
        }
        return null;
    }

    public void chooseColor(int i) {
        if (isMainChosenCharacter(i)) {
            if (dropdown.rows.size() > 2) {
                if (currentChoice == i && i == 1) {
                    dropdown.setSelectedIndex(2);
                } else if (currentChoice == i) {
                    dropdown.setSelectedIndex(1);
                } else {
                    dropdown.setSelectedIndex(currentChoice);
                }
                return;
            }
        }
        currentChoice = i;
        if (currentChoice == 0) {
            HugYouColors.setActiveConfig(false);
        } else {
            HugYouColors.setActiveConfig(true);
            HugYouColors.playerSecondary = getCharacterOptionList(characterSelectScreen).get(i-1).c;
        }
    }

    public void update() {
        dropdown.update();
    }

}
