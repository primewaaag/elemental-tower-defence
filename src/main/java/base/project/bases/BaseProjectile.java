package base.project.bases;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// NOTE: Von dieser Klasse werden keine objekte erstellt, sie ist nur dazu da die basis für alle anderen Porjektil Klassen zu liefern

public class BaseProjectile {
    protected double x;
    protected double y;
    protected final int DAMAGE;
    protected final Image image;

    public BaseProjectile(double x, double y, Image image, int damage) {
        this.x = x;
        this.y = y;
        this.DAMAGE = damage;
        this.image = image;
        }

    public void update(double deltaInSec) {
        throw new UnsupportedOperationException("Method Update was not overriden in Class Definition!");
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public boolean collidesWith(BaseEntity target) {
        return getBoundingBox().intersects(target.getBoundingBox());
    }

    public boolean collidesWith(BaseProjectile target) {
        return getBoundingBox().intersects(target.getBoundingBox());
    }

    protected BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, image.getWidth(), image.getHeight());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}