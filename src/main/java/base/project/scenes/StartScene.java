package base.project.scenes;

import static base.project.Main.nav;
import base.project.bases.BaseScene;
import base.project.framework.Navigator;
import base.project.framework.SceneType;
import base.project.vars.Const;
import base.project.vars.Images;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class StartScene extends BaseScene {
    // -- Variabeln --
    private GraphicsContext gc;
    public static MediaPlayer startScreenMusic;
    // -------------------------

    public StartScene(Navigator navigator) {
        super(navigator);
    }

    // Main funktion (hier code schreiben)
    @Override
    public void onEnter() {
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(Images.startBackgroundImage, 0, 0);

        // music loading (startscreen)
        Media bg = new Media(getClass().getResource("/sounds/screens/startscreen-music.mp3").toExternalForm());
        startScreenMusic = new MediaPlayer(bg);

        // Einstellungen
        startScreenMusic.setCycleCount(MediaPlayer.INDEFINITE); // Endlosschleife
        startScreenMusic.setVolume(0.1); // Lautstärke 10%

        startScreenMusic.play();

        setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.SPACE) {
                nav.navigateTo(SceneType.GAME);
                startScreenMusic.stop();
            }
        });
        canvas.setOnMouseClicked(e -> {
            double mouseX = e.getX();
            double mouseY = e.getY();

            // Start button
            if (mouseX >= Const.SB_TLC_X && mouseX <= Const.SB_TLC_Y && mouseY >= Const.SB_BRC_X && mouseY <= Const.SB_BRC_Y) {
                nav.navigateTo(SceneType.GAME);
                startScreenMusic.stop();
            }

            // hard mode button (does not work)
            if (mouseX >= Const.HMB_TLC_X && mouseX <= Const.HMB_TLC_Y && mouseY >= Const.HMB_BRC_X && mouseY <= Const.HMB_BRC_Y) {
                nav.navigateTo(SceneType.GAME);
                startScreenMusic.stop();
            }

            // Quit button
            if (mouseX >= Const.QB_TLC_X && mouseX <= Const.QB_TLC_Y && mouseY >= Const.QB_BRC_X && mouseY <= Const.QB_BRC_Y) {
                System.exit(0);
            }
        });
    }
}