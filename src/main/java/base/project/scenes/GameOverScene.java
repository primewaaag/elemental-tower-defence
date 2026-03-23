package base.project.scenes;

import static base.project.Main.nav;
import base.project.bases.BaseScene;
import base.project.framework.HighscoreManager;
import base.project.framework.Navigator;
import base.project.framework.SceneType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverScene extends BaseScene {
    // -- Variabeln --
    private GraphicsContext gc;
    private final Image gameOverBackgroundImage = new Image("/screens/GameOverScreenFinal.png", 1600, 950, true, true); // NOTE: image in resources einfügen und hier path ändern
    // -------------------------

    public GameOverScene(Navigator navigator) {
        super(navigator);
    }

    // Main funktion (hier code schreiben)
    @Override
    public void onEnter() {
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(gameOverBackgroundImage, 0, 0);

        double finalTime = GameScene.timerOfRun;
        double highscore = HighscoreManager.getHighscore();

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 32));
        gc.fillText("Your Time: " + String.format("%.2f", finalTime) + " sec", 10, 50);
        gc.fillText("Highscore: " + String.format("%.2f", highscore) + " sec", 10, 90);

        setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.SPACE) {
                nav.navigateTo(SceneType.GAME);
            }
        });

        canvas.setOnMouseClicked(e -> {
            double mouseX = e.getX();
            double mouseY = e.getY();

            if (mouseX >= 470 && mouseX <= 1040 && mouseY >= 300 && mouseY <= 470) {
                nav.navigateTo(SceneType.GAME);
            }
            if (mouseX >= 470 && mouseX <= 1040 && mouseY >= 500 && mouseY <= 670) {
                System.exit(0);
            }

        });
    }
}