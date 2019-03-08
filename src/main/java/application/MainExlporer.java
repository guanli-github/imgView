package application;

import data.BookMark;
import data.Setting;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.ThumbnailUtil;

public class MainExlporer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        runOnStartUp();

        if(Setting.isFullScreen){
            stage.setFullScreen(true);
            stage.initStyle(StageStyle.UNDECORATED);
        }
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        Parent root = FXMLLoader.load(getClass().getResource("/explorer.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add
                (getClass().getResource("/css/explorer.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    //启动式运行
    private void runOnStartUp(){
        ThumbnailUtil.cleanThumbnails();
        BookMark.cleanBookMarks();
    }
    public static void main(String[] args) {
        launch(args);
    }
}