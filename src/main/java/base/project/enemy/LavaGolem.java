package base.project.enemy;

import java.util.List;

import base.project.Main;
import base.project.bases.BaseEntity;
import base.project.framework.HighscoreManager;
import base.project.framework.SceneType;
import base.project.scenes.GameScene;
import base.project.vars.Const;
import base.project.vars.Images;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class LavaGolem extends BaseEntity {
    private int health = 20;
    private final int DAMAGE = 4;
    private final GraphicsContext gc;
    private double cooldown = 5;
    private int speed = 40;
    private final AudioClip gameOverSound = new AudioClip(getClass().getResource("/sounds/screens/game-over-sound.mp3").toExternalForm());
    private final AudioClip shootSound = new AudioClip(getClass().getResource("/sounds/enemys/lavagolem-attack-sound.mp3").toExternalForm());

    private final List<LavaGolemProjectile> lavaGolemShots;

    public LavaGolem(double x, double y, Image image, List<LavaGolemProjectile> lavaGolemShots, GraphicsContext gc) {
        super(x, y, image);
        this.gc = gc;
        this.lavaGolemShots = lavaGolemShots;
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = speed * deltaInSec;
        x -= distanceToMove;

        if (x <= 0 - image.getWidth()) {
            GameScene.highscoreTimerOn = false;
            HighscoreManager.saveHighscore(GameScene.timerOfRun);

            // Backgroundmusic stoppen
            if (GameScene.bgMusic != null) {
                GameScene.bgMusic.stop();
            }

            gameOverSound.play();
            Main.nav.navigateTo(SceneType.GAME_OVER);
        }

        if (cooldown <= 0) {
            shoot(gc);
            cooldown = 5;
        } else {
            cooldown -= deltaInSec;
        }
    }

    @Override
    public void shoot(GraphicsContext gc) {
        LavaGolemProjectile projectile = new LavaGolemProjectile(this.x, this.y, Images.LavaProjectileImage, DAMAGE, lavaGolemShots);
        lavaGolemShots.add(projectile);
        shootSound.play();
        shootSound.setVolume(0.2);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, Const.LAVA_GOLEM_HITBOX_CORNER_X, Const.LAVA_GOLEM_HITBOX_CORNER_Y);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}