package base.project.player;


import java.util.List;

import base.project.bases.BaseEntity;
import base.project.framework.Block;
import base.project.vars.Const;
import base.project.vars.Images;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class WaterGolem extends BaseEntity {
    private int health = 20;
    private final int DAMAGE = 4;
    private double cooldown = 3;
    private final GraphicsContext gc;
    private final AudioClip shootSound = new AudioClip(getClass().getResource("/sounds/player/pixel-sound-effect-water-golem-shoot.mp3").toExternalForm());


    private final List<WaterGolemProjectile> waterGolemShots;
    
    private Block block;

    public WaterGolem(double x, double y, Image image, List<WaterGolemProjectile> waterGolemShots, GraphicsContext gc) {
        super(x, y, image);
        this.waterGolemShots = waterGolemShots;
        this.gc = gc;
    }

    @Override
    public void update(double deltaInSec) {
        if (cooldown <= 0) {
            shoot(gc);
            cooldown = 3;
        } else {
            cooldown -= deltaInSec;
        }
    }

    @Override
    public void shoot(GraphicsContext gc) {
        WaterGolemProjectile projectile = new WaterGolemProjectile(this.x, this.y, Images.WaterProjectileImage, DAMAGE, waterGolemShots);
        waterGolemShots.add(projectile);
        shootSound.setVolume(0.4);
        shootSound.play();

    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, Const.WATER_GOLEM_HITBOX_CORNER_X, Const.WATER_GOLEM_HITBOX_CORNER_Y);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public void setBlock(Block b) {
        this.block = b;
    }

    public Block getBlock() {
        return block;
    }
}