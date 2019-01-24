package viewer;

import data.BookMark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainViewer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/viewer.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add
                (getClass().getResource("/css/viewer.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest((close)->{
            BookMark.saveCurrent();
            System.exit(0);
        });
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}