package base.project.player;

import java.util.List;

import base.project.bases.BaseProjectile;
import base.project.vars.Const;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class PistolShrimpProjectile extends BaseProjectile {
    List<PistolShrimpProjectile> shrimpShots;
    private final AudioClip pistolshrimpshootSound = new AudioClip(getClass().getResource("/sounds/player/pistolshrimp-attack-sound.mp3").toExternalForm());

    private final int SPEED = 200;

    public PistolShrimpProjectile (double x, double y, Image image, int damage, List<PistolShrimpProjectile> shrimpShots) {
        super(x, y, image, damage);
        this.shrimpShots = shrimpShots;


        pistolshrimpshootSound.setVolume(0.25);
        pistolshrimpshootSound.play();
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;

        if (x >= Const.WIDTH) {
            shrimpShots.remove(this);
        } else {
            x += distanceToMove;
        }
    }

    @Override
    protected BoundingBox getBoundingBox() {
        return new BoundingBox(x + Const.WGP_OFFSET_X, y + Const.WGP_OFFSET_Y, Const.WGP_HITBOX_CORNER_X, Const.WGP_HITBOX_CORNER_Y);
    }
}