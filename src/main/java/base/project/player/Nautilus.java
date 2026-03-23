package base.project.player;

import base.project.bases.BaseEntity;
import base.project.framework.Block;
import base.project.vars.Const;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class Nautilus extends BaseEntity {
    private int health = 40;
    private Block block;
    
    public Nautilus(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y + Const.NAUTILUS_OFFSET_Y, Const.NAUTILUS_HITBOX_CORNER_X, Const.NAUTILUS_HITBOX_CORNER_Y);
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