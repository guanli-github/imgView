package utils;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static ObservableList<Stage> stage = FXRobotHelper.getStages();
    public static void toExplorer(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(SceneManager.class.getResource("/explorer.fxml")));
            scene.getStylesheets().add
                    (SceneManager.class.getResource("/css/explorer.css").toExternalForm());
            stage.get(0).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void toRoot(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(SceneManager.class.getResource("/rootExplorer.fxml")));
            scene.getStylesheets().add
                    (SceneManager.class.getResource("/css/explorer.css").toExternalForm());
            stage.get(0).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void toViewer(){
        stage.get(0).setFullScreen(true);
        stage.get(0).setMaximized(true);
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(SceneManager.class.getResource("/viewer.fxml")));
            scene.getStylesheets().add
                    (SceneManager.class.getResource("/css/text.css").toExternalForm());
            stage.get(0).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void toText(){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(SceneManager.class.getResource("/text.fxml")));
            scene.getStylesheets().add
                    (SceneManager.class.getResource("/css/text.css").toExternalForm());
            stage.get(0).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
