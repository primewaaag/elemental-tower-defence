package base.project.bases;

import base.project.framework.Navigator;
import base.project.vars.Const;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class BaseScene extends Scene {
    protected StackPane root;
    protected Canvas canvas;
    protected Navigator navigator;

    public BaseScene(Navigator navigator) {
        super(new StackPane());
        this.navigator = navigator;
        root = (StackPane) getRoot();
        canvas = new Canvas(Const.WIDTH, Const.HEIGHT);
        root.getChildren().add(canvas);
    }

    public void onEnter() {
    }

    public void onExit() {
    }
}