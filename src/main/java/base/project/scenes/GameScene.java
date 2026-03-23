package base.project.scenes;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import base.project.Main;
import base.project.bases.BaseScene;
import base.project.enemy.LavaGolem;
import base.project.enemy.LavaGolemProjectile;
import base.project.enemy.LavaSlime;
import base.project.framework.Block;
import base.project.framework.CollisionHandler;
import base.project.framework.GameGrid;
import base.project.framework.HighscoreManager;
import base.project.framework.Navigator;
import base.project.framework.SceneType;
import base.project.player.Bubble;
import base.project.player.Coral;
import base.project.player.Crab;
import base.project.player.Nautilus;
import base.project.player.PistolShrimpProjectile;
import base.project.player.Pistolshrimp;
import base.project.player.Seamine;
import base.project.player.WaterGolem;
import base.project.player.WaterGolemProjectile;
import base.project.vars.Const;
import base.project.vars.Images;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameScene extends BaseScene {
    // -- Variabeln --
    public static MediaPlayer bgMusic; // public gesetzt für andere Klassen
    private GraphicsContext gc;
    private CollisionHandler collisionHandler;

    private double deltaInSec;
    private long lastTimeInNanoSec;
    private boolean watergolemSelected = false;
    private boolean coralSelected = false;
    private boolean nautilusSelected = false;
    private boolean crabSelected = false;
    private boolean seamineSelected = false;
    private boolean pistolshrimpSelected = false;
    private boolean shovelSelected = false;
    private boolean deleteenemiesshovelSelected = false;

    private double alertTimer = 0;
    private String alertMessage = "";

    public static boolean highscoreTimerOn = true;
    public static double timerOfRun = 0;

    private final List<WaterGolem> waterGolems = new CopyOnWriteArrayList<>();
    private final List<Coral> corals = new CopyOnWriteArrayList<>();
    private final List<Nautilus> nautiluses = new CopyOnWriteArrayList<>();
    private final List<Crab> crabs = new CopyOnWriteArrayList<>();
    private final List<Seamine> seamines = new CopyOnWriteArrayList<>();
    private final List<Pistolshrimp> pistolshrimps = new CopyOnWriteArrayList<>();

    private final GameGrid grid = new GameGrid();

    private final List<WaterGolemProjectile> waterGolemShots = new CopyOnWriteArrayList<>();
    private final List<LavaGolemProjectile> lavaGolemShots = new CopyOnWriteArrayList<>();
    private final List<PistolShrimpProjectile> shrimpShots = new CopyOnWriteArrayList<>();

    private AnimationTimer timer;
    Random rand = new Random();

    // Sounds
    private final AudioClip bubbleclickSound = new AudioClip(getClass().getResource("/sounds/click-bubble-sound.mp3").toExternalForm()); // bubble click

    //LavaGolem Wavegenerierung
    // Variabeln
    private final List<LavaGolem> lavaGolems = new CopyOnWriteArrayList<>();
    private double timeSinceLastSpawnlavaGolem;
    private final double lavaGolemCooldown = 12 + rand.nextInt(13);

    // LavaSlime Wavegenerierung
    private final double[] rowsY = { 340, 450, 565, 680, 790 };
    private double timeSinceLastSpawnlavaslime;
    private final double lavaSlimeCooldown = 7 + rand.nextInt(14);
    private final List<LavaSlime> slimes = new CopyOnWriteArrayList<>();

    // Bubble Klassen Variabeln
    private final List<Bubble> bubbles = new CopyOnWriteArrayList<>();
    private double timeSinceLastBubbleSpawn;
    private final double BUBBLE_COOLDOWN = 15 + rand.nextInt(11);
    private int amountOfBubbles;

    private boolean hitboxesOn = false;
    private javafx.scene.ImageCursor shovelCursor;
    private javafx.scene.Cursor defaultCursor;
    // -------------------------

    public GameScene(Navigator navigator) {
        super(navigator);
    }

    // Main Funktion (hier Code schreiben)
    @Override
    public void onEnter() {
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(Images.gameBackgroundImage, 0, 0);
        // Timer create
        highscoreTimerOn = true;
        timerOfRun = 0;


        // music loading (background)
        Media bg = new Media(getClass().getResource("/sounds/screens/pixel-gamebackground-sound.mp3").toExternalForm());
        bgMusic = new MediaPlayer(bg);

        // Einstellungen
        bgMusic.setCycleCount(MediaPlayer.INDEFINITE); // endless loop background music
        bgMusic.setVolume(0.3); // Volume is 30%

        // Start
        bgMusic.play();

        canvas.setFocusTraversable(true);

        collisionHandler = new CollisionHandler(waterGolems, corals, nautiluses, crabs, slimes, lavaGolems, waterGolemShots, lavaGolemShots, seamines, pistolshrimps, shrimpShots);

        shovelCursor = new javafx.scene.ImageCursor(Images.ShovelCursor);
        defaultCursor = javafx.scene.Cursor.DEFAULT;

        // Reset EVERYTHING
        waterGolems.clear();
        corals.clear();
        nautiluses.clear();
        crabs.clear();
        seamines.clear();
        pistolshrimps.clear();

        waterGolemShots.clear();
        lavaGolemShots.clear();
        bubbles.clear();

        slimes.clear();
        lavaGolems.clear();

        timeSinceLastBubbleSpawn = 20;
        timeSinceLastSpawnlavaGolem = -10;
        timeSinceLastSpawnlavaslime = -10;

        amountOfBubbles = 50;
        // -------------------------

        // -- GRID STUFF --
        for (int row = 0; row < grid.rows; row++) {
            for (int col = 0; col < grid.cols; col++) {
                double x = grid.startX + col * (grid.blockWidth + grid.gapX);
                double y = grid.startY + row * (grid.blockHeight + grid.gapY);
                grid.addBlock(new Block(x, y, grid.blockWidth, grid.blockHeight));
            }
        }
        for (int row = 0; row < grid.rows2; row++) {
            for (int col = 0; col < grid.cols2; col++) {
                double x = grid.startX2 + col * (grid.blockWidth2 + grid.gapX2);
                double y = grid.startY2 + row * (grid.blockHeight2 + grid.gapY2);
                grid.addBlock(new Block(x, y, grid.blockWidth2, grid.blockHeight2));
            }
        }
        // -------------------------

        // -- Animation Timer --
        lastTimeInNanoSec = System.nanoTime();
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long currentTimeInNanoSec) {                   
                long deltaInNanoSec = currentTimeInNanoSec - lastTimeInNanoSec;
                deltaInSec = deltaInNanoSec / 1e9d;             
                lastTimeInNanoSec = currentTimeInNanoSec;
                update(deltaInSec);
                paint();
            }
        };

        timer.start();
        // ---------------------

        canvas.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.H) {
                hitboxesOn = !hitboxesOn;
            }

            if (e.getCode() == KeyCode.DIGIT1) {
                coralSelected = !coralSelected;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                deleteenemiesshovelSelected = false;
            }

            if (e.getCode() == KeyCode.DIGIT2) {
                watergolemSelected = !watergolemSelected;
                nautilusSelected = false;
                crabSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                coralSelected = false;
                deleteenemiesshovelSelected = false;
            }

            if (e.getCode() == KeyCode.DIGIT3) {
                nautilusSelected = !nautilusSelected;
                watergolemSelected = false;
                crabSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                coralSelected = false;
                deleteenemiesshovelSelected = false;
            }

            if (e.getCode() == KeyCode.DIGIT4) {
                crabSelected = !crabSelected;
                watergolemSelected = false;
                nautilusSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                coralSelected = false;
                deleteenemiesshovelSelected = false;
            }

            if (e.getCode() == KeyCode.DIGIT5) {
                seamineSelected = !seamineSelected;
                coralSelected = false;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                deleteenemiesshovelSelected = false;
            }
            if (e.getCode() == KeyCode.DIGIT6) {
                pistolshrimpSelected = !pistolshrimpSelected;
                coralSelected = false;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                shovelSelected = false; 
                seamineSelected = false;
                deleteenemiesshovelSelected = false;
            }
            if (e.getCode() == KeyCode.DIGIT7) {
                shovelSelected = !shovelSelected;
                coralSelected = false;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                pistolshrimpSelected = false;
                seamineSelected = false;
                deleteenemiesshovelSelected = false;
            }
            if (e.getCode() == KeyCode.L) {
                deleteenemiesshovelSelected = !deleteenemiesshovelSelected;
                coralSelected = false;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                pistolshrimpSelected = false;
                seamineSelected = false;
                shovelSelected = false;
            }
            if (e.getCode() == KeyCode.NUMPAD0) {
                GameScene.highscoreTimerOn = false;
                HighscoreManager.saveHighscore(GameScene.timerOfRun);
                Main.nav.navigateTo(SceneType.GAME_OVER);
            }
        });

    
        canvas.setOnMouseClicked(e -> {
            double mouseX = e.getX();
            double mouseY = e.getY();

            if (mouseX >= 130 && mouseX <= 230 && mouseY >= 25 && mouseY <= 125) {
                watergolemSelected = !watergolemSelected;
                nautilusSelected = false;
                crabSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                coralSelected = false;
                return;
            }

            if (mouseX >= 10 && mouseX <= 110 && mouseY >= 25 && mouseY <= 125) {
                coralSelected = !coralSelected;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                return;
            }

            if (mouseX >= 250 && mouseX <= 350 && mouseY >= 25 && mouseY <= 125) {
                nautilusSelected = !nautilusSelected;
                watergolemSelected = false;
                crabSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                coralSelected = false;
                return;
            }

            if (mouseX >= 370 && mouseX <= 470 && mouseY >= 25 && mouseY <= 125) {
                crabSelected = !crabSelected;
                watergolemSelected = false;
                nautilusSelected = false;
                seamineSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                coralSelected = false;
                return;
            }

            if (mouseX >= 490 && mouseX <= 590 && mouseY >= 25 && mouseY <= 125) {
                seamineSelected = !seamineSelected;
                coralSelected = false;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                pistolshrimpSelected = false;
                shovelSelected = false;
                return;
            }
            if (mouseX >= 610 && mouseX <= 710 && mouseY >= 25 && mouseY <= 125) {
                pistolshrimpSelected = !pistolshrimpSelected;
                coralSelected = false;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                shovelSelected = false; 
                seamineSelected = false;
                return;
            }
            if (mouseX >= 730 && mouseX <= 830 && mouseY >= 25 && mouseY <= 125) {
                shovelSelected = !shovelSelected;
                coralSelected = false;
                watergolemSelected = false;
                nautilusSelected = false;
                crabSelected = false;
                pistolshrimpSelected = false;
                seamineSelected = false;
                return;
            }
            

            // Check if any bubble was clicked
            for (Bubble bubble : bubbles) {
                if (mouseX >= (bubble.getX() + 50) && mouseX <= bubble.getX() + 100 &&
                    mouseY >= (bubble.getY() + 50) && mouseY <= bubble.getY() + 100) {
                    bubbleclickSound.setVolume(2); // 200% Lautstärke
                    bubbleclickSound.play();
                    bubbles.remove(bubble);
                    amountOfBubbles += 50;
                    return;
                }
            }

            if (watergolemSelected) {
                for (Block b : grid.getBlocks()) {
                    if (b.contains(mouseX, mouseY) && b.isAvailable() && amountOfBubbles >= 100) {
                        double gx = b.getX() + (b.getWIDTH() - Images.WaterGolemImage.getWidth()) / 2 + grid.offsetWatergolemX;
                        double gy = b.getY() + (b.getHEIGHT() - Images.WaterGolemImage.getHeight()) / 2 + grid.offsetWatergolemY;

                        WaterGolem golem = new WaterGolem(gx, gy, Images.WaterGolemImage, waterGolemShots, gc);
                        golem.setBlock(b);
                        waterGolems.add(golem);
                        b.setOccupied();
                        b.setKindOfBlock("Watergolem");
                        amountOfBubbles -= 100;
                        watergolemSelected = false;
                        break;
                    }
                }
            }

            if (coralSelected) {
                for (Block b : grid.getBlocks()) {
                    if (b.contains(mouseX, mouseY) && b.isAvailable() && amountOfBubbles >= 50) {
                        double gx = b.getX() + (b.getWIDTH() - Images.CoralImage.getWidth()) / 2 + grid.offsetCoralX;
                        double gy = b.getY() + (b.getHEIGHT() - Images.CoralImage.getHeight()) / 2 + grid.offsetCoralY;

                        Coral coral = new Coral(gx, gy, Images.CoralImage);
                        coral.setBlock(b);
                        corals.add(coral);
                        b.setOccupied();
                        b.setKindOfBlock("Coral");
                        amountOfBubbles -= 50;
                        coralSelected = false;
                        break;
                    }
                }
            }

            if (nautilusSelected) {
                for (Block b : grid.getBlocks()) {
                    if (b.contains(mouseX, mouseY) && b.isAvailable() && amountOfBubbles >= 100) {
                        double gx = b.getX() + (b.getWIDTH() - Images.NautilusImage.getWidth()) / 2 + grid.offsetNautilusX;
                        double gy = b.getY() + (b.getHEIGHT() - Images.NautilusImage.getHeight()) / 2 + grid.offsetNautilusY;

                        Nautilus nautilus = new Nautilus(gx, gy, Images.NautilusImage);
                        nautilus.setBlock(b);
                        nautiluses.add(nautilus);
                        b.setOccupied();
                        b.setKindOfBlock("Nautilus");
                        amountOfBubbles -= 100;
                        nautilusSelected = false;
                        break;
                    }
                }
            }

            if (crabSelected) {
                for (Block b : grid.getBlocks()) {
                    if (b.contains(mouseX, mouseY) && b.isAvailable() && amountOfBubbles >= 150) {
                        double gx = b.getX() + (b.getWIDTH() - Images.CrabImage.getWidth()) / 2 + grid.offsetCrabX;
                        double gy = b.getY() + (b.getHEIGHT() - Images.CrabImage.getHeight()) / 2 + grid.offsetCrabY;

                        Crab crab = new Crab(gx, gy, Images.CrabImage);
                        crab.setBlock(b);
                        crabs.add(crab);
                        b.setOccupied();
                        b.setKindOfBlock("Crab");
                        amountOfBubbles -= 150;
                        crabSelected = false;
                        break;
                    }
                }
            }
            if (seamineSelected) {
                for (Block b : grid.getBlocks()) {
                    if (b.contains(mouseX, mouseY) && b.isAvailable() && amountOfBubbles >= 150) {
                        double gx = b.getX() + (b.getWIDTH() - Images.SeamineImage.getWidth()) / 2 + grid.offsetSeamineX;
                        double gy = b.getY() + (b.getHEIGHT() - Images.SeamineImage.getHeight()) / 2 + grid.offsetSeamineY;

                        Seamine seamine = new Seamine(gx, gy, Images.SeamineImage);
                        seamine.setBlock(b);
                        seamines.add(seamine);
                        b.setOccupied();
                        b.setKindOfBlock("Seamine");
                        amountOfBubbles -= 150;
                        seamineSelected = false;
                        break;
                    }
                }
            }
            if (pistolshrimpSelected) {
                for (Block b : grid.getBlocks()) {
                    if (b.contains(mouseX, mouseY) && b.isAvailable() && amountOfBubbles >= 350) {
                        double gx = b.getX() + (b.getWIDTH() - Images.PistolshrimpImage.getWidth()) / 2 + grid.offsetPistolshrimpX;
                        double gy = b.getY() + (b.getHEIGHT() - Images.PistolshrimpImage.getHeight()) / 2 + grid.offsetPistolshrimpY;

                        Pistolshrimp pistolshrimp = new Pistolshrimp(gx, gy, Images.PistolshrimpImage, shrimpShots, gc);
                        pistolshrimp.setBlock(b);
                        pistolshrimps.add(pistolshrimp);
                        b.setOccupied();
                        b.setKindOfBlock("Pistolshrimp");
                        amountOfBubbles -= 350;
                        pistolshrimpSelected = false;
                        break;
                    }
                }
            }
            if (shovelSelected) {
                for (Block b : grid.getBlocks()) {
                    if (b.contains(mouseX, mouseY) && !b.isAvailable()) {
                        waterGolems.removeIf(g -> g.getBlock() == b);
                        corals.removeIf(c -> c.getBlock() == b);
                        nautiluses.removeIf(n -> n.getBlock() == b);
                        crabs.removeIf(c -> c.getBlock() == b);
                        seamines.removeIf(c -> c.getBlock() == b);
                        pistolshrimps.removeIf(c -> c.getBlock() == b);
                        switch (b.getKindOfBlock()) {
                            case "Watergolem" -> amountOfBubbles += 50;
                            case "Coral" -> amountOfBubbles += 25;
                            case "Nautilus" -> amountOfBubbles += 50;
                            case "Crab" -> amountOfBubbles += 75;
                            case "Seamine" -> amountOfBubbles += 75;
                            case "Pistolshrimp" -> amountOfBubbles += 175;
                            default -> {
                            }
                        }

                        // make block available again
                        b.setAvailable();
                        shovelSelected = false;
                        break;
                    }
                }
            }
            if (deleteenemiesshovelSelected) {
                for (LavaSlime slime : slimes) {
                    if (mouseX >= slime.getX() && mouseX <= slime.getX() + Images.LavaSlimeImage.getWidth() &&
                        mouseY >= slime.getY() && mouseY <= slime.getY() + Images.LavaSlimeImage.getHeight()) {
                        slimes.remove(slime);
                        alert("Removed LavaSlime");
                        return;
                    }
                }
                for (LavaGolem golem : lavaGolems) {
                    if (mouseX >= golem.getX() && mouseX <= golem.getX() + Images.LavaGolemImage.getWidth() &&
                        mouseY >= golem.getY() && mouseY <= golem.getY() + Images.LavaGolemImage.getHeight()) {
                        lavaGolems.remove(golem);
                        alert("Removed LavaGolem");
                        return;
                    }
                }
                for (LavaGolemProjectile lgp : lavaGolemShots) {
                    if (mouseX >= lgp.getX() && mouseX <= lgp.getX() + Images.LavaProjectileImage.getWidth() &&
                        mouseY >= lgp.getY() && mouseY <= lgp.getY() + Images.LavaProjectileImage.getHeight()) {
                        lavaGolemShots.remove(lgp);
                        alert("Removed LavaGolem Projectile");
                        return;
                    }
                }
            }
        });
    }
    // ------------------------

    @Override
    public void onExit() {
        timer.stop();
        if (bgMusic != null) bgMusic.stop();
    }

    private void update(double deltaInSec) {
        collisionHandler.checkAllColisions(deltaInSec);
        timeSinceLastSpawnlavaslime += deltaInSec; // Für Cooldown (sobald ein LavaSlime erzeugt wurde)
        timeSinceLastSpawnlavaGolem += deltaInSec; // Für Cooldown (sobald ein LavaGolem erzeugt wurde)
        timeSinceLastBubbleSpawn += deltaInSec;
        
        // Countdown alert timer
        if (alertTimer > 0) {
            alertTimer -= deltaInSec;
        }

        if (highscoreTimerOn) {
            timerOfRun += deltaInSec;
        }

        if (timeSinceLastSpawnlavaslime >= lavaSlimeCooldown) { // Slimes werden mit einem Cooldown erzeugt
            spawnRandomSlime();
            timeSinceLastSpawnlavaslime = 0;
        }

        if (timeSinceLastSpawnlavaGolem >= lavaGolemCooldown) {
            spawnRandomGolem();
            timeSinceLastSpawnlavaGolem = 0;
        }

        for (Coral coral : corals) {
            if (coral.updateAndShouldSpawn(deltaInSec)) {
                spawnCoralBubble(coral);
            }
        }

        if (timeSinceLastBubbleSpawn >= BUBBLE_COOLDOWN) {
            spawnBubble();
            timeSinceLastBubbleSpawn = 0;
        }

        for (Bubble bubble : bubbles) {
            bubble.update(deltaInSec);
        }

        for (Coral coral : corals) {
            if (coral.updateAndShouldSpawn(deltaInSec)) {
                spawnCoralBubble(coral);
            }
        }

        for (LavaSlime slime : slimes) { // LavaSlimes werden updated (um Interaktionen ausführen zu können)
            slime.update(deltaInSec);
        }

        for (LavaGolem golem : lavaGolems) { // LavaGolems werden updated (um Interaktionen ausführen zu können)
            golem.update(deltaInSec);
        }

        for (WaterGolem wg : waterGolems) { // WaterGolems werden updated (um Interaktionen ausführen zu können)
            wg.update(deltaInSec);
        }

        for (Pistolshrimp ps : pistolshrimps) {
            ps.update(deltaInSec);
        }

        for (LavaGolemProjectile lgp : lavaGolemShots) {
            lgp.update(deltaInSec);
        }

        for (WaterGolemProjectile wgp : waterGolemShots) { // WaterGolemProjectiles werden updated (um Interaktionen ausführen zu können)
            wgp.update(deltaInSec);
        }

        for (PistolShrimpProjectile psp : shrimpShots) {
            psp.update(deltaInSec);
        }

        for (Bubble bubble : bubbles) {
            bubble.update(deltaInSec);
        }
    }

    private void paint() {
        gc.drawImage(Images.gameBackgroundImage, 0, 0);
        gc.drawImage(Images.WatergolemCardImage, 130, 25);
        gc.drawImage(Images.CoralCardImage, 10, 25);
        gc.drawImage(Images.NautilusCardImage, 250, 25);
        gc.drawImage(Images.CrabCardImage, 370, 25);
        gc.drawImage(Images.SeamineCardImage, 490, 25);
        gc.drawImage(Images.PistolshrimpCardImage, 610, 25);
        gc.drawImage(Images.ShovelCard, 730, 25);

        if (watergolemSelected) {
            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(4);
            gc.strokeRect(130, 25, 100, 100);
        }

        for (WaterGolem wg : waterGolems) {
            wg.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.LIGHTBLUE);
                gc.setLineWidth(2);
                gc.strokeRect(wg.getX(), wg.getY(), Const.WATER_GOLEM_HITBOX_CORNER_X, Const.WATER_GOLEM_HITBOX_CORNER_Y);
            }
        }

        for (Coral c : corals) {
            c.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.VIOLET);
                gc.setLineWidth(2);
                gc.strokeRect(c.getX(), c.getY() + Const.CORAL_OFFSET_Y, Const.CORAL_HITBOX_CORNER_X, Const.CORAL_HITBOX_CORNER_Y);


            }
        }

        for (Nautilus n : nautiluses) {
            n.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.ORANGE);
                gc.setLineWidth(2);
                gc.strokeRect(n.getX(), n.getY() + Const.NAUTILUS_OFFSET_Y, Const.NAUTILUS_HITBOX_CORNER_X, Const.NAUTILUS_HITBOX_CORNER_Y);
            }
        }

        for (Crab c : crabs) {
            c.draw(gc);

            if (hitboxesOn) {
                // Hitbox for attacking
                gc.setStroke(Color.YELLOW);
                gc.setLineWidth(2);
                gc.strokeRect(c.getX() + Const.CRAB_OFFSET_X, c.getY() + Const.CRAB_OFFSET_Y, 325, Const.CRAB_HITBOX_CORNER_Y);

                // Hitbox for getting Attacked
                gc.setStroke(Color.ORANGE);
                gc.setLineWidth(2);
                gc.strokeRect(c.getX() + Const.CRAB_OFFSET_X, c.getY() + Const.CRAB_OFFSET_Y, Const.CRAB_HITBOX_CORNER_X, Const.CRAB_HITBOX_CORNER_Y);
            }
        }

        for (LavaGolem golem : lavaGolems) {
            golem.draw(gc);
            
            if (hitboxesOn) {
                gc.setStroke(Color.ORANGE);
                gc.setLineWidth(2);
                gc.strokeRect(golem.getX(), golem.getY(), Const.LAVA_GOLEM_HITBOX_CORNER_X, Const.LAVA_GOLEM_HITBOX_CORNER_Y);
            }
        }

        for (LavaSlime slime : slimes) {
            slime.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.ORANGE);
                gc.setLineWidth(2);
                gc.strokeRect(slime.getX(), slime.getY() + Const.LAVA_SLIME_OFFSET_Y, Const.LAVA_SLIME_HITBOX_CORNER_X, Const.LAVA_SLIME_HITBOX_CORNER_Y);
            }
        }

        for (Seamine seamine : seamines) {
            seamine.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.CYAN);
                gc.setLineWidth(2);
                gc.strokeRect(seamine.getX() + Const.SEAMINE_OFFSET_X, seamine.getY() + Const.SEAMINE_OFFSET_Y, Const.SEAMINE_HITBOX_CORNER_X, Const.SEAMINE_HITBOX_CORNER_Y);
            }
        }

        for (Pistolshrimp ps : pistolshrimps) {
            ps.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.MAGENTA);
                gc.setLineWidth(2);
                gc.strokeRect(ps.getX() + Const.PS_OFFSET_X, ps.getY() + Const.PS_OFFSET_Y, Const.PS_HITBOX_CORNER_X, Const.PS_HITBOX_CORNER_Y);
            }
        }

        for (LavaGolemProjectile lgp : lavaGolemShots) {
            lgp.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.ORANGE);
                gc.setLineWidth(2);
                gc.strokeRect(lgp.getX() + Const.LGP_OFFSET_X, lgp.getY() + Const.LGP_OFFSET_Y, Const.LGP_HITBOX_CORNER_X, Const.LGP_HITBOX_CORNER_Y);
            }
        }

        for (WaterGolemProjectile wgp : waterGolemShots) {
            wgp.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.LIGHTBLUE);
                gc.setLineWidth(2);
                gc.strokeRect(wgp.getX() + Const.WGP_OFFSET_X, wgp.getY() + Const.WGP_OFFSET_Y, Const.WGP_HITBOX_CORNER_X, Const.WGP_HITBOX_CORNER_Y);
            }
        }

        for (PistolShrimpProjectile psp : shrimpShots) {
            psp.draw(gc);

            if (hitboxesOn) {
                gc.setStroke(Color.MAGENTA);
                gc.setLineWidth(2);
                gc.strokeRect(psp.getX() + Const.PSP_OFFSET_X, psp.getY() + Const.PSP_OFFSET_Y, Const.PSP_HITBOX_CORNER_X, Const.PSP_HITBOX_CORNER_Y);
            }
        }

        for (Bubble bubble : bubbles) {
            bubble.draw(gc);
        }

        if (coralSelected) {
            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(4);
            gc.strokeRect(10, 25, 100, 100);
        }

        if (nautilusSelected) {
            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(4);
            gc.strokeRect(250, 25, 100, 100);
        }

        if (crabSelected) {
            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(4);
            gc.strokeRect(370, 25, 100, 100);
        }

        for (Crab c : crabs) {
            c.draw(gc);
        }

        if (seamineSelected) {
            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(4);
            gc.strokeRect(490, 25, 100, 100);
        }

        if (pistolshrimpSelected) {
            gc.setStroke(Color.DARKBLUE);
            gc.setLineWidth(4);
            gc.strokeRect(610, 25, 100, 100);
        }

        if (shovelSelected || deleteenemiesshovelSelected) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(4);
            gc.strokeRect(730, 25, 100, 100);
        }

        if (shovelSelected || deleteenemiesshovelSelected) {
            canvas.setCursor(shovelCursor);
        } else {
            canvas.setCursor(defaultCursor);
        }

        // draw hitboxes if toggled
        if (hitboxesOn) {
            gc.setLineWidth(2);
            for (Block b : grid.getBlocks()) {
                if (b.isAvailable()) {
                    gc.setStroke(Color.GREEN);
                } else {
                    gc.setStroke(Color.RED);
                }

                gc.strokeRect(b.getX(), b.getY(), b.getWIDTH(), b.getHEIGHT());
            }
            
            // Draw bubble hitboxes
            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(2);
            for (Bubble bubble : bubbles) {
                gc.strokeRect((bubble.getX() + 50), (bubble.getY() + 50), 50, 50);
            }
        }

        displayBubbles(amountOfBubbles);
        
        if (alertTimer > 0) {
            gc.setFill(Color.WHITE);
            gc.fillRect(8.5, 869, 600, 60);
            
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(3);
            gc.strokeRect(8.5, 869, 600, 60);
            
            gc.setFill(Color.BLACK);
            gc.setFont(new javafx.scene.text.Font("Arial", 30));
            gc.fillText(alertMessage, 50, 910);
        }
    }

    private void displayBubbles(int count) {
        gc.drawImage(Images.BubbleCountImage, 1485, 25);
        gc.setFill(Color.DARKBLUE);
        gc.setFont(new javafx.scene.text.Font("Arial", 25));
        gc.fillText(String.valueOf(count), 1510, 113);
    }

    private void spawnRandomSlime() {
        int index = rand.nextInt(5); // Zwischen den 5 Reihen (zufällige Zahl ausgewählt)
        double y = rowsY[index]; // (Zwischen den Reihen wird eine Zahl ausgewählt)

        LavaSlime slime = new LavaSlime(1513, y, Images.LavaSlimeImage); // Neuer LavaSlime auf der zufälligen, ausgewählter Reihe
        slimes.add(slime); // Slime wird erzeugt
    }

    private void spawnRandomGolem() {
        int index = rand.nextInt(5); // Zwischen den 5 Reihen (zufällige Zahl ausgewählt)
        double y = rowsY[index]; // (Zwischen den Reihen wird eine Zahl ausgewählt)

        LavaGolem golem = new LavaGolem(1513, y, Images.LavaGolemImage, lavaGolemShots, gc);
        lavaGolems.add(golem);
    }

    private void spawnBubble() {
        double x = 50 + rand.nextInt(Const.WIDTH - 100);
        double y = canvas.getHeight() - 50;

        Bubble bubble = new Bubble(x, y, Images.BubbleImage, bubbles);
        bubbles.add(bubble);

        Media bubbleMedia = new Media(getClass().getResource("/sounds/bubbles.mp3").toExternalForm());
        MediaPlayer bubblePlayer = new MediaPlayer(bubbleMedia);
        bubblePlayer.setStartTime(Duration.seconds(0));
        bubblePlayer.setStopTime(Duration.seconds(0.75));
        bubblePlayer.setVolume(4);

        bubblePlayer.play();
    }


    private void spawnCoralBubble(Coral coral) {
        double x = coral.getX() - 27;
        double y = coral.getY() - 43;

        Bubble bubble = new Bubble(x, y, Images.BubbleImage, bubbles);
        bubbles.add(bubble);

        Media bubbleMedia = new Media(getClass().getResource("/sounds/bubbles.mp3").toExternalForm());
        MediaPlayer bubblePlayer = new MediaPlayer(bubbleMedia);
        bubblePlayer.setStartTime(Duration.seconds(0));
        bubblePlayer.setStopTime(Duration.seconds(0.75));
        bubblePlayer.setVolume(2);

        bubblePlayer.play();
    }
    private void alert(String message) {
        alertMessage = message;
        alertTimer = 3.0; // Show alert for 3 seconds
    }
}