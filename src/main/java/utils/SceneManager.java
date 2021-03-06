package utils;

import com.sun.javafx.robot.impl.FXRobotHelper;
import data.Setting;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private final static Stage stage = FXRobotHelper.getStages().get(0);

    public static void toExplorer(){
        changeScene("/explorer.fxml","/css/explorer.css");
    }
    public static void toRoot(){
        changeScene("/rootExplorer.fxml","/css/explorer.css");
    }
    public static void toViewer(){
       changeScene("/viewer.fxml","/css/viewer.css");
    }
    public static void toText(){
        changeScene("/text.fxml","/css/text.css");
    }
    public static void toSearch(){
        changeScene("/textSearch.fxml","/css/search.css");
    }

    private static void changeScene(String fxmlUrl,String cssUrl){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(SceneManager.class.getResource(fxmlUrl)));
            scene.getStylesheets().add
                    (SceneManager.class.getResource(cssUrl).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getStage(){
        if(Setting.isFullScreen) {
            stage.setFullScreen(true);
        }
        return stage;
    }
}
