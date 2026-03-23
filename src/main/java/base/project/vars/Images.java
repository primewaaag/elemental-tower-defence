package base.project.vars;

import javafx.scene.image.Image;

public class Images {

    // -- UI --

    // Screens
    public static final Image gameBackgroundImage = new Image("/screens/GameBackground.png", 1920, 1080, true, true);
    public static final Image startBackgroundImage = new Image("/screens/StartScreenFinal.png", 1920, 1080, true, true);
    public static final Image gameOverBackgroundImage = new Image("/screens/GameOverScreenFinal.png", 1920, 1080, true, true);

    // Cards
    public static final Image WatergolemCardImage = new Image("/gui/water_golem_card.png", 100, 150, true, true);
    public static final Image CoralCardImage = new Image("/gui/coral_card.png", 100, 150, true, true);
    public static final Image NautilusCardImage = new Image("/gui/nautilus_card.png", 100, 150, true, true);
    public static final Image CrabCardImage = new Image("/gui/crab_card.png", 100, 150, true, true);
    public static final Image SeamineCardImage = new Image("/gui/Seamine_card.png", 100, 150, true, true);
    public static final Image PistolshrimpCardImage = new Image("/gui/pistolshrimp_card.png", 100, 150, true, true);
    public static final Image ShovelCard = new Image("/gui/shovelcard.png", 100, 100, true, true);

    // Misc.
    public static final Image ShovelCursor = new Image("/gui/shovel.png", 50, 50, true, true);
    public static final Image BubbleCountImage = new Image("/gui/BubbleCount.png", 200, 100, true, true);

    // -------------------------

    // -- Player Entities
    
    public static final Image WaterGolemImage = new Image("/player/water_golem.png", 32 * 3, 32 * 3, true, true);
    public static final Image CoralImage = new Image("/player/coral.png", 32 * 3, 32 * 3, true, true);
    public static final Image NautilusImage = new Image("/player/nautilus.png", 32 * 3, 32 * 3, true, true);
    public static final Image CrabImage = new Image("/player/crab.png", 32 * 3, 32 * 3, true, true);
    public static final Image SeamineImage = new Image("/player/Seamine.png", 32 * 3, 32 * 3, true, true);
    public static final Image PistolshrimpImage = new Image("/player/pistolshrimp.png", 32 * 3, 32 * 3, true, true);
    public static final Image BubbleImage = new Image("/gui/bubble.png", 32 * 5, 32 * 5, true, true);
    public static final Image WaterProjectileImage = new Image("/player/water_projectile.png", 32 * 3, 32 * 3, true, true);
    public static final Image ShrimpProjectileImage = new Image("/player/shrimp_projectile.png", 32 * 3, 32 * 3, true, true);

    // -------------------------

    // -- Enemy Entities

    public static final Image LavaSlimeImage = new Image("/enemy/lava_slime.png", 32 * 3, 32 * 3, true, true);
    public static final Image LavaGolemImage = new Image("/enemy/lava_golem.png", 32 * 3, 32 * 3, true, true);
    public static final Image LavaProjectileImage = new Image("/enemy/lava_projectile.png", 32 * 3, 32 * 3, true, true);

    // -------------------------
}