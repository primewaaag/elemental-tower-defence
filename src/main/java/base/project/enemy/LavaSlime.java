package base.project.enemy;

import base.project.Main;
import base.project.bases.BaseEntity;
import base.project.framework.HighscoreManager;
import base.project.framework.SceneType;
import base.project.scenes.GameScene;
import base.project.vars.Const;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class LavaSlime extends BaseEntity {
    private int health = 15;
    private double shootcooldown = 2;
    private final int DAMAGE = 4;
    private double speed = 50;
    private final AudioClip gameOverSound = new AudioClip(getClass().getResource("/sounds/screens/game-over-sound.mp3").toExternalForm());

    public LavaSlime(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = speed * deltaInSec; // Gegner läuft mit bestimmter Geschwindigkeit
        x -= distanceToMove; // Gegner läuft permanent nach links

        if (x <= 0 - image.getWidth()) { // Grenzenüberschreitung = GameOver
            GameScene.highscoreTimerOn = false;
            HighscoreManager.saveHighscore(GameScene.timerOfRun);

            // Backgroundmusic stoppen
            if (GameScene.bgMusic != null) {
                GameScene.bgMusic.stop();
            }

            gameOverSound.play();
            Main.nav.navigateTo(SceneType.GAME_OVER);
        }
    }

    @Override
    public void attack(BaseEntity target, double deltaInSec) {
        if (shootcooldown <= 0) {
            target.setHealth(target.getHealth() - DAMAGE);
            shootcooldown = 3;
        } else {
            shootcooldown -= deltaInSec;
        }
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y + Const.LAVA_SLIME_OFFSET_Y, Const.LAVA_SLIME_HITBOX_CORNER_X, Const.LAVA_SLIME_HITBOX_CORNER_Y);
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}