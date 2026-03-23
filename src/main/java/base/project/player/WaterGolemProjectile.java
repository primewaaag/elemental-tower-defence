package base.project.player;

import java.util.List;

import base.project.bases.BaseProjectile;
import base.project.vars.Const;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class WaterGolemProjectile extends BaseProjectile {
    List<WaterGolemProjectile> waterGolemShots;

    private final int SPEED = 200;

    public WaterGolemProjectile (double x, double y, Image image, int damage, List<WaterGolemProjectile> waterGolemShots) {
        super(x, y, image, damage);
        this.waterGolemShots = waterGolemShots;
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;

        if (x >= Const.WIDTH) {
            waterGolemShots.remove(this);
        } else {
            x += distanceToMove;
        }
    }

    @Override
    protected BoundingBox getBoundingBox() {
        return new BoundingBox(x + Const.WGP_OFFSET_X, y + Const.WGP_OFFSET_Y, Const.WGP_HITBOX_CORNER_X, Const.WGP_HITBOX_CORNER_Y);
    }
}