package basicmod.events;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import basicmod.HugYouColors;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static basicmod.HugYouColors.makeID;

public class CharacterHelpEvent extends PhasedEvent {
    public static final String ID = makeID("CharacterHelpEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;

    public CharacterHelpEvent() {
        super(ID, title, "images/events/ShadowyFigure.png");

        setImage(); // If it can find a mod image, it uses that instead.

        registerPhase("EventStart", new TextPhase(DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[1]) {
            @Override
            public String getBody() {
                if (HugYouColors.playerSecondary != null) {
                    return DESCRIPTIONS[0] + HugYouColors.playerSecondary.title + DESCRIPTIONS[1];
                } else {
                    return super.getBody();
                }
            }
        }.addOption(OPTIONS[0], (i)->transitionKey("Combat")).addOption(OPTIONS[1], (i)->this.openMap()));

        registerPhase("Combat", new CombatPhase(AbstractDungeon.eliteMonsterList.remove(0)) {
            @Override
            public boolean reopen(PhasedEvent phasedEvent) {
                boolean b = super.reopen(phasedEvent);
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                return b;
            }
        }.setType(AbstractMonster.EnemyType.ELITE).addRewards(true, (room)->{

            //Adds all starting relics to the reward screen
            ArrayList<String> relicStrings = new ArrayList<>();
            if (HugYouColors.playerSecondary != null) {
                relicStrings = HugYouColors.playerSecondary.getStartingRelics();
            }

            for (String relicID : relicStrings) {
                room.rewards.add(new RewardItem(RelicLibrary.getRelic(relicID).makeCopy()));
            }

        }));

        transitionKey("EventStart"); //starting point
    }

    // Sets the Event image to a cropped version of the character portrait.
    public void setImage() {

        Texture portraitTexture = null;

        // Finds the portrait
        if (HugYouColors.playerSecondary != null) {
            if (Gdx.files.internal("images/ui/charSelect/" + HugYouColors.playerSecondary.getPortraitImageName()).exists()) {
                portraitTexture = ImageMaster.loadImage("images/ui/charSelect/" + HugYouColors.playerSecondary.getPortraitImageName());
            } else if (Gdx.files.internal(BaseMod.playerPortraitMap.get(HugYouColors.playerSecondary.chosenClass)).exists()) {
                portraitTexture = ImageMaster.loadImage((BaseMod.playerPortraitMap.get(HugYouColors.playerSecondary.chosenClass)));
            }
        }

        if (portraitTexture == null) return;

        if (!portraitTexture.getTextureData().isPrepared())
            portraitTexture.getTextureData().prepare();

        Pixmap pixmap = new Pixmap(
                600,
                600,
                portraitTexture.getTextureData().getFormat()
        );

        pixmap.drawPixmap(
                portraitTexture.getTextureData().consumePixmap(),
                720,
                0,
                1200,
                portraitTexture.getHeight(),
                0,
                0,
                600,
                600
        );

        Texture newTexture = new Texture(pixmap);

        // Repeats what this.imageEventText.loadImage(imgUrl) does, with ReflectionHacks.

        if (ReflectionHacks.getPrivate(this.imageEventText, GenericEventDialog.class, "img") != null) {
            ((Texture)ReflectionHacks.getPrivate(this.imageEventText, GenericEventDialog.class, "img")).dispose();
            ReflectionHacks.setPrivate(this.imageEventText, GenericEventDialog.class, "img", null);
        }

        ReflectionHacks.setPrivate(this.imageEventText, GenericEventDialog.class, "img", newTexture);

        ReflectionHacks.setPrivateStatic(GenericEventDialog.class, "DIALOG_MSG_X", ReflectionHacks.getPrivateStatic(GenericEventDialog.class, "DIALOG_MSG_X_IMAGE"));
        ReflectionHacks.setPrivateStatic(GenericEventDialog.class, "DIALOG_MSG_W", ReflectionHacks.getPrivateStatic(GenericEventDialog.class, "DIALOG_MSG_W_IMAGE"));

        pixmap.dispose();

    }

    @Override
    public void enterCombat() {
        super.enterCombat();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToTop(new LoseHPAction(m, AbstractDungeon.player, m.currentHealth/4));
        }
    }
}
