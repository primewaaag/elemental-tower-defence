package base.project.enemy;

import java.util.List;

import base.project.bases.BaseProjectile;
import base.project.vars.Const;
import base.project.vars.Images;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class LavaGolemProjectile extends BaseProjectile {
    List<LavaGolemProjectile> lavaGolemShots;

    private final int SPEED = 200;

    public LavaGolemProjectile (double x, double y, Image image, int damage, List<LavaGolemProjectile> lavaGolemShots) {
        super(x, y, image, damage);
        this.lavaGolemShots = lavaGolemShots;
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;

        if (x <= 0 - Images.LavaProjectileImage.getWidth()) {
            lavaGolemShots.remove(this);
        } else {
            x -= distanceToMove;
        }
    }

    @Override
    protected BoundingBox getBoundingBox() {
        return new BoundingBox(x + Const.LGP_OFFSET_X, y + Const.LGP_OFFSET_Y, Const.LGP_HITBOX_CORNER_X, Const.LGP_HITBOX_CORNER_Y);
    }
}