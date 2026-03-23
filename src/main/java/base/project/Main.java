package base.project;

import base.project.framework.Navigator;
import base.project.framework.SceneType;
import base.project.scenes.GameOverScene;
import base.project.scenes.GameScene;
import base.project.scenes.StartScene;
import base.project.vars.Const;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static Navigator nav;

    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle("Elemental Tower-Defense");
        mainStage.setWidth(Const.WIDTH);
        mainStage.setHeight(Const.HEIGHT);
        mainStage.setAlwaysOnTop(true);

        nav = new Navigator(mainStage);

        nav.registerScene(SceneType.GAME, new GameScene(nav));
        nav.registerScene(SceneType.START, new StartScene(nav));
        nav.registerScene(SceneType.GAME_OVER, new GameOverScene(nav));

        nav.navigateTo(SceneType.START);
    }
}