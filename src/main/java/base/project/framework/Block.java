package base.project.framework;

public class Block {
    private final double X, Y, WIDTH, HEIGHT;
    private boolean canPlace = true;
    private String kindOfBlock;

    public Block(double X, double Y, double WIDTH, double HEIGHT) {
        this.X = X;
        this.Y = Y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public boolean contains(double mouseX, double mouseY) {
        return mouseX >= X && mouseX <= X + WIDTH &&
               mouseY >= Y && mouseY <= Y + HEIGHT;
    }

    public boolean isAvailable() {
        return canPlace;
    }

    public void setOccupied() {
        canPlace = false;
    }

    public void setAvailable() {
        canPlace = true;
    }
    public void setKindOfBlock(String kind) {
        this.kindOfBlock = kind;
    }
    public String getKindOfBlock() {
        return kindOfBlock;
    }

    public double getX() { return X; }
    public double getY() { return Y; }
    public double getWIDTH() { return WIDTH; }
    public double getHEIGHT() { return HEIGHT; }
}
