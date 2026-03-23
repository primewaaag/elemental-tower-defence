package base.project.player;

import base.project.bases.BaseEntity;
import base.project.framework.Block;
import base.project.vars.Const;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Crab extends BaseEntity {
    private int health = 25;
    private final int DAMAGE = 3;
    private double cooldown = 1.5;
    private Block block;
    private final AudioClip crabattackSound = new AudioClip(getClass().getResource("/sounds/player/crab-pinching-sound.mp3").toExternalForm());
    
    public Crab(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public void attack(BaseEntity target, double deltaInSec) {
        if (cooldown <= 0) {
            target.setHealth(target.getHealth() - DAMAGE);
            System.out.println("Crab attack");
            crabattackSound.setVolume(0.25);
            crabattackSound.play();
            cooldown = 1.5;
        } else {
            cooldown -= deltaInSec;
        }
    }
    
    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x + Const.CRAB_OFFSET_X, y + Const.CRAB_OFFSET_Y, Const.CRAB_HITBOX_CORNER_X, Const.CRAB_HITBOX_CORNER_Y);
    }

    protected BoundingBox getAttackBox() {
        return new BoundingBox(x + Const.CRAB_OFFSET_X, y + Const.CRAB_OFFSET_Y, 325, Const.CRAB_HITBOX_CORNER_Y);
    }

    public boolean collidesWithAttackBox(BaseEntity target) {
        return getAttackBox().intersects(target.getBoundingBox());
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

    public void setBlock(Block b) {
        this.block = b;
    }

    public Block getBlock() {
        return block;
    }
}