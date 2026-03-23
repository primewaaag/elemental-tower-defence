package base.project.player;

import java.util.List;

import base.project.bases.BaseEntity;
import javafx.scene.image.Image;

public class Bubble extends BaseEntity {
    private final int SPEED = 65;
    private final List<Bubble> bubbles;

    public Bubble(double x, double y, Image image, List<Bubble> bubbles) {
        super(x, y, image);
        this.bubbles = bubbles;
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;

        if (y <= 0) {
            bubbles.remove(this);
        } else {
            y -= distanceToMove;
        }
    }
}