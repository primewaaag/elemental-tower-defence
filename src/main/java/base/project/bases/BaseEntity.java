package base.project.bases;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class BaseEntity {
    protected double x;
    protected double y;
    protected final Image image;

    public BaseEntity(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void update(double deltaInSec) {
        throw new UnsupportedOperationException("Method update was not Overriden in class definition!");
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public void shoot(GraphicsContext gc) {
        throw new UnsupportedOperationException("Method attack was not Overriden in class definition!");
    }

    public void attack(BaseEntity target, double deltaInSec) {
        throw new UnsupportedOperationException("Method attack was not Overriden in class definition");
    }

    public boolean collidesWith(BaseEntity target) {
        return getBoundingBox().intersects(target.getBoundingBox());
    }

    public boolean collidesWith(BaseProjectile target) {
        return getBoundingBox().intersects(target.getBoundingBox());
    }

    public BoundingBox getBoundingBox() {
        throw new UnsupportedOperationException("Method getBoundingBox was not Overriden in class definition!");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setHealth(int health) {
        throw new UnsupportedOperationException("Method setHealth was not Overriden in class definition");
    }

    public int getHealth() {
        throw new UnsupportedOperationException("Method setHealth was not Overriden in class definition");
    }
}