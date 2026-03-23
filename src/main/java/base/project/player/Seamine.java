package base.project.player;

import base.project.bases.BaseEntity;
import base.project.framework.Block;
import base.project.vars.Const;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Seamine extends BaseEntity {
    private final int DAMAGE = 15;
    private Block block;

    public Seamine(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x + Const.SEAMINE_OFFSET_X, y + Const.SEAMINE_OFFSET_Y, Const.SEAMINE_HITBOX_CORNER_X, Const.SEAMINE_HITBOX_CORNER_Y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    public void setBlock(Block b) {
        this.block = b;
    }

    public Block getBlock() {
        return block;
    }
}