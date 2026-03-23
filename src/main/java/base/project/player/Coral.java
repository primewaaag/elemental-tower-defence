package base.project.player;

import base.project.bases.BaseEntity;
import base.project.framework.Block;
import base.project.vars.Const;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Coral extends BaseEntity {
    private int health = 15;
    private double timeSinceLastBubble = 0;
    private final double BUBBLE_COOLDOWN = 40;
    private final int DAMAGE = 0;
    private Block block;
    
    public Coral(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y + Const.CORAL_OFFSET_Y, Const.CORAL_HITBOX_CORNER_X, Const.CORAL_HITBOX_CORNER_Y);
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
    public boolean updateAndShouldSpawn(double deltaInSec) {
        timeSinceLastBubble += deltaInSec;
        if (timeSinceLastBubble >= BUBBLE_COOLDOWN) {
            timeSinceLastBubble = 0;
            return true;
        }
    return false;
    }
}