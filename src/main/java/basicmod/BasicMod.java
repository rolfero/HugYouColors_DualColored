package basicmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basicmod.cards.DualCard;
import basicmod.dynamicvariables.HalfDamageVariable;
import basicmod.ui.SubColorMenu;
import basicmod.util.GeneralUtils;
import basicmod.util.KeywordInfo;
import basicmod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.megacrit.cardcrawl.helpers.ImageMaster.*;


@SpireInitializer
public class BasicMod implements
        EditStringsSubscriber,
        EditCardsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber {


    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this

    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "basicmod";

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static class Enums {
        
        @SpireEnum(name = "Green and red")
        public static AbstractCard.CardColor CARD_DUAL_RG_COLOR;
        
        @SpireEnum(name = "Green and red")
        public static CardLibrary.LibraryType LIBRARY_DUAL_RG_COLOR;
        
        @SpireEnum(name = "Red and blue")
        public static AbstractCard.CardColor CARD_DUAL_RB_COLOR;
        
        @SpireEnum(name = "Red and blue")
        public static CardLibrary.LibraryType LIBRARY_DUAL_RB_COLOR;
        
        @SpireEnum(name = "Green and blue")
        public static AbstractCard.CardColor CARD_DUAL_GB_COLOR;
        
        @SpireEnum(name = "Green and blue")
        public static CardLibrary.LibraryType LIBRARY_DUAL_GB_COLOR;
        
        @SpireEnum(name = "Green and purple")
        public static AbstractCard.CardColor CARD_DUAL_GP_COLOR;
        
        @SpireEnum(name = "Green and purple")
        public static CardLibrary.LibraryType LIBRARY_DUAL_GP_COLOR;
        
        @SpireEnum(name = "Red and purple")
        public static AbstractCard.CardColor CARD_DUAL_RP_COLOR;
        
        @SpireEnum(name = "Red and purple")
        public static CardLibrary.LibraryType LIBRARY_DUAL_RP_COLOR;
        
        @SpireEnum(name = "Blue and purple")
        public static AbstractCard.CardColor CARD_DUAL_BP_COLOR;
        
        @SpireEnum(name = "Blue and purple")
        public static CardLibrary.LibraryType LIBRARY_DUAL_BP_COLOR;
    }

    private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
    private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
    private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
    private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
    private static final String BG_POWER = characterPath("cardback/bg_power.png");
    private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
    private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/small_orb.png");

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    
    public static void initialize() {
        new BasicMod();
        BaseMod.addColor(Enums.CARD_DUAL_RG_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
        BaseMod.addColor(Enums.CARD_DUAL_RB_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
        BaseMod.addColor(Enums.CARD_DUAL_RP_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
        BaseMod.addColor(Enums.CARD_DUAL_GB_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
        BaseMod.addColor(Enums.CARD_DUAL_GP_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
        BaseMod.addColor(Enums.CARD_DUAL_BP_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
    }

    
    public BasicMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");

        /*
         *
         *
         * Plans for the mod:
         * Expands all colors with new cards.
         * Creates Dual-Color cards.
         * Gives a way of finding the dual color cards in-game.
         ** How?
         *** Maybe you can choose a secondary class, and then your card rewards are skewed in a certain way - and includes the dual colors.
         **** Example 1: 2 cards are primary color, 1 card is secondary color (what to do with busted crown, etc)
         **** Example 2: First, choose between 3 cards of primary color. If none suit you, you can get a card reward of your secondary color.
         **** Example 3: An additional card is given as a choice, of the secondary color.
         **** Example 4: You share the pool, but with skewed probability towards your primary color (70-30, maybe)
         *** Maybe all dual cards are obtainable to both the classes that have it, just like normal. (Sort of boring, but the easiest probably.)
         *
         * I want to try with example 4.
         *
         * DONE: Allow you to pick your subcolor - DONE.
         *  DONE: Save the subcolor (either base color or modded character) somehow. This needs to be a savable! Probably a string of either modid:name or The Silent etc.
         *  DONE: -> skew card reward probability based on it. DONE.
         *   DONE:   Card reward probability probably like: 60% primary, 20% dual, 20% secondary.
         *          maybe more like 75% primary, 25% secondary - with both the primary and secondary card pool including the dual cards, which means they have a slightly bigger chance of occuring compared to any other singular card.
         * DONE: Add DUAL COLOR CARDS to the game.
         * DONE: Make DUAL COLOR CARDS come up if you chose the appropriate colors.
         * SCRAPPED: Add COLOR cards to the game.
         *
         *
         *
         *
         */

    }

    public static final SubColorMenu subColorMenu = new SubColorMenu();

    public static AbstractPlayer playerSecondary;

    
    public static AbstractPlayer.PlayerClass getSecondaryClass() {
        return playerSecondary.chosenClass;
    }

    public static CardGroup secondaryCommonCardPool;
    public static CardGroup secondaryUncommonCardPool;
    public static CardGroup secondaryRareCardPool;
    public static CardGroup srcSecondaryCommonCardPool;
    public static CardGroup srcSecondaryUncommonCardPool;
    public static CardGroup srcSecondaryRareCardPool;

    public static void initializeSecondaryCardPools() {
        logger.info("INIT SECONDARY CARD POOL");
        long startTime = System.currentTimeMillis();
        secondaryCommonCardPool.clear();
        secondaryUncommonCardPool.clear();
        secondaryRareCardPool.clear();
        ArrayList<AbstractCard> tmpPool = new ArrayList<>();
        if (ModHelper.isModEnabled("Colorless Cards"))
            CardLibrary.addColorlessCards(tmpPool);
        if (ModHelper.isModEnabled("Diverse")) {
            CardLibrary.addRedCards(tmpPool);
            CardLibrary.addGreenCards(tmpPool);
            CardLibrary.addBlueCards(tmpPool);
            if (!UnlockTracker.isCharacterLocked("Watcher"))
                CardLibrary.addPurpleCards(tmpPool);
        } else {
            playerSecondary.getCardPool(tmpPool);
        }
        for (AbstractCard c : tmpPool) {
            switch (c.rarity) {
                case COMMON:
                    secondaryCommonCardPool.addToTop(c);
                    continue;
                case UNCOMMON:
                    secondaryUncommonCardPool.addToTop(c);
                    continue;
                case RARE:
                    secondaryRareCardPool.addToTop(c);
                    continue;
                case CURSE:
                    continue;
            }
            logger.info("Unspecified rarity: " + c.rarity
                    .name() + " when creating pools! HugYouColors");
        }
        srcSecondaryRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        srcSecondaryUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        srcSecondaryCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractCard c : secondaryRareCardPool.group)
            srcSecondaryRareCardPool.addToBottom(c);
        for (AbstractCard c : secondaryUncommonCardPool.group)
            srcSecondaryUncommonCardPool.addToBottom(c);
        for (AbstractCard c : secondaryCommonCardPool.group)
            srcSecondaryCommonCardPool.addToBottom(c);
        logger.info("Secondary Cardpool load time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void initializeDualCardPools() {
        logger.info("INIT DUAL CARD POOL");
        long startTime = System.currentTimeMillis();

        ArrayList<AbstractCard> tmpPool = new ArrayList<>();

        CardLibrary.addCardsIntoPool(tmpPool, Enums.CARD_DUAL_RG_COLOR);
        CardLibrary.addCardsIntoPool(tmpPool, Enums.CARD_DUAL_RB_COLOR);
        CardLibrary.addCardsIntoPool(tmpPool, Enums.CARD_DUAL_GB_COLOR);
        CardLibrary.addCardsIntoPool(tmpPool, Enums.CARD_DUAL_GP_COLOR);
        CardLibrary.addCardsIntoPool(tmpPool, Enums.CARD_DUAL_RP_COLOR);
        CardLibrary.addCardsIntoPool(tmpPool, Enums.CARD_DUAL_BP_COLOR);

        for (AbstractCard c : tmpPool) {
            if (!(c instanceof DualCard) || !((DualCard)c).belongsTo(AbstractDungeon.player.getCardColor(), playerSecondary.getCardColor()))
                continue;
            switch (c.rarity) {
                case COMMON:
                    secondaryCommonCardPool.addToTop(c);
                    srcSecondaryCommonCardPool.addToTop(c);
                    AbstractDungeon.commonCardPool.addToTop(c);
                    AbstractDungeon.srcCommonCardPool.addToTop(c);
                    continue;
                case UNCOMMON:
                    secondaryUncommonCardPool.addToTop(c);
                    srcSecondaryUncommonCardPool.addToTop(c);
                    AbstractDungeon.uncommonCardPool.addToTop(c);
                    AbstractDungeon.srcUncommonCardPool.addToTop(c);
                    continue;
                case RARE:
                    secondaryRareCardPool.addToTop(c);
                    srcSecondaryRareCardPool.addToTop(c);
                    AbstractDungeon.rareCardPool.addToTop(c);
                    AbstractDungeon.srcRareCardPool.addToTop(c);
                    continue;
                case CURSE:
                    continue;
            }
            logger.info("Unspecified rarity: " + c.rarity
                    .name() + " when creating pools! HugYouColors, Basicmod");
        }
        logger.info("Dual cards added to load times: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public Texture MergeTextures(TextureAtlas.AtlasRegion texture1, TextureAtlas.AtlasRegion texture2) {

        if (!texture1.getTexture().getTextureData().isPrepared())
            texture1.getTexture().getTextureData().prepare();

        Pixmap pixmap = new Pixmap(
                texture1.originalWidth,
                texture1.originalHeight,
                texture1.getTexture().getTextureData().getFormat()
        );

        pixmap.drawPixmap(
                texture1.getTexture().getTextureData().consumePixmap(),
                (int)texture1.offsetX,
                (int)texture1.offsetY,
                texture1.getRegionX(),
                texture1.getRegionY(),
                texture1.getRegionWidth()/2,
                texture1.getRegionHeight()
        );

        if (!texture2.getTexture().getTextureData().isPrepared())
            texture2.getTexture().getTextureData().prepare();

        pixmap.drawPixmap(
                texture2.getTexture().getTextureData().consumePixmap(),
                (int)texture1.offsetX+(texture1.getRegionWidth()/2),
                (int)texture2.offsetY,//(int)texture2.offsetY,
                texture2.getRegionX()+(texture2.getRegionWidth()/2),
                texture2.getRegionY(),
                texture2.getRegionWidth()/2,
                texture2.getRegionHeight()
        );

        Texture toRet = new Texture(pixmap);

        pixmap.dispose();

        return toRet;
    }

    public static Texture RED_GREEN_DUAL_TEXTURE_SKILL;
    public static Texture RED_BLUE_DUAL_TEXTURE_SKILL;
    public static Texture RED_PURPLE_DUAL_TEXTURE_SKILL;
    public static Texture GREEN_BLUE_DUAL_TEXTURE_SKILL;
    public static Texture GREEN_PURPLE_DUAL_TEXTURE_SKILL;
    public static Texture BLUE_PURPLE_DUAL_TEXTURE_SKILL;
    public static Texture RED_GREEN_DUAL_TEXTURE_SKILL_L;
    public static Texture RED_BLUE_DUAL_TEXTURE_SKILL_L;
    public static Texture RED_PURPLE_DUAL_TEXTURE_SKILL_L;
    public static Texture GREEN_BLUE_DUAL_TEXTURE_SKILL_L;
    public static Texture GREEN_PURPLE_DUAL_TEXTURE_SKILL_L;
    public static Texture BLUE_PURPLE_DUAL_TEXTURE_SKILL_L;
    public static Texture RED_GREEN_DUAL_TEXTURE_ATTACK;
    public static Texture RED_BLUE_DUAL_TEXTURE_ATTACK;
    public static Texture RED_PURPLE_DUAL_TEXTURE_ATTACK;
    public static Texture GREEN_BLUE_DUAL_TEXTURE_ATTACK;
    public static Texture GREEN_PURPLE_DUAL_TEXTURE_ATTACK;
    public static Texture BLUE_PURPLE_DUAL_TEXTURE_ATTACK;
    public static Texture RED_GREEN_DUAL_TEXTURE_ATTACK_L;
    public static Texture RED_BLUE_DUAL_TEXTURE_ATTACK_L;
    public static Texture RED_PURPLE_DUAL_TEXTURE_ATTACK_L;
    public static Texture GREEN_BLUE_DUAL_TEXTURE_ATTACK_L;
    public static Texture GREEN_PURPLE_DUAL_TEXTURE_ATTACK_L;
    public static Texture BLUE_PURPLE_DUAL_TEXTURE_ATTACK_L;
    public static Texture RED_GREEN_DUAL_TEXTURE_POWER;
    public static Texture RED_BLUE_DUAL_TEXTURE_POWER;
    public static Texture RED_PURPLE_DUAL_TEXTURE_POWER;
    public static Texture GREEN_BLUE_DUAL_TEXTURE_POWER;
    public static Texture GREEN_PURPLE_DUAL_TEXTURE_POWER;
    public static Texture BLUE_PURPLE_DUAL_TEXTURE_POWER;
    public static Texture RED_GREEN_DUAL_TEXTURE_POWER_L;
    public static Texture RED_BLUE_DUAL_TEXTURE_POWER_L;
    public static Texture RED_PURPLE_DUAL_TEXTURE_POWER_L;
    public static Texture GREEN_BLUE_DUAL_TEXTURE_POWER_L;
    public static Texture GREEN_PURPLE_DUAL_TEXTURE_POWER_L;
    public static Texture BLUE_PURPLE_DUAL_TEXTURE_POWER_L;

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(resourcePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);

        secondaryCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        secondaryUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        secondaryRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        srcSecondaryCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        srcSecondaryUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        srcSecondaryRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

        //Making some textures here apparently
        //I think we're close!

        RED_GREEN_DUAL_TEXTURE_SKILL = MergeTextures(CARD_SKILL_BG_RED, CARD_SKILL_BG_GREEN);
        RED_BLUE_DUAL_TEXTURE_SKILL = MergeTextures(CARD_SKILL_BG_RED, CARD_SKILL_BG_BLUE);
        RED_PURPLE_DUAL_TEXTURE_SKILL = MergeTextures(CARD_SKILL_BG_RED, CARD_SKILL_BG_PURPLE);
        GREEN_BLUE_DUAL_TEXTURE_SKILL = MergeTextures(CARD_SKILL_BG_GREEN, CARD_SKILL_BG_BLUE);
        GREEN_PURPLE_DUAL_TEXTURE_SKILL = MergeTextures(CARD_SKILL_BG_GREEN, CARD_SKILL_BG_PURPLE);
        BLUE_PURPLE_DUAL_TEXTURE_SKILL = MergeTextures(CARD_SKILL_BG_BLUE, CARD_SKILL_BG_PURPLE);
        RED_GREEN_DUAL_TEXTURE_SKILL_L = MergeTextures(CARD_SKILL_BG_RED_L, CARD_SKILL_BG_GREEN_L);
        RED_BLUE_DUAL_TEXTURE_SKILL_L = MergeTextures(CARD_SKILL_BG_RED_L, CARD_SKILL_BG_BLUE_L);
        RED_PURPLE_DUAL_TEXTURE_SKILL_L = MergeTextures(CARD_SKILL_BG_RED_L, CARD_SKILL_BG_PURPLE_L);
        GREEN_BLUE_DUAL_TEXTURE_SKILL_L = MergeTextures(CARD_SKILL_BG_GREEN_L, CARD_SKILL_BG_BLUE_L);
        GREEN_PURPLE_DUAL_TEXTURE_SKILL_L = MergeTextures(CARD_SKILL_BG_GREEN_L, CARD_SKILL_BG_PURPLE_L);
        BLUE_PURPLE_DUAL_TEXTURE_SKILL_L = MergeTextures(CARD_SKILL_BG_BLUE_L, CARD_SKILL_BG_PURPLE_L);
        RED_GREEN_DUAL_TEXTURE_ATTACK = MergeTextures(CARD_ATTACK_BG_RED, CARD_ATTACK_BG_GREEN);
        RED_BLUE_DUAL_TEXTURE_ATTACK = MergeTextures(CARD_ATTACK_BG_RED, CARD_ATTACK_BG_BLUE);
        RED_PURPLE_DUAL_TEXTURE_ATTACK = MergeTextures(CARD_ATTACK_BG_RED, CARD_ATTACK_BG_PURPLE);
        GREEN_BLUE_DUAL_TEXTURE_ATTACK = MergeTextures(CARD_ATTACK_BG_GREEN, CARD_ATTACK_BG_BLUE);
        GREEN_PURPLE_DUAL_TEXTURE_ATTACK = MergeTextures(CARD_ATTACK_BG_GREEN, CARD_ATTACK_BG_PURPLE);
        BLUE_PURPLE_DUAL_TEXTURE_ATTACK = MergeTextures(CARD_ATTACK_BG_BLUE, CARD_ATTACK_BG_PURPLE);
        RED_GREEN_DUAL_TEXTURE_ATTACK_L = MergeTextures(CARD_ATTACK_BG_RED_L, CARD_ATTACK_BG_GREEN_L);
        RED_BLUE_DUAL_TEXTURE_ATTACK_L = MergeTextures(CARD_ATTACK_BG_RED_L, CARD_ATTACK_BG_BLUE_L);
        RED_PURPLE_DUAL_TEXTURE_ATTACK_L = MergeTextures(CARD_ATTACK_BG_RED_L, CARD_ATTACK_BG_PURPLE_L);
        GREEN_BLUE_DUAL_TEXTURE_ATTACK_L = MergeTextures(CARD_ATTACK_BG_GREEN_L, CARD_ATTACK_BG_BLUE_L);
        GREEN_PURPLE_DUAL_TEXTURE_ATTACK_L = MergeTextures(CARD_ATTACK_BG_GREEN_L, CARD_ATTACK_BG_PURPLE_L);
        BLUE_PURPLE_DUAL_TEXTURE_ATTACK_L = MergeTextures(CARD_ATTACK_BG_BLUE_L, CARD_ATTACK_BG_PURPLE_L);
        RED_GREEN_DUAL_TEXTURE_POWER = MergeTextures(CARD_POWER_BG_RED, CARD_POWER_BG_GREEN);
        RED_BLUE_DUAL_TEXTURE_POWER = MergeTextures(CARD_POWER_BG_RED, CARD_POWER_BG_BLUE);
        RED_PURPLE_DUAL_TEXTURE_POWER = MergeTextures(CARD_POWER_BG_RED, CARD_POWER_BG_PURPLE);
        GREEN_BLUE_DUAL_TEXTURE_POWER = MergeTextures(CARD_POWER_BG_GREEN, CARD_POWER_BG_BLUE);
        GREEN_PURPLE_DUAL_TEXTURE_POWER = MergeTextures(CARD_POWER_BG_GREEN, CARD_POWER_BG_PURPLE);
        BLUE_PURPLE_DUAL_TEXTURE_POWER = MergeTextures(CARD_POWER_BG_BLUE, CARD_POWER_BG_PURPLE);
        RED_GREEN_DUAL_TEXTURE_POWER_L = MergeTextures(CARD_POWER_BG_RED_L, CARD_POWER_BG_GREEN_L);
        RED_BLUE_DUAL_TEXTURE_POWER_L = MergeTextures(CARD_POWER_BG_RED_L, CARD_POWER_BG_BLUE_L);
        RED_PURPLE_DUAL_TEXTURE_POWER_L = MergeTextures(CARD_POWER_BG_RED_L, CARD_POWER_BG_PURPLE_L);
        GREEN_BLUE_DUAL_TEXTURE_POWER_L = MergeTextures(CARD_POWER_BG_GREEN_L, CARD_POWER_BG_BLUE_L);
        GREEN_PURPLE_DUAL_TEXTURE_POWER_L = MergeTextures(CARD_POWER_BG_GREEN_L, CARD_POWER_BG_PURPLE_L);
        BLUE_PURPLE_DUAL_TEXTURE_POWER_L = MergeTextures(CARD_POWER_BG_BLUE_L, CARD_POWER_BG_PURPLE_L);

        // savable

        BaseMod.addSaveField("SecondaryColor", new CustomSavable<AbstractPlayer.PlayerClass>() {

            @Override
            public AbstractPlayer.PlayerClass onSave() {
                return BasicMod.playerSecondary.chosenClass;
            }

            @Override
            public void onLoad(AbstractPlayer.PlayerClass save) {
                BasicMod.playerSecondary = CardCrawlGame.characterManager.recreateCharacter(save);
            }

        });

    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String resourcePath(String file) {
        return resourcesFolder + "/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(BasicMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(DualCard.class)
                .setDefaultSeen(true)
                .cards();

        //Variables here!

        BaseMod.addDynamicVariable(new HalfDamageVariable());
    }
}
