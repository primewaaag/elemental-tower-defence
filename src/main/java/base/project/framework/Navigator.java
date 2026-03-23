package base.project.framework;

import java.util.HashMap;
import java.util.Map;

import base.project.bases.BaseScene;
import javafx.stage.Stage;

public class Navigator {
    private final Stage stage;
    private BaseScene currentScene;
    private final Map<SceneType, BaseScene> viewMap = new HashMap<>();

    public Navigator(Stage stage) {
        this.stage = stage;
    }

    public void registerScene(SceneType enumScene, BaseScene scene) {
        viewMap.put(enumScene, scene);
    }

    public void navigateTo(SceneType enumScene) {
        if (currentScene != null) {
            currentScene.onExit();
        }

        BaseScene baseScene = viewMap.get(enumScene);

        currentScene = baseScene;
        baseScene.onEnter();
        stage.setScene(baseScene);
        stage.show();
    }
}