package base.project.framework;

import java.util.ArrayList;
import java.util.List;

public class GameGrid {
    public int rows = 5;
    public int cols = 9;
    public double blockWidth = 110;
    public double blockHeight = 100;
    public double startX = 60;
    public double startY = 345;
    public double gapX = 10;
    public double gapY = 10;

    public int rows2 = 5;
    public int cols2 = 2;
    public double blockWidth2 = 130;
    public double blockHeight2 = 100;
    public double startX2 = 1140;
    public double startY2 = 345;
    public double gapX2 = 10;
    public double gapY2 = 10;
    public double offsetWatergolemX = 7;
    public double offsetWatergolemY = 0;
    public double offsetCoralX = 5;
    public double offsetCoralY = 0;
    public double offsetNautilusX = 15;
    public double offsetNautilusY = 0;
    public double offsetCrabX = 10;
    public double offsetCrabY = -20;
    public double offsetSeamineX = 7;
    public double offsetSeamineY = 0;
    public double offsetPistolshrimpX = 7;
    public double offsetPistolshrimpY = 0;

    private final List<Block> blocks = new ArrayList<>();

    public void addBlock(Block b) {
        blocks.add(b);
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}