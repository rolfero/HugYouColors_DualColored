package basicmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import basicmod.cards.DualCard;
import basicmod.dynamicvariables.HalfDamageVariable;
import basicmod.events.CharacterHelpEvent;
import basicmod.ui.SubColorMenu;
import basicmod.util.GeneralUtils;
import basicmod.util.KeywordInfo;
import basicmod.util.ModSliderBetter;
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
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.megacrit.cardcrawl.helpers.ImageMaster.*;


@SpireInitializer
public class HugYouColors implements
        EditStringsSubscriber,
        EditCardsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        StartGameSubscriber {

    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this

    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "HugYouColors";
    public static SpireConfig modConfig = null;
    public static SpireConfig currentRunConfig = null;
    public static boolean currentRunActive = false;
    public static boolean currentRunDualSetActive = false;
    public static boolean currentRunAllDualActive = false;
    public static int settingsSecondaryRewardChance = 25;

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //TODO: Can I strike through or otherwise remove the None: Dark part of Accord?
    public static boolean newGameStarted = false;

    @Override
    public void receiveStartGame() {
        if (HugYouColors.getActiveConfig()) {
            if (!newGameStarted) {
                int newOrbs = (AbstractDungeon.player.masterMaxOrbs + HugYouColors.playerSecondary.masterMaxOrbs + 1) / 2;
                AbstractDungeon.player.masterMaxOrbs = Math.max(newOrbs, AbstractDungeon.player.masterMaxOrbs);
            }

            newGameStarted = true;
        }

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
    private static final String ENERGY_ORB_RG_P = characterPath("cardback/energy_orb_rg_p.png");
    private static final String ENERGY_ORB_RB_P = characterPath("cardback/energy_orb_rb_p.png");
    private static final String ENERGY_ORB_RP_P = characterPath("cardback/energy_orb_rp_p.png");
    private static final String ENERGY_ORB_GB_P = characterPath("cardback/energy_orb_gb_p.png");
    private static final String ENERGY_ORB_GP_P = characterPath("cardback/energy_orb_gp_p.png");
    private static final String ENERGY_ORB_BP_P = characterPath("cardback/energy_orb_bp_p.png");
    private static final String ENERGY_ORB_RG = characterPath("cardback/energy_orb_rg.png");
    private static final String ENERGY_ORB_RB = characterPath("cardback/energy_orb_rb.png");
    private static final String ENERGY_ORB_RP = characterPath("cardback/energy_orb_rp.png");
    private static final String ENERGY_ORB_GB = characterPath("cardback/energy_orb_gb.png");
    private static final String ENERGY_ORB_GP = characterPath("cardback/energy_orb_gp.png");
    private static final String ENERGY_ORB_BP = characterPath("cardback/energy_orb_bp.png");
    private static final String SMALL_ORB_RG = characterPath("cardback/small_orb_rg.png");
    private static final String SMALL_ORB_RB = characterPath("cardback/small_orb_rb.png");
    private static final String SMALL_ORB_RP = characterPath("cardback/small_orb_rp.png");
    private static final String SMALL_ORB_GB = characterPath("cardback/small_orb_gb.png");
    private static final String SMALL_ORB_GP = characterPath("cardback/small_orb_gp.png");
    private static final String SMALL_ORB_BP = characterPath("cardback/small_orb_bp.png");

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    
    public static void initialize() {
        new HugYouColors();
        BaseMod.addColor(Enums.CARD_DUAL_RG_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB_RG,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_RG_P,
                SMALL_ORB_RG);
        BaseMod.addColor(Enums.CARD_DUAL_RB_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB_RB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_RB_P,
                SMALL_ORB_RB);
        BaseMod.addColor(Enums.CARD_DUAL_RP_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB_RP,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_RP_P,
                SMALL_ORB_RP);
        BaseMod.addColor(Enums.CARD_DUAL_GB_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB_GB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_GB_P,
                SMALL_ORB_GB);
        BaseMod.addColor(Enums.CARD_DUAL_GP_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB_GP,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_GP_P,
                SMALL_ORB_GP);
        BaseMod.addColor(Enums.CARD_DUAL_BP_COLOR, Color.WHITE,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB_BP,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_BP_P,
                SMALL_ORB_BP);

        try {
            Properties defaults = new Properties();
            defaults.put("active", "TRUE");
            defaults.put("dualset", "TRUE");
            defaults.put("dualall", "FALSE");
            defaults.put("secondarychance",25);
            modConfig = new SpireConfig(modID, "hugYouColorsConfig", defaults);
            modConfig.load();
            settingsSecondaryRewardChance = modConfig.getInt("secondarychance");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            currentRunConfig = new SpireConfig(modID, "hugYouColorsConfigCurrentRun");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getConfigDualSet() {
        return modConfig == null || modConfig.getBool("dualset");
    }

    public static void setConfigDualSet(boolean active) {
        if (modConfig != null) {
            modConfig.setBool("dualset",active);
            try {
                modConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getConfigDualAll() {
        return modConfig == null || modConfig.getBool("dualall");
    }

    public static void setConfigDualAll(boolean active) {
        if (modConfig != null) {
            modConfig.setBool("dualall",active);
            try {
                modConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getActiveConfig() {
        return modConfig == null || modConfig.getBool("active");
    }

    public static void setActiveConfig(boolean active) {
        if (modConfig != null) {
            modConfig.setBool("active",active);
            try {
                modConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    public HugYouColors() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    public static final SubColorMenu subColorMenu = new SubColorMenu();

    public static AbstractPlayer playerSecondary;

    
    public static AbstractPlayer.PlayerClass getSecondaryClass() {
        return playerSecondary.chosenClass;
    }

    public static CardGroup secondaryCommonCardPool;
    public static CardGroup secondaryUncommonCardPool;
    public static CardGroup secondaryRareCardPool;

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
            if (HugYouColors.getActiveConfig() && HugYouColors.getConfigDualSet()) {
                if (!(c instanceof DualCard) || !((DualCard) c).belongsTo(AbstractDungeon.player.getCardColor(), playerSecondary.getCardColor()))
                    continue;
            } else if (!HugYouColors.getActiveConfig() && HugYouColors.getConfigDualAll()) {
                if (!(c instanceof DualCard) || !((DualCard) c).belongsTo(AbstractDungeon.player.getCardColor()))
                    continue;
            } else {
                continue;
            }
            switch (c.rarity) {
                case COMMON:
                    secondaryCommonCardPool.addToTop(c);
                    AbstractDungeon.commonCardPool.addToTop(c);
                    AbstractDungeon.srcCommonCardPool.addToTop(c);
                    continue;
                case UNCOMMON:
                    secondaryUncommonCardPool.addToTop(c);
                    AbstractDungeon.uncommonCardPool.addToTop(c);
                    AbstractDungeon.srcUncommonCardPool.addToTop(c);
                    continue;
                case RARE:
                    secondaryRareCardPool.addToTop(c);
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

        BaseMod.addEvent(new AddEventParams.Builder(CharacterHelpEvent.ID, CharacterHelpEvent.class).endsWithRewardsUI(true).create());

        secondaryCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        secondaryUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        secondaryRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

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

        // SETTINGS HERE

        ModPanel settingsPanel = new ModPanel();
        ModSliderBetter cardChanceSlider = new ModSliderBetter(CardCrawlGame.languagePack.getUIString(makeID("Settings")).TEXT[0],
                650.0f, 700.0f,
                1.0f, 100.0f, (float)settingsSecondaryRewardChance,
                "%.0f",
                settingsPanel,
                (slider) -> {
                    settingsSecondaryRewardChance = (int)slider.getValue();
                    try {
                        modConfig.setInt("secondarychance", settingsSecondaryRewardChance);
                        modConfig.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(cardChanceSlider);

        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, settingsPanel);

        // SAVABLE HERE

        BaseMod.addSaveField("SecondaryColor", new CustomSavable<AbstractPlayer.PlayerClass>() {

            @Override
            public AbstractPlayer.PlayerClass onSave() {
                return HugYouColors.playerSecondary.chosenClass;
            }

            @Override
            public void onLoad(AbstractPlayer.PlayerClass save) {
                HugYouColors.playerSecondary = CardCrawlGame.characterManager.recreateCharacter(save);
            }

        });
        BaseMod.addSaveField("CurrentRunActive", new CustomSavable<Boolean>() {

            @Override
            public Boolean onSave() {
                return currentRunActive;
            }

            @Override
            public void onLoad(Boolean save) {
                currentRunActive = save == null || save;
            }

        });
        BaseMod.addSaveField("CurrentRunDualSet", new CustomSavable<Boolean>() {

            @Override
            public Boolean onSave() {
                return currentRunDualSetActive;
            }

            @Override
            public void onLoad(Boolean save) {
                currentRunDualSetActive = save == null || save;
            }

        });
        BaseMod.addSaveField("CurrentRunDualAll", new CustomSavable<Boolean>() {

            @Override
            public Boolean onSave() {
                return currentRunAllDualActive;
            }

            @Override
            public void onLoad(Boolean save) {
                currentRunAllDualActive = save != null && save;
            }

        });
        BaseMod.addSaveField("FirstGameStart", new CustomSavable<Boolean>() {

            @Override
            public Boolean onSave() {
                return newGameStarted;
            }

            @Override
            public void onLoad(Boolean save) {
                newGameStarted = save != null && save;
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
            return initializers.contains(HugYouColors.class.getName());
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
