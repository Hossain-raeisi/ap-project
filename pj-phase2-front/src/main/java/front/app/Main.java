package front.app;

import front.Config;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Config.loadConfig();
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        SceneControl.loadGui(primaryStage);
    }
}
