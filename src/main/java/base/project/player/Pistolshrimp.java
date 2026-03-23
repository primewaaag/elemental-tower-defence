package base.project.player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import base.project.bases.BaseEntity;
import base.project.framework.Block;
import base.project.vars.Const;
import base.project.vars.Images;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pistolshrimp extends BaseEntity {
    private int health = 30;
    private final int DAMAGE = 20;
    private double cooldown = 35;
    private Block block;

    GraphicsContext gc;
    List<PistolShrimpProjectile> shrimpShots = new CopyOnWriteArrayList<>();

    public Pistolshrimp(double x, double y, Image image, List<PistolShrimpProjectile> shrimpShots, GraphicsContext gc) {
        super(x, y, image);
        this.gc = gc;
        this.shrimpShots = shrimpShots;
    }

    @Override
    public void update(double deltaInSec) {
        if (cooldown <= 0) {
            shoot(gc);
            cooldown = 25;
        } else {
            cooldown -= deltaInSec;
        }
    }

    @Override
    public void shoot(GraphicsContext gc) {
        PistolShrimpProjectile projectile = new PistolShrimpProjectile(this.x, this.y, Images.ShrimpProjectileImage, DAMAGE, shrimpShots);
        shrimpShots.add(projectile);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, Const.WATER_GOLEM_HITBOX_CORNER_X, Const.WATER_GOLEM_HITBOX_CORNER_Y);
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