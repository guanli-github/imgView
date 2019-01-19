package viewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class MainViewer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/viewer.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        //stage.setFullScreen(true);
        scene.getStylesheets().add
                (getClass().getResource("/viewer.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}